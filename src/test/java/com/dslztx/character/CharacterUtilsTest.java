package com.dslztx.character;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class CharacterUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(CharacterUtilsTest.class);

    @Test
    public void testIsHexChar() throws Exception {
        try {
            assertTrue(CharacterUtils.isHexChar('f'));
            assertFalse(CharacterUtils.isHexChar('z'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsOctChar() throws Exception {
        try {
            assertTrue(CharacterUtils.isOctChar('7'));
            assertFalse(CharacterUtils.isOctChar('8'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDecimalChar() throws Exception {
        try {
            assertTrue(CharacterUtils.isDecimalChar('9'));
            assertFalse(CharacterUtils.isDecimalChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDigitChar() throws Exception {
        try {
            assertTrue(CharacterUtils.isDigitChar('9'));
            assertFalse(CharacterUtils.isDigitChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
