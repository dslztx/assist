package me.dslztx.assist.component.loadbalancer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author dslztx
 */
public class JedisProxy {

  Jedis jedis;
  JedisPool jedisPool;

  public JedisProxy(Jedis jedis, JedisPool jedisPool) {
    this.jedis = jedis;
    this.jedisPool = jedisPool;
  }

  public void returnJedis() {
    this.jedisPool.returnResourceObject(this.jedis);
  }
}
