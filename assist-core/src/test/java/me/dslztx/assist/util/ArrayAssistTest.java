package me.dslztx.assist.util;

import static org.junit.Assert.*;

import java.util.List;

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

    // Task 2.1: 为isEmpty(Object[])添加null测试
    @Test
    public void isEmptyObjectArrayNull() {
        assertTrue(ArrayAssist.isEmpty((Object[]) null));
    }

    // Task 2.2: 为isNotEmpty(Object[])添加正常和null测试
    @Test
    public void isNotEmptyObjectArray() {
        assertTrue(ArrayAssist.isNotEmpty(new String[]{"a", "b"}));
        assertFalse(ArrayAssist.isNotEmpty(new String[0]));
        assertFalse(ArrayAssist.isNotEmpty((Object[]) null));
    }

    // Task 2.3: 为isEmpty(byte[])添加null测试
    @Test
    public void isEmptyByteArrayNull() {
        assertTrue(ArrayAssist.isEmpty((byte[]) null));
    }

    // Task 2.4: 为isNotEmpty(byte[])添加正常和null测试
    @Test
    public void isNotEmptyByteArray() {
        assertTrue(ArrayAssist.isNotEmpty(new byte[]{1, 2}));
        assertFalse(ArrayAssist.isNotEmpty(new byte[0]));
        assertFalse(ArrayAssist.isNotEmpty((byte[]) null));
    }

    // Task 2.5: 为isEmpty(int[])添加null测试
    @Test
    public void isEmptyIntArrayNull() {
        assertTrue(ArrayAssist.isEmpty((int[]) null));
    }

    // Task 2.6: 为isNotEmpty(int[])添加正常和null测试
    @Test
    public void isNotEmptyIntArray() {
        assertTrue(ArrayAssist.isNotEmpty(new int[]{1, 2}));
        assertFalse(ArrayAssist.isNotEmpty(new int[0]));
        assertFalse(ArrayAssist.isNotEmpty((int[]) null));
    }

    // Task 3.1: 为obtainSizeDefaultZero(Object[])添加null测试
    @Test
    public void obtainSizeDefaultZeroObjectArrayNull() {
        assertEquals(0, ArrayAssist.obtainSizeDefaultZero((Object[]) null));
    }

    // Task 3.2: 为obtainSizeDefaultZero(int[])添加null测试
    @Test
    public void obtainSizeDefaultZeroIntArrayNull() {
        assertEquals(0, ArrayAssist.obtainSizeDefaultZero((int[]) null));
    }

    // Task 3.3: 为obtainSizeDefaultZero(byte[])添加null测试
    @Test
    public void obtainSizeDefaultZeroByteArrayNull() {
        assertEquals(0, ArrayAssist.obtainSizeDefaultZero((byte[]) null));
    }

    // Task 4.1: 为toList(int[])添加正常、空数组、null测试
    @Test
    public void toListIntArray() {
        // 正常数组
        List<Integer> result1 = ArrayAssist.toList(new int[]{1, 2, 3});
        assertEquals(3, result1.size());
        assertEquals(Integer.valueOf(1), result1.get(0));
        assertEquals(Integer.valueOf(2), result1.get(1));
        assertEquals(Integer.valueOf(3), result1.get(2));

        // 空数组
        List<Integer> result2 = ArrayAssist.toList(new int[0]);
        assertEquals(0, result2.size());

        // null
        List<Integer> result3 = ArrayAssist.toList((int[]) null);
        assertEquals(0, result3.size());
    }

    // Task 4.2: 为toList(byte[])添加正常、空数组、null测试
    @Test
    public void toListByteArray() {
        // 正常数组
        List<Byte> result1 = ArrayAssist.toList(new byte[]{1, 2, 3});
        assertEquals(3, result1.size());
        assertEquals(Byte.valueOf((byte) 1), result1.get(0));
        assertEquals(Byte.valueOf((byte) 2), result1.get(1));
        assertEquals(Byte.valueOf((byte) 3), result1.get(2));

        // 空数组
        List<Byte> result2 = ArrayAssist.toList(new byte[0]);
        assertEquals(0, result2.size());

        // null
        List<Byte> result3 = ArrayAssist.toList((byte[]) null);
        assertEquals(0, result3.size());
    }

    // Task 4.3: 为toList(Integer[])添加正常、空数组、null测试
    @Test
    public void toListIntegerArray() {
        // 正常数组
        List<Integer> result1 = ArrayAssist.toList(new Integer[]{1, 2, 3});
        assertEquals(3, result1.size());
        assertEquals(Integer.valueOf(1), result1.get(0));
        assertEquals(Integer.valueOf(2), result1.get(1));
        assertEquals(Integer.valueOf(3), result1.get(2));

        // 空数组
        List<Integer> result2 = ArrayAssist.toList(new Integer[0]);
        assertEquals(0, result2.size());

        // null
        List<Integer> result3 = ArrayAssist.toList((Integer[]) null);
        assertEquals(0, result3.size());
    }
}