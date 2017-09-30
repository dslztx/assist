package me.dslztx.assist.util;

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

    @Test
    public void testIsChineseChar() {
        try {
            assertTrue(CharUtils.isChineseChar('好'));
            assertFalse(CharUtils.isChineseChar('》'));
            assertFalse(CharUtils.isChineseChar('>'));
            assertFalse(CharUtils.isChineseChar('a'));
            assertFalse(CharUtils.isChineseChar('1'));
            assertFalse(CharUtils.isChineseChar('\r'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsEnglishChar() {
        try {
            assertFalse(CharUtils.isEnglishChar('》'));
            assertFalse(CharUtils.isEnglishChar('〸'));
            assertFalse(CharUtils.isEnglishChar('>'));
            assertFalse(CharUtils.isEnglishChar('='));
            assertTrue(CharUtils.isEnglishChar('a'));
            assertFalse(CharUtils.isEnglishChar('好'));
            assertFalse(CharUtils.isEnglishChar('\r'));
            assertFalse(CharUtils.isEnglishChar('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsChinesePunctuation() {
        try {
            assertTrue(CharUtils.isChinesePunctuation('》'));
            assertTrue(CharUtils.isChinesePunctuation('〸'));
            assertFalse(CharUtils.isChinesePunctuation('>'));
            assertFalse(CharUtils.isChinesePunctuation('='));
            assertFalse(CharUtils.isChinesePunctuation('a'));
            assertFalse(CharUtils.isChinesePunctuation('好'));
            assertFalse(CharUtils.isChinesePunctuation('\r'));
            assertFalse(CharUtils.isChinesePunctuation('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsEnglishPunctuation() {
        try {
            assertFalse(CharUtils.isEnglishPunctuation('》'));
            assertFalse(CharUtils.isEnglishPunctuation('〸'));
            assertTrue(CharUtils.isEnglishPunctuation('>'));
            assertTrue(CharUtils.isEnglishPunctuation('='));
            assertFalse(CharUtils.isEnglishPunctuation('a'));
            assertFalse(CharUtils.isEnglishPunctuation('好'));
            assertFalse(CharUtils.isEnglishPunctuation('\r'));
            assertFalse(CharUtils.isEnglishPunctuation('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
