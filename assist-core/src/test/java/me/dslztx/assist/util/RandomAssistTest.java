package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(RandomAssistTest.class);

    @Test
    public void randomInt() {
        try {
            Assert.assertEquals(0, RandomAssist.randomInt(0, 1));
            Assert.assertEquals(0, RandomAssist.randomInt(0, 1));
            Assert.assertEquals(0, RandomAssist.randomInt(0, 1));

            Assert.assertEquals(RandomAssist.randomInt(-5, -4), -5);
            Assert.assertEquals(RandomAssist.randomInt(-5, -4), -5);
            Assert.assertEquals(RandomAssist.randomInt(-5, -4), -5);

            Assert.assertTrue(RandomAssist.randomInt(-5, 100) >= -5);
            Assert.assertTrue(RandomAssist.randomInt(-5, 100) < 100);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}