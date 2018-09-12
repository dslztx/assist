package me.dslztx.assist.util;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

/**
 * @author dslztx
 * @date 2015年08月18日
 */
public class StringAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(StringAssistTest.class);

    @Test
    public void testIsHexStr() throws Exception {
        try {
            assertTrue(StringAssist.isHexStr("aaFf"));
            assertFalse(StringAssist.isHexStr("zu"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsOctStr() throws Exception {
        try {
            assertTrue(StringAssist.isOctStr("07770"));
            assertFalse(StringAssist.isOctStr("0768"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDecimalStr() throws Exception {
        try {
            assertTrue(StringAssist.isDecimalStr("3128390217"));
            assertFalse(StringAssist.isDecimalStr("983219z"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsBlank() {
        try {
            assertTrue(StringAssist.isBlank(""));
            assertTrue(StringAssist.isBlank(null));
            assertTrue(StringAssist.isBlank("   "));
            assertFalse(StringAssist.isBlank("  fdsfd "));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToLowerCase() {
        try {
            assertNull(StringAssist.toLowerCase(null));
            assertTrue(StringAssist.toLowerCase("hEllo").equals("hello"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToUpperCase() {
        try {
            assertNull(StringAssist.toUpperCase(null));
            assertTrue(StringAssist.toUpperCase("hEllo").equals("HELLO"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testSplit() {
        try {
            String s = "hello   world";

            Assert.assertTrue(StringAssist.split(s, ' ', false).length == 4);
            Assert.assertTrue(StringAssist.split(s, ' ', true).length == 2);

            String ss = ";;;;,,,;;;";
            Assert.assertTrue(StringAssist.split(ss, ';', false).length == 8);
            Assert.assertTrue(StringAssist.split(ss, ';', true).length == 1);
            Assert.assertTrue(StringAssist.split(ss, ',', false).length == 4);
            Assert.assertTrue(StringAssist.split(ss, ',', true).length == 2);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testRemoveChar() {
        try {
            String s = "abcd\r\n f gh\r\nfdss";
            Assert.assertTrue(StringAssist.removeChar(s, ' ', '\r', '\n').equals("abcdfghfdss"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
