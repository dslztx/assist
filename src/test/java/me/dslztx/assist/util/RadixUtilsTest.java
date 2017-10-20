package me.dslztx.assist.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.*;

/**
 * @author dslztx
 * @date 2015年08月11日
 */
public class RadixUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(RadixUtilsTest.class);

    @Test
    public void testFromByte() {
        try {
            assertTrue(RadixUtils.fromByte((byte) -1, RadixUtils.Radix.Binary).equals("11111111"));
            assertTrue(RadixUtils.fromByte((byte) -1, RadixUtils.Radix.Octal).equals("377"));
            assertTrue(RadixUtils.fromByte((byte) -1, RadixUtils.Radix.Hex).equals("ff"));
            assertTrue(RadixUtils.fromByte((byte) 127, RadixUtils.Radix.Binary).equals("01111111"));
            assertTrue(RadixUtils.fromByte((byte) 127, RadixUtils.Radix.Octal).equals("177"));
            assertTrue(RadixUtils.fromByte((byte) 127, RadixUtils.Radix.Hex).equals("7f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromShort() {
        try {
            assertTrue(RadixUtils.fromShort((short) -1, RadixUtils.Radix.Binary).equals(
                    "1111111111111111"));
            assertTrue(RadixUtils.fromShort((short) -1, RadixUtils.Radix.Octal).equals("177777"));
            assertTrue(RadixUtils.fromShort((short) -1, RadixUtils.Radix.Hex).equals("ffff"));
            assertTrue(RadixUtils.fromShort((short) 127, RadixUtils.Radix.Binary).equals(
                    "0000000001111111"));
            assertTrue(RadixUtils.fromShort((short) 127, RadixUtils.Radix.Octal).equals("000177"));
            assertTrue(RadixUtils.fromShort((short) 127, RadixUtils.Radix.Hex).equals("007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromInt() {
        try {
            assertTrue(RadixUtils.fromInt(-1, RadixUtils.Radix.Binary).equals(
                    "11111111111111111111111111111111"));
            assertTrue(RadixUtils.fromInt(-1, RadixUtils.Radix.Octal).equals("37777777777"));
            assertTrue(RadixUtils.fromInt(-1, RadixUtils.Radix.Hex).equals("ffffffff"));
            assertTrue(RadixUtils.fromInt(127, RadixUtils.Radix.Binary).equals(
                    "00000000000000000000000001111111"));
            assertTrue(RadixUtils.fromInt(127, RadixUtils.Radix.Octal).equals("00000000177"));
            assertTrue(RadixUtils.fromInt(127, RadixUtils.Radix.Hex).equals("0000007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromLong() {
        try {
            assertTrue(RadixUtils.fromLong(-1L, RadixUtils.Radix.Binary).equals(
                    "1111111111111111111111111111111111111111111111111111111111111111"));
            assertTrue(RadixUtils.fromLong(-1L, RadixUtils.Radix.Octal).equals(
                    "1777777777777777777777"));
            assertTrue(RadixUtils.fromLong(-1L, RadixUtils.Radix.Hex).equals("ffffffffffffffff"));
            assertTrue(RadixUtils.fromLong(127L, RadixUtils.Radix.Binary).equals(
                    "0000000000000000000000000000000000000000000000000000000001111111"));
            assertTrue(RadixUtils.fromLong(127L, RadixUtils.Radix.Octal).equals(
                    "0000000000000000000177"));
            assertTrue(RadixUtils.fromLong(127L, RadixUtils.Radix.Hex).equals("000000000000007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToByte() {
        try {
            assertTrue(RadixUtils.toByte("00001111".toCharArray(), RadixUtils.Radix.Binary) == (byte) 15);
            assertTrue(RadixUtils.toByte("11111111".toCharArray(), RadixUtils.Radix.Binary) == (byte) -1);
            assertTrue(RadixUtils.toByte("000000000000177".toCharArray(), RadixUtils.Radix.Octal) == (byte) 127);
            assertTrue(RadixUtils.toByte("71".toCharArray(), RadixUtils.Radix.Octal) == (byte) 57);
            assertTrue(RadixUtils.toByte("000000000000ff".toCharArray(), RadixUtils.Radix.Hex) == (byte) -1);
            assertTrue(RadixUtils.toByte("00005f".toCharArray(), RadixUtils.Radix.Hex) == (byte) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToShort() {
        try {
            assertTrue(RadixUtils.toShort("00001111".toCharArray(), RadixUtils.Radix.Binary) == (short) 15);
            assertTrue(RadixUtils
                    .toShort("1111111111111111".toCharArray(), RadixUtils.Radix.Binary) == (short) -1);
            assertTrue(
                RadixUtils.toShort("000000000000177".toCharArray(), RadixUtils.Radix.Octal) == (short) 127);
            assertTrue(RadixUtils.toShort("55".toCharArray(), RadixUtils.Radix.Octal) == (short) 45);
            assertTrue(RadixUtils.toShort("000000000000ff".toCharArray(), RadixUtils.Radix.Hex) == (short) 255);
            assertTrue(RadixUtils.toShort("00005f".toCharArray(), RadixUtils.Radix.Hex) == (short) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToInt() {
        try {
            assertTrue(RadixUtils.toInt("00001111".toCharArray(), RadixUtils.Radix.Binary) == (int) 15);
            assertTrue(RadixUtils
                    .toShort("1111111111111111".toCharArray(), RadixUtils.Radix.Binary) == (int) -1);
            assertTrue(RadixUtils.toInt("000000000000177".toCharArray(), RadixUtils.Radix.Octal) == (int) 127);
            assertTrue(RadixUtils.toInt("55".toCharArray(), RadixUtils.Radix.Octal) == (int) 45);
            assertTrue(RadixUtils.toInt("000000000000ff".toCharArray(), RadixUtils.Radix.Hex) == (int) 255);
            assertTrue(RadixUtils.toInt("00005f".toCharArray(), RadixUtils.Radix.Hex) == (int) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToLong() {
        try {
            assertTrue(RadixUtils.toLong("00001111".toCharArray(), RadixUtils.Radix.Binary) == (long) 15);
            assertTrue(RadixUtils
                    .toShort("1111111111111111".toCharArray(), RadixUtils.Radix.Binary) == (long) -1);
            assertTrue(RadixUtils.toLong("000000000000177".toCharArray(), RadixUtils.Radix.Octal) == (long) 127);
            assertTrue(RadixUtils.toLong("55".toCharArray(), RadixUtils.Radix.Octal) == (long) 45);
            assertTrue(RadixUtils.toLong("000000000000ff".toCharArray(), RadixUtils.Radix.Hex) == (long) 255);
            assertTrue(RadixUtils.toLong("00005f".toCharArray(), RadixUtils.Radix.Hex) == (long) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
