package me.dslztx.assist.client.zookeeper;

import org.apache.commons.configuration2.Configuration;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.StringAssist;

public class ZooKeeperClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperClientFactory.class);

    private static final String CONFIG_FILE = "zookeeper.properties";

    private static volatile boolean init = false;

    private static CuratorFramework curatorFramework;

    public static CuratorFramework obtainZooKeeperClient() {
        if (!init) {
            init();
        }

        return curatorFramework;
    }

    private static void init() {
        if (!init) {
            synchronized (ZooKeeperClientFactory.class) {
                if (!init) {
                    try {
                        Configuration configuration = ConfigLoadAssist.propConfig(CONFIG_FILE);

                        String addresses = configuration.getString("zookeeper.curator.addresses");
                        if (StringAssist.isBlank(addresses)) {
                            throw new RuntimeException("no addresses");
                        }

                        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                        curatorFramework = CuratorFrameworkFactory.newClient(addresses, retryPolicy);

                        curatorFramework.start();
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
}
