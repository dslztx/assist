package com.dslztx.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 管理ZooKeeper Session的对象，同时也是一个监听器对象
 */
public class ZooKeeperSession implements Watcher {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperSession.class);

    /**
     * ZooKeeper集群的地址和端口号
     */
    private String hostPort;

    /**
     * ZooKeeper　Server判定session超期的时间间隔
     */
    private Integer sessionTimeout;

    /**
     * ZooKeeper Session对象
     */
    private ZooKeeper zk;

    /**
     * 表示ZooKeeper Session的状态
     */
    private volatile boolean connected = false;

    /**
     * 表示ZooKeeper Session是否超期
     */
    private volatile boolean expired = false;

    public ZooKeeperSession() {}

    /**
     * 监听器的回调方法
     * 
     * @param watchedEvent
     */
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.None) {
            if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                logger.info("session is created successfully");

                connected = true;
            } else if (watchedEvent.getState() == Event.KeeperState.Disconnected) {
                logger.info("session is disconnected");

                connected = false;
            } else if (watchedEvent.getState() == Event.KeeperState.Expired) {
                logger.info("session expired");

                expired = true;
                connected = false;
            }
        }
    }

    public void startSession() throws IOException {
        zk = new ZooKeeper(hostPort, sessionTimeout, this);
    }

    public void stopSession() throws InterruptedException {
        zk.close();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
