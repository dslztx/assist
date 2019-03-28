package me.dslztx.assist.algorithm.loadbalance;

import java.util.List;

public abstract class AbstractLoadBalancer<T extends ServiceProvider> {

    public T select(List<T> serviceProviderList) {
        if (serviceProviderList == null || serviceProviderList.size() == 0) {
            return null;
        }

        if (serviceProviderList.size() == 1) {
            return serviceProviderList.get(0);
        }

        return doSelect(serviceProviderList);
    }

    protected abstract T doSelect(List<T> serviceProviderList);
}
