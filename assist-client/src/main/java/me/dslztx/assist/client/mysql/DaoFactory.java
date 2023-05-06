package me.dslztx.assist.client.mysql;

import static me.dslztx.assist.util.ObjectAssist.isNotNull;
import static me.dslztx.assist.util.ObjectAssist.isNull;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.dao.Dao;
import me.dslztx.assist.dao.Mapper;
import me.dslztx.assist.util.ArrayAssist;
import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.StringAssist;

@Deprecated
public class DaoFactory {

    private static final Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    private static final String MAPPER_CONFIG_FILE = "mapper.properties";

    private static ConcurrentHashMap<DataSource, ConcurrentHashMap<Class<? extends Dao>, Dao>> daoInstanceCache =
        new ConcurrentHashMap<DataSource, ConcurrentHashMap<Class<? extends Dao>, Dao>>();

    private static ConcurrentHashMap<DataSource, SqlSessionFactory> sqlSessionFactoryCache =
        new ConcurrentHashMap<DataSource, SqlSessionFactory>();

    public static synchronized <T extends Dao> T obtainDao(DataSource dataSource, Class<T> clz) {
        if (isNull(dataSource)) {
            throw new RuntimeException("no datasource");
        }

        if (isNull(clz)) {
            throw new RuntimeException("no dao class");
        }

        injectCacheIfNotExist(dataSource);

        if (isNull(daoInstanceCache.get(dataSource).get(clz))) {
            try {
                @SuppressWarnings("JavaReflectionMemberAccess")
                Constructor<T> constructor = clz.getDeclaredConstructor();
                constructor.setAccessible(true);
                T obj = constructor.newInstance();

                obj.setDataSource(dataSource);
                obj.setSqlSessionFactory(sqlSessionFactoryCache.get(dataSource));

                // 需要考虑并发情形，否则很容易导致重复生成Dao实例
                daoInstanceCache.get(dataSource).putIfAbsent(clz, obj);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return clz.cast(daoInstanceCache.get(dataSource).get(clz));
    }

    private static void injectCacheIfNotExist(DataSource dataSource) {
        if (isNull(sqlSessionFactoryCache.get(dataSource))) {
            // 需要考虑并发情形，否则很容易导致重复generateSqlSessionFactory(dataSource)，进而重复扫描mapper多次
            sqlSessionFactoryCache.putIfAbsent(dataSource, generateSqlSessionFactory(dataSource));
        }

        if (isNull(daoInstanceCache.get(dataSource))) {
            daoInstanceCache.putIfAbsent(dataSource, new ConcurrentHashMap<Class<? extends Dao>, Dao>());
        }
    }

    private static SqlSessionFactory generateSqlSessionFactory(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment =
            new Environment("development#" + dataSource.hashCode(), transactionFactory, dataSource);
        logger.info("the sqlSessionFactory's environment id is: {}", environment.getId());

        Configuration configuration = new Configuration(environment);

        registerMapper(configuration);

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static void registerMapper(Configuration conf) {
        try {
            org.apache.commons.configuration2.Configuration configuration =
                ConfigLoadAssist.propConfig(MAPPER_CONFIG_FILE);

            if (isNotNull(configuration)) {
                String mapperPathPrefixs = configuration.getString("mapper.path");

                if (StringAssist.isNotBlank(mapperPathPrefixs)) {
                    String[] mapperPathPrefixArray = StringAssist.split(mapperPathPrefixs, ',', true);

                    if (ArrayAssist.isNotEmpty(mapperPathPrefixArray)) {
                        scanRegisterMapper(conf, mapperPathPrefixArray);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private static void scanRegisterMapper(Configuration configuration, String[] mapperPathPrefixArray) {
        logger.info("the mapper scan path is [{}]", StringAssist.joinUseSeparator(mapperPathPrefixArray, ','));

        // 非线程安全的，详见“https://github.com/ronmamo/reflections/issues/81”
        Reflections reflections = new Reflections((Object[])mapperPathPrefixArray);

        Set<Class<? extends Mapper>> subTypes = reflections.getSubTypesOf(Mapper.class);

        if (CollectionAssist.isNotEmpty(subTypes)) {
            for (Class clz : subTypes) {
                if (clz.isInterface()) {
                    logger.info("mapper class {} is scanned and added", clz.getCanonicalName());
                    configuration.addMapper(clz);
                }
            }
        }
    }
}
