package me.dslztx.assist.component.loadbalancer;

import redis.clients.jedis.JedisPool;

/**
 * @author dslztx
 */
public class JedisPoolProxy implements Service {

  JedisPool jedisPool;

  public JedisProxy obtainJedis() {
    return new JedisProxy(jedisPool.getResource(), jedisPool);
  }
}
