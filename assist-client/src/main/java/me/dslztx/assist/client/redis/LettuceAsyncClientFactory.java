package me.dslztx.assist.client.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dslztx.assist.util.VarAssist;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.api.async.RedisClusterAsyncCommands;

import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.StringAssist;

@SuppressWarnings("Duplicates")
public class LettuceAsyncClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(LettuceAsyncClientFactory.class);

    private static final String CONFIG_FILE = "redis_client.properties";

    private static volatile boolean init = false;

    private static Map<String, List<LettuceAsyncClientProxy>> groupedClientPools =
        new HashMap<String, List<LettuceAsyncClientProxy>>();

    public static List<LettuceAsyncClientProxy> obtainRedisClient(String group) {
        if (!init) {
            init();
        }

        return groupedClientPools.get(group);
    }

    private static void init() {
        if (!init) {
            synchronized (LettuceAsyncClientFactory.class) {
                if (!init) {
                    try {
                        Configuration configuration = ConfigLoadAssist.propConfig(CONFIG_FILE);

                        String groups = configuration.getString("groups");
                        if (StringAssist.isBlank(groups)) {
                            throw new RuntimeException("no groups");
                        }

                        String[] groupArray = groups.split(",");
                        for (String group : groupArray) {
                            logger.info("generate client pools for group: " + group);
                            Configuration subConfiguration = configuration.subset(group);
                            groupedClientPools.put(group, generateClientPools(subConfiguration));
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                        throw new RuntimeException(e);
                    } finally {
                        init = true;
                    }
                }
            }
        }
    }

    private static List<LettuceAsyncClientProxy> generateClientPools(Configuration configuration) {
        List<LettuceAsyncClientProxy> clientPools = new ArrayList<LettuceAsyncClientProxy>();

        if (configuration == null) {
            return clientPools;
        }

        String servers = configuration.getString("redis.servers");
        if (StringAssist.isBlank(servers)) {
            logger.error("no redis servers");
            return clientPools;
        }

        servers = VarAssist.parse(servers);

        logger.info("the redis servers is {} after parse", servers);

        if (servers.toLowerCase().startsWith("master(") && servers.toLowerCase().endsWith(")")) {
            buildClusterConnections(servers, clientPools);
        } else {
            buildNonClusterConnections(servers, clientPools);
        }

        logger.info("clientPools size : " + clientPools.size());

        return clientPools;
    }

    private static void buildClusterConnections(String servers, List<LettuceAsyncClientProxy> clientPools) {

        servers = servers.substring(7, servers.length() - 1);

        if (StringAssist.isBlank(servers)) {
            logger.error("no redis servers");
        }

        String[] serverArray = servers.split(",");

        for (String server : serverArray) {
            try {
                com.lambdaworks.redis.cluster.RedisClusterClient client =
                    com.lambdaworks.redis.cluster.RedisClusterClient.create(RedisURI.create("redis://" + server));

                RedisClusterAsyncCommands<String, String> connection = client.connect().async();

                clientPools.add(new LettuceAsyncClientProxy(connection, server));
            } catch (Exception e) {
                logger.error(server, e);
            }
        }
    }

    private static void buildNonClusterConnections(String servers, List<LettuceAsyncClientProxy> clientPools) {
        String[] serverArray = servers.split(",");

        for (String server : serverArray) {
            try {
                com.lambdaworks.redis.RedisClient client =
                    com.lambdaworks.redis.RedisClient.create(RedisURI.create("redis://" + server));
                RedisClusterAsyncCommands<String, String> connection = client.connect().async();

                clientPools.add(new LettuceAsyncClientProxy(connection, server));
            } catch (Exception e) {
                logger.error(server, e);
            }
        }
    }
}
