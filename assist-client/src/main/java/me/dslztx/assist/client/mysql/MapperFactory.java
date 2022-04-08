package me.dslztx.assist.client.mysql;

import static me.dslztx.assist.util.ObjectAssist.isNotNull;
import static me.dslztx.assist.util.ObjectAssist.isNull;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ArrayAssist;
import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.StringAssist;

public class MapperFactory {

    private static final Logger logger = LoggerFactory.getLogger(MapperFactory.class);

    private static final String MAPPER_CONFIG_FILE = "mapper.properties";

    private static ConcurrentHashMap<DataSource, ConcurrentHashMap<Class, Object>> mapperInstanceCache =
        new ConcurrentHashMap<>();

    private static ConcurrentHashMap<DataSource, SqlSessionFactory> sqlSessionFactoryCache =
        new ConcurrentHashMap<DataSource, SqlSessionFactory>();

    private static boolean mapperPathInit = false;

    private static Set<Class<?>> mapperSet;

    /**
     * 直接synchronized修饰，解决线程安全性问题，调用频次低，加该锁性能影响不大
     * 
     * @param dataSource
     * @param clz
     * @param <T>
     * @return
     */
    public static synchronized <T> T obtainMapper(DataSource dataSource, Class<T> clz) {
        if (isNull(dataSource)) {
            throw new RuntimeException("no datasource");
        }

        if (isNull(clz)) {
            throw new RuntimeException("no mapper class");
        }

        // 已经有了则直接返回结果
        if (isNotNull(mapperInstanceCache.get(dataSource)) && isNotNull(mapperInstanceCache.get(dataSource).get(clz))) {
            return (T)mapperInstanceCache.get(dataSource).get(clz);
        }

        try {
            if (isNull(sqlSessionFactoryCache.get(dataSource))) {
                if (!mapperPathInit) {
                    // 扫描耗费性能，故优化只扫描一次
                    mapperSet = scanMapperInterfaceAll();

                    mapperPathInit = true;
                }

                sqlSessionFactoryCache.putIfAbsent(dataSource, generateSqlSessionFactory(dataSource));
            }

            if (isNull(mapperInstanceCache.get(dataSource))) {
                mapperInstanceCache.putIfAbsent(dataSource, new ConcurrentHashMap<>());
            }

            Object mapperInstance = generateMapperInstance(sqlSessionFactoryCache.get(dataSource), clz);

            mapperInstanceCache.get(dataSource).putIfAbsent(clz, mapperInstance);

            return (T)mapperInstance;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    private static <T> Object generateMapperInstance(SqlSessionFactory sqlSessionFactory, Class<T> clz) {
        /*
            SqlSession session = obtainSqlSessionFactory().openSession();
            try {
                UserMapper userMapper = session.getMapper(UserMapper.class);
                return userMapper.listUsers();
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                CloseableAssist.closeQuietly(session);
            }
         */

        /*
        以上是Mapper生成和使用的的朴素方案<br/>
        这里是生成一个Mapper代理类，传入SqlSessionFactory，代理类会完成上述工作，具体可看SqlSessionManager的invoke()方法
        
        <br/>
        在Spring中是使用SqlSessionTemplate类，实现效果跟SqlSessionManager一致
         */
        SqlSessionManager sqlSessionTemplate = SqlSessionManager.newInstance(sqlSessionFactory);

        MapperProxyFactory mapperFactory = new MapperProxyFactory(clz);

        return mapperFactory.newInstance(sqlSessionTemplate);
    }

    private static SqlSessionFactory generateSqlSessionFactory(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment =
            new Environment("development#" + dataSource.hashCode(), transactionFactory, dataSource);

        Configuration configuration = new Configuration(environment);

        if (CollectionAssist.isEmpty(mapperSet)) {
            throw new RuntimeException("no mapper interface");
        }

        int cnt = 0;

        for (Class clz : mapperSet) {
            if (clz.isInterface()) {
                cnt++;

                logger.info("mapper class {} is scanned and added", clz.getCanonicalName());
                configuration.addMapper(clz);
            }
        }

        if (cnt == 0) {
            throw new RuntimeException("no mapper interface");
        }

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static String[] obtainScanPath() {
        try {
            org.apache.commons.configuration2.Configuration configuration =
                ConfigLoadAssist.propConfig(MAPPER_CONFIG_FILE);

            if (isNotNull(configuration)) {
                String mapperScanPaths = configuration.getString("mapper.path");

                if (StringAssist.isNotBlank(mapperScanPaths)) {
                    return StringAssist.split(mapperScanPaths, ',', true);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return null;
    }

    public static Set<Class<?>> scanMapperClassByAnnotation(Class<? extends Annotation> clz) {
        String[] mapperPathPrefixArray = obtainScanPath();

        if (ArrayAssist.isEmpty(mapperPathPrefixArray)) {
            throw new RuntimeException("not specify mapper scan path");
        }

        logger.info("the mapper scan path is [{}]", StringAssist.joinUseSeparator(mapperPathPrefixArray, ','));

        // 非线程安全的，详见“https://github.com/ronmamo/reflections/issues/81”
        Reflections reflections = new Reflections((Object[])mapperPathPrefixArray);

        return reflections.getTypesAnnotatedWith(clz);
    }

    public static Set<Class<?>> scanMapperClassByParentType(Class clz) {
        String[] mapperPathPrefixArray = obtainScanPath();

        if (ArrayAssist.isEmpty(mapperPathPrefixArray)) {
            throw new RuntimeException("not specify mapper scan path");
        }

        logger.info("the mapper scan path is [{}]", StringAssist.joinUseSeparator(mapperPathPrefixArray, ','));

        // 非线程安全的，详见“https://github.com/ronmamo/reflections/issues/81”
        Reflections reflections = new Reflections((Object[])mapperPathPrefixArray);

        return reflections.getSubTypesOf(clz);
    }

    public static Set<Class<?>> scanMapperInterfaceAll() {
        String[] mapperPathPrefixArray = obtainScanPath();

        if (ArrayAssist.isEmpty(mapperPathPrefixArray)) {
            throw new RuntimeException("not specify mapper scan path");
        }

        logger.info("the mapper scan path is [{}]", StringAssist.joinUseSeparator(mapperPathPrefixArray, ','));

        // 非线程安全的，详见“https://github.com/ronmamo/reflections/issues/81”
        Reflections reflections = new Reflections((Object[])mapperPathPrefixArray, new SubTypesScanner(false));

        Set<Class<?>> subTypes = reflections.getSubTypesOf(Object.class);

        Set<Class<?>> filtered = new HashSet<>();

        for (Class clz : subTypes) {
            if (clz.isInterface()) {
                filtered.add(clz);
            }
        }

        return filtered;
    }
}
