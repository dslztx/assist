package me.dslztx.assist.client.redis;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClient {

    private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

    private Jedis jedis;

    private JedisPoolProxy jedisPoolProxy;

    public RedisClient(Jedis jedis, JedisPoolProxy jedisPoolProxy) {
        this.jedis = jedis;
        this.jedisPoolProxy = jedisPoolProxy;
    }

    public void returnResource() {
        try {
            if (jedis != null && jedisPoolProxy != null) {
                jedisPoolProxy.getPool().returnResource(jedis);
            }
        } catch (Exception e) {
            logger.error("", e);

            if (jedis != null && jedisPoolProxy != null) {
                jedisPoolProxy.getPool().returnBrokenResource(jedis);
            }
        } finally {
            jedis = null;
            jedisPoolProxy = null;
        }
    }

    public Jedis getJedis() {
        return jedis;
    }
}

class JedisPoolProxy {

    private static final Logger logger = LoggerFactory.getLogger(JedisPoolProxy.class);

    // 冻结10秒
    private static final long BLOCKED_TIME = 10 * 1000;

    String server;

    JedisPool pool;

    AtomicLong blockedTime = new AtomicLong(-1);

    public JedisPoolProxy(String server, JedisPool pool) {
        this.server = server;
        this.pool = pool;
    }

    public boolean isBlocked() {
        if (blockedTime.get() < 0) {
            return false;
        }

        if (System.currentTimeMillis() - blockedTime.get() > BLOCKED_TIME) {
            logger.info("server " + server + " recovers");
            blockedTime.set(-1);
            return false;
        }

        return true;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public long getBlockedTime() {
        return blockedTime.get();
    }

    public void setBlockedTime(long blockedTime) {
        logger.info("server " + server + " is blocked");
        this.blockedTime.set(blockedTime);
    }
}
