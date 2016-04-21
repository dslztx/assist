package com.dslztx.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class MQNodesSync {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * ZooKeeper中维护MQ地址和端口号数据结构的顶层路径
     */
    private static final String DIR = "/mqs";

    /**
     * Spring中配置的ZooKeeperSession对象的名称
     */
    private static final String BEANNAME = "zooKeeperSession";

    /**
     * 管理ZooKeeper Session的对象
     */
    private ZooKeeperSession session;

    /**
     * MQ节点组名
     */
    private String mqNodeGroup;

    /**
     * MQ客户端实例管理器类实例
     */
    private MQClientManager manager;

    public MQNodesSync(String mqNodeGroup) {
        try {
            BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");

            // 从Spring中获取ZooKeeperSession对象
            this.session = (ZooKeeperSession) beanFactory.getBean(BEANNAME);

            this.mqNodeGroup = mqNodeGroup;
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 设置回调对象
     *
     * @param manager
     */
    public void setMQClientManager(MQClientManager manager) {
        this.manager = manager;
    }

    /**
     * 开始ZooKeeper Session
     * 
     * @throws IOException
     */
    public void startSession() throws IOException {
        session.startSession();
    }

    /**
     * 开始ZooKeeper Session，并获取最新的MQ节点地址和端口号列表
     * 
     * @throws IOException
     */
    public void start() throws IOException {
        // 开始ZooKeeper Session
        startSession();

        // 获取最新的MQ节点地址和端口号列表
        getMQNodes();
    }

    /**
     * 异步获取最新的MQ节点地址和端口号列表
     */
    void getMQNodes() {
        // 在异步获取时，同时设置一个监听器，当监听到该路径下的子节点数量变化时，会自动回调该监听器下的处理方法
        session.getZk().getChildren(DIR + "/" + this.mqNodeGroup, mqNodesChangeWatcher, getMQNodesCallback, null);
    }

    Watcher mqNodesChangeWatcher = new Watcher() {
        /**
         * 监听到路径下的子节点数量变化时，自动回调的处理方法
         * 
         * @param e
         */
        public void process(WatchedEvent e) {
            if (e.getType() == Event.EventType.NodeChildrenChanged) {
                // 获取最新的MQ节点地址和端口号列表
                getMQNodes();
            }
        }
    };

    AsyncCallback.ChildrenCallback getMQNodesCallback = new AsyncCallback.ChildrenCallback() {

        /**
         * 异步获取最新的MQ节点地址和端口号列表的回调方法
         */
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    getMQNodes();
                    break;
                case OK:
                    logger.info("Succesfully got a list of mqnodes: " + children.size() + " mqnodes");

                    // 回调
                    manager.syncMQNodes(children);

                    break;
                default:
                    logger.error("getMQNodes failed", KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };
}
