package com.dslztx.zookeeper;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MQConsumerManager extends MQClientManager {
    private static final Logger logger = LoggerFactory.getLogger(MQProducerManager.class);

    /**
     * 读写锁
     */
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();


    /**
     * MQ节点地址和端口号列表
     */
    List<String> mqNodes = new ArrayList<String>();

    /**
     * "MQ节点地址和端口号"与"消费者对象"关系
     */
    Map<String, ConsumerTuple> map = new HashMap<String, ConsumerTuple>();

    /**
     * 消费者对象列表
     */
    List<ConsumerTuple> consumers = new ArrayList<ConsumerTuple>();


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

    @Override
    public void init() throws IOException {
        logger.info("In MQConsumerManager,initializing");

        super.init();
    }

    /**
     * 在MQNodesSync中回调
     * 
     * @param mqNodesList
     */
    @Override
    public void syncMQNodes(List<String> mqNodesList) {
        rwl.writeLock().lock();
        try {
            logger.info("In MQConsumerManager,callback's content is " + mqNodesList);

            // 需要增加的MQ节点
            List<String> toAdd = obtainToAdd(mqNodesList);

            logger.info("In MQConsumerManager,callback's toAdd content is " + toAdd);

            // 需要移除的MQ节点
            List<String> toDel = obtainToDel(mqNodesList);

            logger.info("In MQConsumerManager,callback's toDel content is " + toDel);

            // 处理增加
            add(toAdd);

            logger.info("In MQConsumerManager,finish addition");

            // 处理移除
            remove(toDel);

            logger.info("In MQConsumerManager,finish deletion");
        } finally {
            rwl.writeLock().unlock();
        }
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

                ConsumerTuple tuple = new ConsumerTuple(newMQNode, connection, session, consumer);
                map.put(newMQNode, tuple);

                toAddConsumers.add(tuple);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        consumers.addAll(toAddConsumers);
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
    public ConsumerTuple nextConsumer() {
        rwl.readLock().lock();
        try {
            if (consumers.size() == 0) {
                return null;
            }

            index.set(index.get() + 1);
            if (index.get().compareTo(consumers.size()) >= 0) {
                index.set(0);
            }

            return consumers.get(index.get());
        } finally {
            rwl.readLock().unlock();
        }
    }
}


class ConsumerTuple {
    private static final Logger logger = LoggerFactory.getLogger(ProducerTuple.class);

    /**
     * MQ节点地址和端口号
     */
    String hostPort;

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

    public ConsumerTuple(String hostPort, Connection connection, Session session, MessageConsumer consumer) {
        this.hostPort = hostPort;
        this.connection = connection;
        this.session = session;
        this.consumer = consumer;
    }

    public String getHostPort() {
        return hostPort;
    }

    public Session getSession() {
        return session;
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
