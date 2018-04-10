package me.dslztx.assist.dao;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoFactory {

  private static final Logger logger = LoggerFactory.getLogger(DaoFactory.class);

  private static ConcurrentHashMap<DataSource, ConcurrentHashMap<Class<? extends Dao>, Dao>> daoInstanceCache = new ConcurrentHashMap<DataSource, ConcurrentHashMap<Class<? extends Dao>, Dao>>();

  public static <T extends Dao> T obtainDao(DataSource dataSource, Class<T> clz) {
    if (dataSource == null) {
      throw new RuntimeException("no datasource");
    }

    if (clz == null) {
      throw new RuntimeException("no dao class");
    }

    if (daoInstanceCache.get(dataSource) == null) {
      daoInstanceCache.putIfAbsent(dataSource, new ConcurrentHashMap<Class<? extends Dao>, Dao>());
    }

    if (daoInstanceCache.get(dataSource).get(clz) == null) {
      try {
        @SuppressWarnings("JavaReflectionMemberAccess")
        Constructor<T> constructor = clz.getConstructor(DataSource.class);
        T obj = constructor.newInstance(dataSource);

        daoInstanceCache.get(dataSource).putIfAbsent(clz, obj);
      } catch (Exception e) {
        logger.error("", e);
      }
    }

    return clz.cast(daoInstanceCache.get(dataSource).get(clz));
  }
}
