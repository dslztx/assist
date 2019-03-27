package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(NumberAssistTest.class);

    @Test
    public void hexStrToDec() {
        try {
            Assert.assertTrue(NumberAssist.hexStrToDec("0x45f", 2, 4) == 1119);
            Assert.assertTrue(NumberAssist.hexStrToDec("14a", 0, 2) == 330);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void hexStrToDec2() {
        try {
            NumberAssist.hexStrToDec("0x45g", 2, 4);
        } catch (NumberFormatException e) {
            logger.info("expected");
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void decStrToDec() {
        try {
            Assert.assertTrue(NumberAssist.decStrToDec("0x00450", 2, 6) == 450);
            Assert.assertTrue(NumberAssist.decStrToDec("149", 0, 2) == 149);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void decStrToDec2() {
        try {
            NumberAssist.decStrToDec("0x45g", 2, 4);
        } catch (NumberFormatException e) {
            logger.info("expected");
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}