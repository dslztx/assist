package me.dslztx.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class CharUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(CharUtilTest.class);

    @Test
    public void testIsHexChar() throws Exception {
        try {
            assertTrue(CharUtil.isHexChar('f'));
            assertFalse(CharUtil.isHexChar('z'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsOctChar() throws Exception {
        try {
            assertTrue(CharUtil.isOctChar('7'));
            assertFalse(CharUtil.isOctChar('8'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDecimalChar() throws Exception {
        try {
            assertTrue(CharUtil.isDecimalChar('9'));
            assertFalse(CharUtil.isDecimalChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDigitChar() throws Exception {
        try {
            assertTrue(CharUtil.isDigitChar('9'));
            assertFalse(CharUtil.isDigitChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
