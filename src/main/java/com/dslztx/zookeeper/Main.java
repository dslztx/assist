package com.dslztx.zookeeper;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        ZooKeeperSession session1 = (ZooKeeperSession) beanFactory.getBean("zooKeeperSession");
        System.out.println(session1.getHostPort());
        System.out.println(session1.getSessionTimeout());

        ZooKeeperSession session2 = (ZooKeeperSession) beanFactory.getBean("zooKeeperSession");
        System.out.println(session2.getHostPort());
        System.out.println(session2.getSessionTimeout());
    }
}
