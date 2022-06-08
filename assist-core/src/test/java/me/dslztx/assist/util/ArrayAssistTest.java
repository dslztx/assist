package me.dslztx.assist.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArrayAssistTest {

    @Test
    public void isEmpty() throws Exception {
        try {
            assertTrue(ArrayAssist.isEmpty(new String[0]));
            assertFalse(ArrayAssist.isEmpty(new String[1]));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void obtainSizeDefaultZeroTest() {
        try {
            Assert.assertTrue(ArrayAssist.obtainSizeDefaultZero(new Object[0]) == 0);
            Assert.assertTrue(ArrayAssist.obtainSizeDefaultZero(new int[0]) == 0);
            Assert.assertTrue(ArrayAssist.obtainSizeDefaultZero(new byte[0]) == 0);

            Assert.assertTrue(ArrayAssist.obtainSizeDefaultZero(new Object[3]) == 3);
            Assert.assertTrue(ArrayAssist.obtainSizeDefaultZero(new int[3]) == 3);
            Assert.assertTrue(ArrayAssist.obtainSizeDefaultZero(new byte[3]) == 3);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}