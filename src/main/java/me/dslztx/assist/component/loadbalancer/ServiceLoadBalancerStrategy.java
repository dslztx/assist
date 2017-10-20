package me.dslztx.assist.component.loadbalancer;

import java.util.List;

/**
 * @author dslztx
 */
public abstract class ServiceLoadBalancerStrategy<T extends Service> {

  List<T> services;

  public void setServices(List<T> services) {
    this.services = services;
  }

  public abstract T choose();
}
