package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimitiveAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(PrimitiveAssistTest.class);

    @Test
    public void less() {
        try {
            Assert.assertTrue(PrimitiveAssist.less(0.1, 0.2));
            Assert.assertTrue(PrimitiveAssist.less(0.00000001, 0.00000002));
            Assert.assertFalse(PrimitiveAssist.less(0.00000003, 0.00000002));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void lessEqual() {
        try {
            Assert.assertTrue(PrimitiveAssist.lessEqual(0.1, 0.2));
            Assert.assertTrue(PrimitiveAssist.lessEqual(0.00000002, 0.0000002));
            Assert.assertFalse(PrimitiveAssist.lessEqual(0.0000003, 0.0000002));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void less1() {
        try {
            Assert.assertTrue(PrimitiveAssist.less(0.1F, 0.2F));
            Assert.assertTrue(PrimitiveAssist.less(0.00000001F, 0.00000002F));
            Assert.assertFalse(PrimitiveAssist.less(0.00000003F, 0.00000002F));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void lessEqual1() {
        try {
            Assert.assertTrue(PrimitiveAssist.lessEqual(0.1F, 0.2F));
            Assert.assertTrue(PrimitiveAssist.lessEqual(0.00000002F, 0.00000002F));
            Assert.assertFalse(PrimitiveAssist.lessEqual(0.00000003F, 0.00000002F));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

}