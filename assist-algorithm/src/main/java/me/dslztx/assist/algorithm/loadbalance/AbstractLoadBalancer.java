package me.dslztx.assist.algorithm.loadbalance;

import java.util.List;

public abstract class AbstractLoadBalancer {

    public ServiceProvider select(List<ServiceProvider> serviceProviderList) {
        if (serviceProviderList == null || serviceProviderList.size() == 0) {
            return null;
        }

        if (serviceProviderList.size() == 1) {
            return serviceProviderList.get(0);
        }

        return doSelect(serviceProviderList);
    }

    protected abstract ServiceProvider doSelect(List<ServiceProvider> serviceProviderList);
}
