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

public class MQProducerManager extends MQClientManager {
    private static final Logger logger = LoggerFactory.getLogger(MQProducerManager.class);

    /**
     * MQ节点地址和端口号列表
     */
    CopyOnWriteArrayList<String> mqNodes = new CopyOnWriteArrayList<String>();

    /**
     * "MQ节点地址和端口号"与"生产者对象"关系
     */
    ConcurrentHashMap<String, ProducerTuple> map = new ConcurrentHashMap<String, ProducerTuple>();

    /**
     * 生产者对象列表
     */
    CopyOnWriteArrayList<ProducerTuple> producers = new CopyOnWriteArrayList<ProducerTuple>();

    /**
     * "mqNodes"或者"producers"的大小，这两者的大小是一致的
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


    public MQProducerManager(String mqNodeGroup, String username, String password, String destination, DESTTYPE type) {
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

        List<ProducerTuple> toDelProducers = new ArrayList<ProducerTuple>();
        for (String delMQNode : toDel) {
            toDelProducers.add(map.get(delMQNode));
            map.remove(delMQNode);
        }

        size.addAndGet((-1) * toDel.size());

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

                ProducerTuple tuple = new ProducerTuple(connection, session, producer);
                map.put(newMQNode, tuple);

                toAddProducers.add(tuple);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        producers.addAll(toAddProducers);

        size.addAndGet(toAddProducers.size());
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
    public MessageProducer nextProducer() {
        index.set(index.get() + 1);
        if (index.get().equals(size.get())) {
            index.set(0);
        }

        if (index.get().compareTo(size.get()) >= 0) {
            return null;
        }

        return producers.get(index.get()).getProducer();
    }
}


class ProducerTuple {
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
     * 生产者对象
     */
    MessageProducer producer;

    public ProducerTuple(Connection connection, Session session, MessageProducer producer) {
        this.connection = connection;
        this.session = session;
        this.producer = producer;
    }

    public MessageProducer getProducer() {
        return producer;
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
