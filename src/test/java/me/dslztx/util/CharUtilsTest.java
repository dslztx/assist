package me.dslztx.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class CharUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(CharUtilsTest.class);

    @Test
    public void testIsHexChar() throws Exception {
        try {
            assertTrue(CharUtils.isHexChar('f'));
            assertFalse(CharUtils.isHexChar('z'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsOctChar() throws Exception {
        try {
            assertTrue(CharUtils.isOctChar('7'));
            assertFalse(CharUtils.isOctChar('8'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDecimalChar() throws Exception {
        try {
            assertTrue(CharUtils.isDecimalChar('9'));
            assertFalse(CharUtils.isDecimalChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDigitChar() throws Exception {
        try {
            assertTrue(CharUtils.isDigitChar('9'));
            assertFalse(CharUtils.isDigitChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
