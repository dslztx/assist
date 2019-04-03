package me.dslztx.assist.client.redis;

import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdaworks.redis.RedisFuture;

import me.dslztx.assist.algorithm.loadbalance.AbstractLoadBalancer;
import me.dslztx.assist.algorithm.loadbalance.LeastActiveLoadBalancer;
import me.dslztx.assist.algorithm.loadbalance.LoadBalancerEnum;
import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.ObjectAssist;
import me.dslztx.assist.util.StringAssist;

public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private static final String CONFIG_FILE = "redis_service.properties";

    private static AbstractLoadBalancer<LettuceAsyncClientProxy> loadBalancer = null;

    static {
        initLoadBalancer();
    }

    private static void initLoadBalancer() {
        try {
            Configuration configuration = ConfigLoadAssist.propConfig(CONFIG_FILE);

            String loadBalancerType = configuration.getString("loadbalancer");

            if (StringAssist.isBlank(loadBalancerType)) {
                throw new RuntimeException("no loadbalancer type specified");
            }

            if (LoadBalancerEnum.Random.toString().equalsIgnoreCase(loadBalancerType)) {
            } else if (LoadBalancerEnum.RoundRobin.toString().equalsIgnoreCase(loadBalancerType)) {
            } else if (LoadBalancerEnum.LeastActive.toString().equalsIgnoreCase(loadBalancerType)) {
                loadBalancer = new LeastActiveLoadBalancer<LettuceAsyncClientProxy>();
            } else if (LoadBalancerEnum.ConsistentHash.toString().equalsIgnoreCase(loadBalancerType)) {
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            if (ObjectAssist.isNull(loadBalancer)) {
                loadBalancer = new LeastActiveLoadBalancer<LettuceAsyncClientProxy>();
            }

            logger.info("used load balancer is {}", loadBalancer.getClass().getSimpleName());
        }
    }

    public static RedisFutureProxy<List<String>> mgetAsync(String redisClusterName, String... keys) {

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            return null;
        }

        RedisFuture<List<String>> result = client.getRedisAsyncConnection().mget(keys);

        client.incActive();

        return new RedisFutureProxy<List<String>>(result, client);
    }

    public static RedisFutureProxy<String> setAsync(String redisClusterName, String key, String value) {

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            return null;
        }

        RedisFuture<String> result = client.getRedisAsyncConnection().set(key, value);

        client.incActive();

        return new RedisFutureProxy<String>(result, client);
    }

    // TODO 最少连接数如何定义呢，异步的根本不能定义这个指标，否则就得依赖于用户的是否get了呢
    public static RedisFutureProxy<String> msetAsync(String redisClusterName, Map<String, String> keyValues) {
        if (ObjectAssist.isNull(keyValues) || CollectionAssist.isEmpty(keyValues.keySet())) {
            return null;
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            return null;
        }

        RedisFuture<String> result = client.getRedisAsyncConnection().mset(keyValues);

        return new RedisFutureProxy<String>(result, client);
    }

    public static List<String> mgetSync(String redisClusterName, String... keys) {
        throw new UnsupportedOperationException("sync operation is not supported now");
    }

    public static String setSync(String redisClusterName, String key, String value) {
        throw new UnsupportedOperationException("sync operation is not supported now");
    }

    public static String msetSync(String redisClusterName, Map<String, String> keyValues) {
        throw new UnsupportedOperationException("sync operation is not supported now");
    }
}
