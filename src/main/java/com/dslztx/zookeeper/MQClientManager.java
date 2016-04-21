package com.dslztx.zookeeper;

import java.io.IOException;
import java.util.List;

public abstract class MQClientManager {
    /**
     * MQ节点组名
     */
    protected String mqNodeGroup;

    /**
     * MQ节点用户名
     */
    protected String username;

    /**
     * MQ节点密码
     */
    protected String password;

    /**
     * Queue或者Topic的名称
     */
    protected String destination;

    /**
     * 标识是"Queue"还是"Topic"
     */
    protected DESTTYPE type;

    public MQClientManager(String mqNodeGroup, String username, String password, String destination, DESTTYPE type) {
        this.mqNodeGroup = mqNodeGroup;
        this.username = username;
        this.password = password;
        this.destination = destination;
        this.type = type;
    }

    /**
     * 与ZooKeeper集群建立“会话”，当MQ节点地址和端口号列表发生变化时，会有异步线程回调“syncMQNodes(List<String> children)”方法
     * 
     * @throws IOException
     */
    public void init() throws IOException {
        MQNodesSync mqNodesSync = new MQNodesSync(this.mqNodeGroup);

        // 设置回调对象
        mqNodesSync.setMQClientManager(this);

        mqNodesSync.start();
    }

    public abstract void syncMQNodes(List<String> children);
}


enum DESTTYPE {
    TOPIC, QUEUE;
}
