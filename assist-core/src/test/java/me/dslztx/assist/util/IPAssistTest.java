package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(IPAssistTest.class);

    @Test
    public void obtainIPCIPv4() {
        try {
            String ip = "20.42.242.5";
            Assert.assertTrue("20.42.242".equals(IPAssist.obtainIPCIPv4(ip)));

            ip = "255.42.42";
            Assert.assertNull(IPAssist.obtainIPCIPv4(ip));

            ip = "";
            Assert.assertNull(IPAssist.obtainIPCIPv4(ip));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void obtainIPCIPv4Simply() {
        try {
            String ip = "255.442.432.3";
            Assert.assertTrue("255.442.432".equals(IPAssist.obtainIPCIPv4Simply(ip)));

            ip = "255,423,42.5";
            Assert.assertTrue("255,423,42".equals(IPAssist.obtainIPCIPv4Simply(ip)));

            ip = "255.42.1.10";
            Assert.assertTrue("255.42.1".equals(IPAssist.obtainIPCIPv4Simply(ip)));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void encodeDecodeIP() {
        try {
            String ip1 = "255.255.255.255";
            Assert.assertTrue(ip1.equals(IPAssist.decodeIP(IPAssist.encodeIP(ip1))));

            String ip2 = "0.0.0.0";
            Assert.assertTrue(ip2.equals(IPAssist.decodeIP(IPAssist.encodeIP(ip2))));

            String ip3 = "127.247.21.9";
            Assert.assertTrue(ip3.equals(IPAssist.decodeIP(IPAssist.encodeIP(ip3))));

            String ip4 = "012.04.013.231";
            Assert.assertTrue("12.4.13.231".equals(IPAssist.decodeIP(IPAssist.encodeIP(ip4))));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isLanIPv4Test() {
        try {
            Assert.assertTrue(IPAssist.isLanIPv4("10.110.20.22"));
            Assert.assertTrue(IPAssist.isLanIPv4("172.16.20.52"));
            Assert.assertTrue(IPAssist.isLanIPv4("172.31.20.52"));
            Assert.assertTrue(IPAssist.isLanIPv4("192.168.20.22"));

            Assert.assertFalse(IPAssist.isLanIPv4("10.110.20.522"));
            Assert.assertFalse(IPAssist.isLanIPv4("172.32.20.52"));
            Assert.assertFalse(IPAssist.isLanIPv4("172.15.20.52"));
            Assert.assertFalse(IPAssist.isLanIPv4("173.15.20.52"));
            Assert.assertFalse(IPAssist.isLanIPv4("192.169.20.52"));
            Assert.assertFalse(IPAssist.isLanIPv4("193.168.20.52"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}