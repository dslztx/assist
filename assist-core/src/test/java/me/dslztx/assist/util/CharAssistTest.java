package me.dslztx.assist.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(CharAssistTest.class);

    @Test
    public void testIsHexChar() throws Exception {
        try {
            assertTrue(CharAssist.isHexChar('f'));
            assertFalse(CharAssist.isHexChar('z'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsOctChar() throws Exception {
        try {
            assertTrue(CharAssist.isOctChar('7'));
            assertFalse(CharAssist.isOctChar('8'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDecimalChar() throws Exception {
        try {
            assertTrue(CharAssist.isDecimalChar('9'));
            assertFalse(CharAssist.isDecimalChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDigitChar() throws Exception {
        try {
            assertTrue(CharAssist.isDigitChar('9'));
            assertFalse(CharAssist.isDigitChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsChineseChar() {
        try {
            assertTrue(CharAssist.isChineseChar('好'));
            assertFalse(CharAssist.isChineseChar('》'));
            assertFalse(CharAssist.isChineseChar('>'));
            assertFalse(CharAssist.isChineseChar('a'));
            assertFalse(CharAssist.isChineseChar('1'));
            assertFalse(CharAssist.isChineseChar('\r'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsEnglishChar() {
        try {
            assertFalse(CharAssist.isEnglishChar('》'));
            assertFalse(CharAssist.isEnglishChar('〸'));
            assertFalse(CharAssist.isEnglishChar('>'));
            assertFalse(CharAssist.isEnglishChar('='));
            assertTrue(CharAssist.isEnglishChar('a'));
            assertFalse(CharAssist.isEnglishChar('好'));
            assertFalse(CharAssist.isEnglishChar('\r'));
            assertFalse(CharAssist.isEnglishChar('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsChinesePunctuation() {
        try {
            assertTrue(CharAssist.isChinesePunctuation('》'));
            assertTrue(CharAssist.isChinesePunctuation('〸'));
            assertFalse(CharAssist.isChinesePunctuation('>'));
            assertFalse(CharAssist.isChinesePunctuation('='));
            assertFalse(CharAssist.isChinesePunctuation('a'));
            assertFalse(CharAssist.isChinesePunctuation('好'));
            assertFalse(CharAssist.isChinesePunctuation('\r'));
            assertFalse(CharAssist.isChinesePunctuation('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsEnglishPunctuation() {
        try {
            assertFalse(CharAssist.isEnglishPunctuation('》'));
            assertFalse(CharAssist.isEnglishPunctuation('〸'));
            assertTrue(CharAssist.isEnglishPunctuation('>'));
            assertTrue(CharAssist.isEnglishPunctuation('='));
            assertFalse(CharAssist.isEnglishPunctuation('a'));
            assertFalse(CharAssist.isEnglishPunctuation('好'));
            assertFalse(CharAssist.isEnglishPunctuation('\r'));
            assertFalse(CharAssist.isEnglishPunctuation('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
