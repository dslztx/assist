package com.dslztx.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author dslztx
 * @date 2015年07月03日
 */
public class RedisUtils {
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("localhost", 6379);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set("rediskey1", "redisvalue1");
            jedis.set("rediskey2", "redisvalue2");
            System.out.println(jedis.get("rediskey1"));
            System.out.println(jedis.get("rediskey2"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
        jedisPool.destroy();
    }
}
