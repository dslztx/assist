package me.dslztx.assist.client.redis;

import java.util.concurrent.atomic.AtomicInteger;

import com.lambdaworks.redis.cluster.api.async.RedisClusterAsyncCommands;

import me.dslztx.assist.algorithm.loadbalance.ServiceProvider;

public class LettuceAsyncClientProxy implements ServiceProvider {

    private RedisClusterAsyncCommands<String, String> lettuceAsyncConnection;

    private String server;

    private AtomicInteger activeCnt = new AtomicInteger(0);

    public LettuceAsyncClientProxy(RedisClusterAsyncCommands<String, String> redisAsyncConnection, String server) {
        this.lettuceAsyncConnection = redisAsyncConnection;
        this.server = server;
    }

    RedisClusterAsyncCommands<String, String> getRedisAsyncConnection() {
        return lettuceAsyncConnection;
    }

    public String getServer() {
        return server;
    }

    @Override
    public int getActive() {
        int result = activeCnt.get();

        if (result < 0) {
            // 重排序，有可能先减，因此结果可能为0的
            return 0;
        }
        return result;
    }

    @Override
    public int getWeight() {
        // 这里设置权重都为0
        return 0;
    }

    void incActive() {
        activeCnt.incrementAndGet();
    }

    void decActive() {
        activeCnt.decrementAndGet();
    }
}
