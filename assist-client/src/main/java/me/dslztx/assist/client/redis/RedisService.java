package me.dslztx.assist.client.redis;

import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.ScriptOutputType;
import com.lambdaworks.redis.cluster.api.async.RedisClusterAsyncCommands;

import me.dslztx.assist.algorithm.loadbalance.AbstractLoadBalancer;
import me.dslztx.assist.algorithm.loadbalance.LeastActiveLoadBalancer;
import me.dslztx.assist.algorithm.loadbalance.LoadBalancerEnum;
import me.dslztx.assist.util.*;

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

    public static RedisClusterAsyncCommands<String, String> obtainAsyncCommands(String redisClusterName) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        return client.getRedisAsyncConnection();
    }

    public static RedisFutureProxy<List<String>> mgetAsync(String redisClusterName, String... keys) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (ArrayAssist.isEmpty(keys)) {
            throw new RuntimeException("keys is empty");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<List<String>> result = client.getRedisAsyncConnection().mget(keys);

        return new RedisFutureProxy<List<String>>(result, client);
    }

    public static RedisFutureProxy<String> getAsync(String redisClusterName, String key) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<String> result = client.getRedisAsyncConnection().get(key);

        return new RedisFutureProxy<String>(result, client);
    }

    public static RedisFutureProxy<Map<String, String>> hgetallAsync(String redisClusterName, String key) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<Map<String, String>> result = client.getRedisAsyncConnection().hgetall(key);

        return new RedisFutureProxy<Map<String, String>>(result, client);
    }

    public static RedisFutureProxy<String> hgetAsync(String redisClusterName, String firstKey, String secondKey) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(firstKey) || StringAssist.isBlank(secondKey)) {
            throw new RuntimeException("key is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<String> result = client.getRedisAsyncConnection().hget(firstKey, secondKey);

        return new RedisFutureProxy<String>(result, client);
    }

    public static RedisFutureProxy<String> setAsync(String redisClusterName, String key, String value, long seconds) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        if (StringAssist.isBlank(value)) {
            throw new RuntimeException("value is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<String> result = client.getRedisAsyncConnection().setex(key, seconds, value);

        return new RedisFutureProxy<String>(result, client);
    }

    /**
     * @param redisClusterName
     * @param keyValues
     * @return RedisFuture<String> simple-string-reply always OK since MSET can't fail.
     */
    public static RedisFutureProxy<String> msetAsync(String redisClusterName, Map<String, String> keyValues) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (CollectionAssist.isEmpty(keyValues.keySet())) {
            throw new RuntimeException("keyValues is empty");
        }

        if (keyValues.containsKey(null)) {
            keyValues.remove(null);

            if (CollectionAssist.isEmpty(keyValues.keySet())) {
                throw new RuntimeException("keyValues is empty after excluding null key");
            }
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<String> result = client.getRedisAsyncConnection().mset(keyValues);

        return new RedisFutureProxy<String>(result, client);
    }

    public static RedisFutureProxy<Long> saddAsync(String redisClusterName, String key, String... values) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        if (ArrayAssist.isEmpty(values)) {
            throw new RuntimeException("value is empty");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<Long> result = client.getRedisAsyncConnection().sadd(key, values);

        return new RedisFutureProxy<Long>(result, client);
    }

    public static RedisFutureProxy<Boolean> expireAsync(String redisClusterName, String key, long seconds) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<Boolean> result = client.getRedisAsyncConnection().expire(key, seconds);

        return new RedisFutureProxy<Boolean>(result, client);
    }

    /**
     *
     * @return Boolean integer-reply specifically: true if the timeout was set. false if key does not exist or the
     *         timeout could not be set (see: EXPIRE).
     */
    /**
     * @param redisClusterName
     * @param key
     * @param expireAtTime 到期时间，不是TTL
     * @return RedisFuture<Long> integer-reply The number of keys that were removed.
     */
    public static RedisFutureProxy<Boolean> expireAtAsync(String redisClusterName, String key, long expireAtTime) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<Boolean> result = client.getRedisAsyncConnection().expireat(key, expireAtTime);

        return new RedisFutureProxy<Boolean>(result, client);
    }

    /**
     * @param redisClusterName
     * @param keys
     * @return RedisFuture<Long> integer-reply The number of keys that were removed.
     */
    public static RedisFutureProxy<Long> mdelAsync(String redisClusterName, String... keys) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (ArrayAssist.isEmpty(keys)) {
            throw new RuntimeException("keyValues is empty");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<Long> result = client.getRedisAsyncConnection().del(keys);

        return new RedisFutureProxy<Long>(result, client);
    }

    /**
     * @param redisClusterName
     * @param keys
     * @return 实际存在的个数
     */
    public static RedisFutureProxy<Long> existAsync(String redisClusterName, String... keys) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (ArrayAssist.isEmpty(keys)) {
            throw new RuntimeException("keyValues is empty");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<Long> result = client.getRedisAsyncConnection().exists(keys);

        return new RedisFutureProxy<Long>(result, client);
    }

    /**
     * @param redisClusterName
     * @param luaScriptSHA
     * @param key
     * @return Lua脚本执行结果值（这个方法是返回Long值）
     */
    public static RedisFutureProxy<Long> evalLuaScriptSHASingleKeyLongResultAsync(String redisClusterName,
        String luaScriptSHA, String key) {

        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(luaScriptSHA)) {
            throw new RuntimeException("lua script sha is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        // ScriptOutputType.INTEGER: Expects a Redis integer reply to be converted to a Java Long.
        // Redis中的Integer就对应Java中的Long类型
        RedisFuture<Long> result =
            client.getRedisAsyncConnection().evalsha(luaScriptSHA, ScriptOutputType.INTEGER, key);

        return new RedisFutureProxy<Long>(result, client);
    }

    /**
     * @param redisClusterName
     * @param luaScriptSHAs
     * @return SHA对应的Lua脚本是否存在
     */
    public static RedisFutureProxy<List<Boolean>> luaScriptSHAExistAsync(String redisClusterName,
        String... luaScriptSHAs) {

        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (ArrayAssist.isEmpty(luaScriptSHAs)) {
            throw new RuntimeException("lua script shas is empty");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<List<Boolean>> result = client.getRedisAsyncConnection().scriptExists(luaScriptSHAs);

        return new RedisFutureProxy<List<Boolean>>(result, client);
    }

    /**
     * @param redisClusterName
     * @param luaScript
     * @return Lua脚本的SHA值
     */
    public static RedisFutureProxy<String> luaScriptLoadAsync(String redisClusterName, String luaScript) {

        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(luaScript)) {
            throw new RuntimeException("lua script is blank");
        }

        LettuceAsyncClientProxy client =
            loadBalancer.select(LettuceAsyncClientFactory.obtainRedisClient(redisClusterName));

        if (ObjectAssist.isNull(client)) {
            throw new RuntimeException("no redis client");
        }

        RedisFuture<String> result = client.getRedisAsyncConnection().scriptLoad(luaScript);

        return new RedisFutureProxy<String>(result, client);
    }

    public static List<String> mgetSync(String redisClusterName, String... keys) {
        throw new UnsupportedOperationException("sync operation is not supported now");
    }

    public static String setSync(String redisClusterName, String key, String value, long seconds) {
        throw new UnsupportedOperationException("sync operation is not supported now");
    }

    public static String msetSync(String redisClusterName, Map<String, String> keyValues) {
        throw new UnsupportedOperationException("sync operation is not supported now");
    }

    public static Long rpush(String redisClusterName, String key, String... values) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        if (ArrayAssist.isEmpty(values)) {
            throw new RuntimeException("value is empty");
        }

        RedisClient redisClient = null;
        try {
            redisClient = RedisClientFactory.obtainRedisClient(redisClusterName);
            if (ObjectAssist.isNull(redisClient) || ObjectAssist.isNull(redisClient.getJedis())) {
                throw new RuntimeException("no redis client");
            }

            // JedisPool有个bug，这里只能用synchronized关键词临时解决
            synchronized (redisClient.getJedis()) {
                return redisClient.getJedis().rpush(key, values);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ObjectAssist.isNotNull(redisClient)) {
                redisClient.returnResource();
            }
        }
    }

    public static Long sadd(String redisClusterName, String key, String... values) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        if (ArrayAssist.isEmpty(values)) {
            throw new RuntimeException("value is empty");
        }

        RedisClient redisClient = null;
        try {
            redisClient = RedisClientFactory.obtainRedisClient(redisClusterName);
            if (ObjectAssist.isNull(redisClient) || ObjectAssist.isNull(redisClient.getJedis())) {
                throw new RuntimeException("no redis client");
            }

            // JedisPool有个bug，这里只能用synchronized关键词临时解决
            synchronized (redisClient.getJedis()) {
                return redisClient.getJedis().sadd(key, values);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ObjectAssist.isNotNull(redisClient)) {
                redisClient.returnResource();
            }
        }
    }

    public static Long expire(String redisClusterName, String key, long seconds) {
        if (StringAssist.isBlank(redisClusterName)) {
            throw new RuntimeException("redisClusterName is blank");
        }

        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        RedisClient redisClient = null;
        try {
            redisClient = RedisClientFactory.obtainRedisClient(redisClusterName);

            if (ObjectAssist.isNull(redisClient) || ObjectAssist.isNull(redisClient.getJedis())) {
                throw new RuntimeException("no redis client");
            }

            // JedisPool有个bug，这里只能用synchronized关键词临时解决
            synchronized (redisClient.getJedis()) {
                return redisClient.getJedis().expire(key, (int)seconds);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ObjectAssist.isNotNull(redisClient)) {
                redisClient.returnResource();
            }
        }
    }
}
