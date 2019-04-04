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
            // 异步形式请求服务节点时，连接数加减方案最完美的实现是在“真正请求和真正接收到结果处做”，这里这样实现不甚完美，而且当用户不调用get()方法时，就不做连接数的加减了
            proxy.incActive();

            return redisFuture.get(timeout, timeUnit);
        } finally {
            proxy.decActive();
        }
    }
}
