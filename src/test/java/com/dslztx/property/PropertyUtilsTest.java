package com.dslztx.property;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author dslztx
 * @date 2015年11月12日
 */
public class PropertyUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtilsTest.class);

    @Test
    public void testLoadFromFile() throws Exception {
        File file1 = new ClassPathResource("1").getFile();
        File file2 = new ClassPathResource("2").getFile();
        try {
            Properties properties1 = PropertyUtils.loadFromFile(file1, Charset.forName("UTF-8"));
            Properties properties2 = PropertyUtils.loadFromFile(file2, Charset.forName("GBK"));

            assertTrue(properties1.stringPropertyNames().size() == 3);
            assertTrue(properties1.get("A").equals("你"));

            assertTrue(properties2.stringPropertyNames().size() == 3);
            assertTrue(properties2.get("A").equals("你"));

            Properties properties11 = PropertyUtils.loadFromFile(file1, Charset.forName("GBK"));
            Properties properties22 = PropertyUtils.loadFromFile(file2, Charset.forName("UTF-8"));
            assertFalse(properties11.get("A").equals("你"));
            logger.info("Wrong Result:" + properties11.get("A"));
            assertFalse(properties22.get("A").equals("你"));
            logger.info("Wrong Result:" + properties22.get("A"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testStoreToFile() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("A", "你");
        properties.setProperty("B", "好");
        properties.setProperty("C", "吗");

        File file3 = new File("3");
        File file4 = new File("4");

        PropertyUtils.storeToFile(properties, file3, Charset.forName("UTF-8"));
        PropertyUtils.storeToFile(properties, file4, Charset.forName("GBK"));

        try {
            Properties properties3 = PropertyUtils.loadFromFile(file3, Charset.forName("UTF-8"));
            Properties properties4 = PropertyUtils.loadFromFile(file4, Charset.forName("GBK"));

            assertTrue(properties3.get("A").equals("你"));
            assertTrue(properties4.get("A").equals("你"));

            Properties properties33 = PropertyUtils.loadFromFile(file3, Charset.forName("GBK"));
            Properties properties44 = PropertyUtils.loadFromFile(file4, Charset.forName("UTF-8"));
            assertFalse(properties33.get("A").equals("你"));
            logger.info("Wrong Result:" + properties33.get("A"));
            assertFalse(properties44.get("A").equals("你"));
            logger.info("Wrong Result:" + properties44.get("A"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        } finally {
            if (file3 != null && file3.exists()) {
                try {
                    file3.delete();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
            if (file4 != null && file4.exists()) {
                try {
                    file4.delete();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }

    @Test
    public void testLoadFromXMLFile() throws Exception {
        File xmlFile1 = new ClassPathResource("1.xml").getFile();
        File xmlFile2 = new ClassPathResource("2.xml").getFile();

        Properties properties1 = PropertyUtils.loadFromXMLFile(xmlFile1);
        Properties properties2 = PropertyUtils.loadFromXMLFile(xmlFile2);

        try {
            assertTrue(properties1.get("A").equals("你"));
            assertTrue(properties2.get("A").equals("你"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testStoreToXMLFile() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("A", "你");
        properties.setProperty("B", "好");
        properties.setProperty("C", "吗");

        File file3 = new File("3.xml");
        File file4 = new File("4.xml");

        PropertyUtils.storeToXMLFile(properties, file3, Charset.forName("UTF-8"));
        PropertyUtils.storeToXMLFile(properties, file4, Charset.forName("GBK"));

        try {
            Properties properties3 = PropertyUtils.loadFromXMLFile(file3);
            Properties properties4 = PropertyUtils.loadFromXMLFile(file4);
            assertTrue(properties3.get("A").equals("你"));
            assertTrue(properties4.get("A").equals("你"));


            BufferedReader reader = null;
            String line = null;

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file3), "ASCII"));
            line = reader.readLine();
            assertTrue(line.contains("UTF-8"));

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file4), "ASCII"));
            line = reader.readLine();
            assertTrue(line.contains("GBK"));
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            fail();
        } finally {
            if (file3 != null && file3.exists()) {
                try {
                    file3.delete();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
            if (file4 != null && file4.exists()) {
                try {
                    file4.delete();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }
}
