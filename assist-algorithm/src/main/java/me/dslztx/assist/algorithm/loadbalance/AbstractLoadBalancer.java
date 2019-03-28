package me.dslztx.assist.algorithm.loadbalance;

import java.util.List;

import me.dslztx.assist.util.CollectionAssist;

public abstract class AbstractLoadBalancer<T extends ServiceProvider> {

    public T select(List<T> serviceProviderList) {
        if (CollectionAssist.isEmpty(serviceProviderList)) {
            return null;
        }

        if (serviceProviderList.size() == 1) {
            return serviceProviderList.get(0);
        }

        return doSelect(serviceProviderList);
    }

    protected abstract T doSelect(List<T> serviceProviderList);
}
