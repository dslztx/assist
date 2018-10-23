package me.dslztx.assist.util;

import org.apache.commons.configuration2.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigLoadAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoadAssist.class);

    @Test
    public void propConfigTest() {
        try {
            Configuration conf = ConfigLoadAssist.propConfig("1", "UTF-8");
            Assert.assertTrue(conf.size() == 3);
            Assert.assertTrue(conf.getString("A").equals("你"));
            Assert.assertTrue(conf.getString("B").equals("好"));
            Assert.assertTrue(conf.getString("C").equals("吗"));

            Configuration conf1 = ConfigLoadAssist.propConfig("2", "GBK");
            Assert.assertTrue(conf1.size() == 3);
            Assert.assertTrue(conf1.getString("A").equals("你"));
            Assert.assertTrue(conf1.getString("B").equals("好"));
            Assert.assertTrue(conf1.getString("C").equals("吗"));

            Configuration conf2 = ConfigLoadAssist.propConfig("1");
            Assert.assertTrue(conf2.size() == 3);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void xmlPropConfigTest() {
        try {
            Configuration conf = ConfigLoadAssist.xmlPropConfig("1.xml");

            Assert.assertTrue(conf.size() == 3);
            Assert.assertTrue(conf.getString("A").equals("你"));
            Assert.assertTrue(conf.getString("B").equals("好"));
            Assert.assertTrue(conf.getString("C").equals("吗"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void iniConfigTest() {
        try {
            Configuration conf = ConfigLoadAssist.iniPropConfig("3.ini", "GBK");

            Assert.assertTrue(conf.size() == 3);
            Assert.assertTrue(conf.getString("A").equals("你"));
            Assert.assertTrue(conf.getString("B").equals("好"));
            Assert.assertTrue(conf.getString("C").equals("吗"));

            Configuration conf1 = ConfigLoadAssist.iniPropConfig("3.ini");

            Assert.assertTrue(conf1.size() == 3);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void xmlDocumentConfigTest() {
        try {
            Document document = ConfigLoadAssist.xmlDocumentConfig("1.xml");

            NodeList nodeList = document.getElementsByTagName("properties");
            Assert.assertTrue(nodeList.getLength() == 1);

            Node node = nodeList.item(0);

            NodeList comment = ((Element)node).getElementsByTagName("comment");
            Assert.assertTrue(comment.getLength() == 1);
            Assert.assertTrue(comment.item(0).getChildNodes().item(0).getNodeValue().equals("test"));

            NodeList entry = ((Element)node).getElementsByTagName("entry");
            Assert.assertTrue(entry.getLength() == 3);
            Assert.assertTrue(entry.item(0).getChildNodes().item(0).getNodeValue().equals("你"));
            Assert.assertTrue(entry.item(1).getChildNodes().item(0).getNodeValue().equals("吗"));
            Assert.assertTrue(entry.item(2).getChildNodes().item(0).getNodeValue().equals("好"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}