package com.dslztx.zookeeper;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MQConsumerManager extends MQClientManager {
    private static final Logger logger = LoggerFactory.getLogger(MQProducerManager.class);

    /**
     * MQ节点地址和端口号列表
     */
    CopyOnWriteArrayList<String> mqNodes = new CopyOnWriteArrayList<String>();

    /**
     * "MQ节点地址和端口号"与"消费者对象"关系
     */
    ConcurrentHashMap<String, ConsumerTuple> map = new ConcurrentHashMap<String, ConsumerTuple>();

    /**
     * 消费者对象列表
     */
    CopyOnWriteArrayList<ConsumerTuple> consumers = new CopyOnWriteArrayList<ConsumerTuple>();

    /**
     * "mqNodes"或者"consumers"的大小，这两者的大小是一致的
     */
    AtomicInteger size = new AtomicInteger(0);

    /**
     * 索引器
     */
    ThreadLocal<Integer> index = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };


    public MQConsumerManager(String mqNodeGroup, String username, String password, String destination, DESTTYPE type) {
        super(mqNodeGroup, username, password, destination, type);
    }

    /**
     * 在MQNodesSync中回调
     * 
     * @param mqNodesList
     */
    @Override
    public void syncMQNodes(List<String> mqNodesList) {
        // 需要增加的MQ节点
        List<String> toAdd = obtainToAdd(mqNodesList);

        // 需要移除的MQ节点
        List<String> toDel = obtainToDel(mqNodesList);

        // 处理增加
        add(toAdd);

        // 处理移除
        remove(toDel);
    }

    /**
     * 处理移除，释放资源
     * 
     * @param toDel
     */
    private void remove(List<String> toDel) {
        if (toDel.size() == 0)
            return;

        mqNodes.removeAll(toDel);

        List<ConsumerTuple> toDelConsumers = new ArrayList<ConsumerTuple>();
        for (String delMQNode : toDel) {
            toDelConsumers.add(map.get(delMQNode));
            map.remove(delMQNode);
        }

        size.addAndGet((-1) * toDel.size());

        consumers.removeAll(toDelConsumers);

        // 释放资源
        for (ConsumerTuple tuple : toDelConsumers) {
            tuple.destroy();
        }
    }

    /**
     * 处理增加，与MQ节点建立新连接，新会话，生成新的消费者对象
     * 
     * @param toAdd
     */
    private void add(List<String> toAdd) {
        if (toAdd.size() == 0)
            return;

        mqNodes.addAll(toAdd);
        List<ConsumerTuple> toAddConsumers = new ArrayList<ConsumerTuple>();
        for (String newMQNode : toAdd) {
            try {
                MessageConsumer consumer = null;

                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(newMQNode);
                Connection connection = factory.createConnection(username, password);
                connection.start();

                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                if (type == DESTTYPE.QUEUE) {
                    Destination dest = new ActiveMQQueue(destination);
                    consumer = session.createConsumer(dest);
                } else if (type == DESTTYPE.TOPIC) {
                    Topic topic = session.createTopic(destination);
                    consumer = session.createConsumer(topic);
                }

                ConsumerTuple tuple = new ConsumerTuple(connection, session, consumer);
                map.put(newMQNode, tuple);

                toAddConsumers.add(tuple);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        consumers.addAll(toAddConsumers);

        size.addAndGet(toAddConsumers.size());
    }

    private List<String> obtainToAdd(List<String> mqNodesList) {
        List<String> toAdd = new ArrayList<String>();
        for (String mqNode : mqNodesList) {
            if (!mqNodes.contains(mqNode)) {
                toAdd.add(mqNode);
            }
        }
        return toAdd;
    }

    private List<String> obtainToDel(List<String> mqNodesList) {
        List<String> toDel = new ArrayList<String>();
        for (String mqNode : mqNodes) {
            if (!mqNodesList.contains(mqNode)) {
                toDel.add(mqNode);
            }
        }
        return toDel;
    }

    /**
     * 依次获取下一个消费者对象
     * 
     * @return
     */
    public MessageConsumer nextConsumer() {
        index.set(index.get() + 1);
        if (index.get().equals(size.get())) {
            index.set(0);
        }

        if (index.get().compareTo(size.get()) >= 0) {
            return null;
        }

        return consumers.get(index.get()).getConsumer();
    }
}


class ConsumerTuple {
    private static final Logger logger = LoggerFactory.getLogger(ProducerTuple.class);

    /**
     * 与MQ节点的连接
     */
    Connection connection;

    /**
     * 与MQ节点的会话
     */
    Session session;

    /**
     * 消费者对象
     */
    MessageConsumer consumer;

    public ConsumerTuple(Connection connection, Session session, MessageConsumer consumer) {
        this.connection = connection;
        this.session = session;
        this.consumer = consumer;
    }

    public MessageConsumer getConsumer() {
        return consumer;
    }

    /**
     * 释放资源
     */
    public void destroy() {
        if (consumer != null) {
            try {
                consumer.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
}
