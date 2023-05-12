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

    @Test
    public void obtainNetAddressIPv4ByteArrayTest() {
        try {
            Assert.assertTrue(ObjectAssist.equalsGenerally(IPAssist.obtainNetAddressByteArrayIPv4("127.0.0.1", 8),
                new byte[] {127, 0, 0, 0}));

            Assert.assertTrue(ObjectAssist.equalsGenerally(IPAssist.obtainNetAddressByteArrayIPv4("127.0.0.255", 25),
                new byte[] {127, 0, 0, -128}));

            Assert.assertTrue(ObjectAssist.equalsGenerally(IPAssist.obtainNetAddressByteArrayIPv4("7.33.204.128", 26),
                new byte[] {7, 33, -52, -128}));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void obtainNetAddressIPv4Test() {
        try {
            Assert.assertTrue(IPAssist.obtainNetAddressIPv4("127.0.0.1", 8).equals("127.0.0.0"));

            Assert.assertTrue(IPAssist.obtainNetAddressIPv4("127.0.0.255", 25).equals("127.0.0.128"));

            Assert.assertTrue(IPAssist.obtainNetAddressIPv4("7.33.204.128", 26).equals("7.33.204.128"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isInRangeTest() {
        try {
            Assert.assertTrue(IPAssist.isInRange("192.168.1.127", "192.168.1.64/26"));
            Assert.assertTrue(IPAssist.isInRange("192.168.1.2", "192.168.0.0/23"));
            Assert.assertTrue(IPAssist.isInRange("192.168.0.1", "192.168.0.0/24"));
            Assert.assertTrue(IPAssist.isInRange("192.168.0.0", "192.168.0.0/32"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isIPv6NormalCompressTest() {
        try {
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("ABCD:EF01:2345:6789:ABCD:EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("::"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("::ABCD:EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("EF01::ABCD:EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("EF01:ABCD::EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("EF01:ABCD:EF01::2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("EF01:ABCD:EF01:2345::6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("EF01:ABCD:EF01:2345:6789::6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompress("EF01:ABCD:EF01:2345:6789:6789::"));

            Assert.assertFalse(IPAssist.isIPv6NormalCompress("EF01:ABCD:EF01:2345:G789:6789::"));
            Assert.assertFalse(IPAssist.isIPv6NormalCompress("EF01:ABCD:EF01:2345:6789:6789::6789"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isIPv6NormalCompressMixTest() {
        try {
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("ABCD:EF01:2345:6789:ABCD:EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("::"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("::ABCD:EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01::ABCD:EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD::EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD:EF01::2345:6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD:EF01:2345::6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD:EF01:2345:6789::6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD:EF01:2345:6789:6789::"));

            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("ABCD:EF01:2345:6789:ABCD:EF01:192.168.0.1"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("::"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("::ABCD:EF01:2345:6789:192.168.0.1"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01::ABCD:EF01:6789:192.168.0.1"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01::EF01:6789:192.168.0.1"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD::2345:6789:192.168.0.1"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD:2345::6789:192.168.0.1"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD:EF01:2345:6789::6789"));
            Assert.assertTrue(IPAssist.isIPv6NormalCompressMix("EF01:ABCD:EF01:2345:6789:6789::"));

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isLanIPv6Test() {
        try {
            Assert.assertTrue(IPAssist.isLanIPv6("FC00:EF01:2345:6789:ABCD:EF01:2345:6789"));
            Assert.assertTrue(IPAssist.isLanIPv6("FD00:EF01:2345:6789:ABCD:EF01:2345:6789"));
            Assert.assertFalse(IPAssist.isLanIPv6(""));
            Assert.assertFalse(IPAssist.isLanIPv6("FE00::GF01:2345:6789:ABCD:EF01:2345:6789"));
            Assert.assertFalse(IPAssist.isLanIPv6("::EF01:2345:6789:ABCD:EF01:2345:6789"));
            Assert.assertFalse(IPAssist.isLanIPv6("FE00:EF01:2345:6789:ABCD:EF01:2345:6789"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}