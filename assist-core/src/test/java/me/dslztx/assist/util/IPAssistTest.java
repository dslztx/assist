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
}