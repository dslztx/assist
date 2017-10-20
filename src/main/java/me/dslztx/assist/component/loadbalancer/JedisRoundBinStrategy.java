package me.dslztx.assist.component.loadbalancer;

import me.dslztx.assist.util.CollectionUtils;

/**
 * @author dslztx
 */
public class JedisRoundBinStrategy extends ServiceLoadBalancerStrategy<JedisPoolProxy> {

  private int pointer = 0;
  private Object lock = new Object();

  public JedisPoolProxy choose() {
    if (CollectionUtils.isEmpty(services)) {
      return null;
    }

    //for concurrence
    synchronized (lock) {
      return services.get(pointer++ % services.size());
    }
  }
}
