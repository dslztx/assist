package me.dslztx.assist.component.loadbalancer;

import java.util.List;

/**
 * @author dslztx
 */
public class ServiceLoadBalancer<T extends Service> {

  ServiceLoadBalancerStrategy<T> serviceLoadBalancerStrategy;

  public ServiceLoadBalancer(List<T> services,
      ServiceLoadBalancerStrategy<T> serviceLoadBalancerStrategy) {
    this.serviceLoadBalancerStrategy = serviceLoadBalancerStrategy;
    this.serviceLoadBalancerStrategy.setServices(services);
  }

  public T choose() {
    return this.serviceLoadBalancerStrategy.choose();
  }
}
