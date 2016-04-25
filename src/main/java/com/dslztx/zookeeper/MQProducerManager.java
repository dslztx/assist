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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MQProducerManager extends MQClientManager {
    private static final Logger logger = LoggerFactory.getLogger(MQProducerManager.class);

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();

    /**
     * MQ节点地址和端口号列表
     */
    List<String> mqNodes = new ArrayList<String>();

    /**
     * "MQ节点地址和端口号"与"生产者对象"关系
     */
    Map<String, ProducerTuple> map = new HashMap<String, ProducerTuple>();

    /**
     * 生产者对象列表
     */
    List<ProducerTuple> producers = new ArrayList<ProducerTuple>();

    /**
     * 索引器
     */
    ThreadLocal<Integer> index = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };


    public MQProducerManager(String mqNodeGroup, String username, String password, String destination, DESTTYPE type) {
        super(mqNodeGroup, username, password, destination, type);
    }

    @Override
    public void init() throws IOException {
        logger.info("In MQProducerManager,initializing");

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
            logger.info("In MQProducerManager,callback's content is " + mqNodesList);

            // 需要增加的MQ节点
            List<String> toAdd = obtainToAdd(mqNodesList);

            logger.info("In MQProducerManager,callback's toAdd content is " + toAdd);

            // 需要移除的MQ节点
            List<String> toDel = obtainToDel(mqNodesList);

            logger.info("In MQProducerManager,callback's toDel content is " + toDel);

            // 处理增加
            add(toAdd);

            logger.info("In MQProducerManager,finish addition");

            // 处理移除
            remove(toDel);

            logger.info("In MQProducerManager,finish deletion");
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

        List<ProducerTuple> toDelProducers = new ArrayList<ProducerTuple>();
        for (String delMQNode : toDel) {
            toDelProducers.add(map.get(delMQNode));
            map.remove(delMQNode);
        }

        producers.removeAll(toDelProducers);

        // 释放资源
        for (ProducerTuple tuple : toDelProducers) {
            tuple.destroy();
        }
    }

    /**
     * 处理增加，与MQ节点建立新连接，新会话，生成新的生产者对象
     * 
     * @param toAdd
     */
    private void add(List<String> toAdd) {
        if (toAdd.size() == 0)
            return;

        mqNodes.addAll(toAdd);
        List<ProducerTuple> toAddProducers = new ArrayList<ProducerTuple>();
        for (String newMQNode : toAdd) {
            try {
                MessageProducer producer = null;

                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(newMQNode);
                Connection connection = factory.createConnection(username, password);
                connection.start();

                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                if (type == DESTTYPE.QUEUE) {
                    Destination dest = new ActiveMQQueue(destination);
                    producer = session.createProducer(dest);
                } else if (type == DESTTYPE.TOPIC) {
                    Topic topic = session.createTopic(destination);
                    producer = session.createProducer(topic);
                }

                ProducerTuple tuple = new ProducerTuple(newMQNode, connection, session, producer);
                map.put(newMQNode, tuple);

                toAddProducers.add(tuple);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        producers.addAll(toAddProducers);
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
     * 依次获取下一个生产者对象
     * 
     * @return
     */
    public ProducerTuple nextProducerTuple() {
        rwl.readLock().lock();
        try {
            index.set(index.get() + 1);
            if (index.get().equals(producers.size())) {
                index.set(0);
            }

            if (index.get().compareTo(producers.size()) >= 0) {
                return null;
            }

            return producers.get(index.get());
        } finally {
            rwl.readLock().unlock();
        }
    }
}


class ProducerTuple {
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
     * 生产者对象
     */
    MessageProducer producer;

    public ProducerTuple(String hostPort, Connection connection, Session session, MessageProducer producer) {
        this.hostPort = hostPort;
        this.connection = connection;
        this.session = session;
        this.producer = producer;
    }

    public String getHostPort() {
        return hostPort;
    }

    public MessageProducer getProducer() {
        return producer;
    }

    public Session getSession() {
        return session;
    }

    public void destroy() {
        if (producer != null) {
            try {
                producer.close();
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
