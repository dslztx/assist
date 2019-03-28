package me.dslztx.assist.client.redis;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.lambdaworks.redis.RedisFuture;

public class RedisFutureProxy<V> {

    RedisFuture<V> redisFuture;

    LettuceAsyncClientProxy proxy;

    public RedisFutureProxy(RedisFuture<V> redisFuture, LettuceAsyncClientProxy proxy) {
        this.redisFuture = redisFuture;
        this.proxy = proxy;
    }

    public LettuceAsyncClientProxy getProxy() {
        return proxy;
    }

    public String obtainServer() {
        return proxy.getServer();
    }

    public V get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            return redisFuture.get(timeout, timeUnit);
        } finally {
            proxy.decActive();
        }
    }
}
