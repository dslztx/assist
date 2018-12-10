package me.dslztx.assist.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(BeanAssistTest.class);

    static {
        LoggerAssist.openLogAllConsole();
    }

    @Test
    public void convertFromMap() {
        try {
            User user = new User();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "Alice");
            map.put("age", 15);

            BeanAssist.convertFromMap(user, map, false);

            Assert.assertTrue(user.getName().equals("Alice"));
            Assert.assertTrue(user.getAge() == 15);

            User user2 = new User();
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("NAME", "Alice");
            map2.put("AGE", 15);

            BeanAssist.convertFromMap(user2, map2, true);

            Assert.assertTrue(user2.getName().equals("Alice"));
            Assert.assertTrue(user2.getAge() == 15);

            User user3 = new User();
            Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("NAME", "Alice");
            map3.put("AGE", 15);

            BeanAssist.convertFromMap(user3, map3, false);

            Assert.assertNull(user3.getName());
            Assert.assertTrue(user3.getAge() == 0);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void convertFromConfiguration() {
        try {
            User user = new User();
            Configuration map = new PropertiesConfiguration();

            map.setProperty("name", "Alice");
            map.setProperty("age", 15);

            BeanAssist.convertFromConfiguration(user, map, false);

            Assert.assertTrue(user.getName().equals("Alice"));
            Assert.assertTrue(user.getAge() == 15);

            User user2 = new User();
            Configuration map2 = new PropertiesConfiguration();
            map2.setProperty("NAME", "Alice");
            map2.setProperty("AGE", 15);

            BeanAssist.convertFromConfiguration(user2, map2, true);

            Assert.assertTrue(user2.getName().equals("Alice"));
            Assert.assertTrue(user2.getAge() == 15);

            User user3 = new User();
            Configuration map3 = new PropertiesConfiguration();
            map3.setProperty("NAME", "Alice");
            map3.setProperty("AGE", 15);

            BeanAssist.convertFromConfiguration(user3, map3, false);

            Assert.assertNull(user3.getName());
            Assert.assertTrue(user3.getAge() == 0);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void convertFromProperties() {
        try {
            User user = new User();
            Properties map = new Properties();

            map.setProperty("name", "Alice");
            map.setProperty("age", "15");

            BeanAssist.convertFromProperties(user, map, false);

            Assert.assertTrue(user.getName().equals("Alice"));
            Assert.assertTrue(user.getAge() == 15);

            User user2 = new User();
            Properties map2 = new Properties();
            map2.setProperty("NAME", "Alice");
            map2.setProperty("AGE", "15");

            BeanAssist.convertFromProperties(user2, map2, true);

            Assert.assertTrue(user2.getName().equals("Alice"));
            Assert.assertTrue(user2.getAge() == 15);

            User user3 = new User();
            Properties map3 = new Properties();
            map3.setProperty("NAME", "Alice");
            map3.setProperty("AGE", "15");

            BeanAssist.convertFromProperties(user3, map3, false);

            Assert.assertNull(user3.getName());
            Assert.assertTrue(user3.getAge() == 0);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    private static class User {
        String name;

        int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}