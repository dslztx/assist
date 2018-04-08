package me.dslztx.assist.client.redis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.dslztx.assist.util.StringUtils;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings("Duplicates")
public class RedisClientFactory {

  private static final Logger logger = LoggerFactory.getLogger(RedisClientFactory.class);

  private static final int MAX_TOTAL = 30;
  private static final int MAX_IDLE = 10;
  private static final int MAX_WAIT_MILLIS = 1000;
  private static final boolean TEST_ON_BORROW = false;
  private static final boolean TEST_WHILE_IDLE = false;
  private static final int TIMEOUT = 5000;


  private static final String CONFIG_FILE = "redis.properties";

  private static volatile boolean init = false;

  private static List<JedisPoolProxy> jedisPools = new ArrayList<JedisPoolProxy>();

  public static RedisClient obtainRedisClient() {
    if (!init) {
      init();
    }

    int idx = (int) (Math.random() * jedisPools.size());

    for (int index = idx; index < jedisPools.size(); index++) {
      if (!jedisPools.get(index).isBlocked()) {
        try {
          Jedis x = jedisPools.get(index).getPool().getResource();

          if (x != null) {
            return new RedisClient(x, jedisPools.get(index));
          }
        } catch (Throwable e) {
          logger.error("", e);
          jedisPools.get(index).setBlockedTime(System.currentTimeMillis());
        }
      }
    }

    for (int index = 0; index < idx; index++) {
      if (!jedisPools.get(index).isBlocked()) {
        try {
          Jedis x = jedisPools.get(index).getPool().getResource();

          if (x != null) {
            return new RedisClient(x, jedisPools.get(index));
          }
        } catch (Throwable e) {
          logger.error("", e);
          jedisPools.get(index).setBlockedTime(System.currentTimeMillis());
        }
      }
    }

    return null;
  }

  private static void init() {
    if (!init) {
      synchronized (RedisClientFactory.class) {
        if (!init) {
          try {
            Configurations configs = new Configurations();

            Configuration configuration = configs.properties(new File(CONFIG_FILE));

            String servers = configuration.getString("redis.servers");
            if (StringUtils.isBlank(servers)) {
              throw new RuntimeException("no redis servers");
            }

            String[] serverArray = servers.split(",");

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setTestWhileIdle(TEST_WHILE_IDLE);

            for (String server : serverArray) {
              try {
                String host = server.split(":")[0];
                int port = Integer.parseInt(server.split(":")[1]);

                JedisPoolProxy proxy = new JedisPoolProxy(server,
                    new JedisPool(config, host, port, TIMEOUT));

                jedisPools.add(proxy);
              } catch (Exception e) {
                logger.error(server, e);
              }
            }

            logger.info("jedisPools size : " + jedisPools.size());
          } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
          } finally {
            init = true;
          }
        }
      }
    }
  }
}
