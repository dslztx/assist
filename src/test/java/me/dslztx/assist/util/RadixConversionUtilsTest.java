package me.dslztx.assist.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.*;

/**
 * @author dslztx
 * @date 2015年08月11日
 */
public class RadixConversionUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(RadixConversionUtilsTest.class);

    @Test
    public void testFromByte() {
        try {
            assertTrue(RadixConversionUtils.fromByte((byte) -1, RadixConversionUtils.Radix.Binary).equals("11111111"));
            assertTrue(RadixConversionUtils.fromByte((byte) -1, RadixConversionUtils.Radix.Octal).equals("377"));
            assertTrue(RadixConversionUtils.fromByte((byte) -1, RadixConversionUtils.Radix.Hex).equals("ff"));
            assertTrue(RadixConversionUtils.fromByte((byte) 127, RadixConversionUtils.Radix.Binary).equals("01111111"));
            assertTrue(RadixConversionUtils.fromByte((byte) 127, RadixConversionUtils.Radix.Octal).equals("177"));
            assertTrue(RadixConversionUtils.fromByte((byte) 127, RadixConversionUtils.Radix.Hex).equals("7f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromShort() {
        try {
            assertTrue(RadixConversionUtils.fromShort((short) -1, RadixConversionUtils.Radix.Binary).equals(
                    "1111111111111111"));
            assertTrue(RadixConversionUtils.fromShort((short) -1, RadixConversionUtils.Radix.Octal).equals("177777"));
            assertTrue(RadixConversionUtils.fromShort((short) -1, RadixConversionUtils.Radix.Hex).equals("ffff"));
            assertTrue(RadixConversionUtils.fromShort((short) 127, RadixConversionUtils.Radix.Binary).equals(
                    "0000000001111111"));
            assertTrue(RadixConversionUtils.fromShort((short) 127, RadixConversionUtils.Radix.Octal).equals("000177"));
            assertTrue(RadixConversionUtils.fromShort((short) 127, RadixConversionUtils.Radix.Hex).equals("007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromInt() {
        try {
            assertTrue(RadixConversionUtils.fromInt(-1, RadixConversionUtils.Radix.Binary).equals(
                    "11111111111111111111111111111111"));
            assertTrue(RadixConversionUtils.fromInt(-1, RadixConversionUtils.Radix.Octal).equals("37777777777"));
            assertTrue(RadixConversionUtils.fromInt(-1, RadixConversionUtils.Radix.Hex).equals("ffffffff"));
            assertTrue(RadixConversionUtils.fromInt(127, RadixConversionUtils.Radix.Binary).equals(
                    "00000000000000000000000001111111"));
            assertTrue(RadixConversionUtils.fromInt(127, RadixConversionUtils.Radix.Octal).equals("00000000177"));
            assertTrue(RadixConversionUtils.fromInt(127, RadixConversionUtils.Radix.Hex).equals("0000007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromLong() {
        try {
            assertTrue(RadixConversionUtils.fromLong(-1L, RadixConversionUtils.Radix.Binary).equals(
                    "1111111111111111111111111111111111111111111111111111111111111111"));
            assertTrue(RadixConversionUtils.fromLong(-1L, RadixConversionUtils.Radix.Octal).equals(
                    "1777777777777777777777"));
            assertTrue(RadixConversionUtils.fromLong(-1L, RadixConversionUtils.Radix.Hex).equals("ffffffffffffffff"));
            assertTrue(RadixConversionUtils.fromLong(127L, RadixConversionUtils.Radix.Binary).equals(
                    "0000000000000000000000000000000000000000000000000000000001111111"));
            assertTrue(RadixConversionUtils.fromLong(127L, RadixConversionUtils.Radix.Octal).equals(
                    "0000000000000000000177"));
            assertTrue(RadixConversionUtils.fromLong(127L, RadixConversionUtils.Radix.Hex).equals("000000000000007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToByte() {
        try {
            assertTrue(RadixConversionUtils.toByte("00001111".toCharArray(), RadixConversionUtils.Radix.Binary) == (byte) 15);
            assertTrue(RadixConversionUtils.toByte("11111111".toCharArray(), RadixConversionUtils.Radix.Binary) == (byte) -1);
            assertTrue(RadixConversionUtils.toByte("000000000000177".toCharArray(), RadixConversionUtils.Radix.Octal) == (byte) 127);
            assertTrue(RadixConversionUtils.toByte("71".toCharArray(), RadixConversionUtils.Radix.Octal) == (byte) 57);
            assertTrue(RadixConversionUtils.toByte("000000000000ff".toCharArray(), RadixConversionUtils.Radix.Hex) == (byte) -1);
            assertTrue(RadixConversionUtils.toByte("00005f".toCharArray(), RadixConversionUtils.Radix.Hex) == (byte) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToShort() {
        try {
            assertTrue(RadixConversionUtils.toShort("00001111".toCharArray(), RadixConversionUtils.Radix.Binary) == (short) 15);
            assertTrue(RadixConversionUtils
                    .toShort("1111111111111111".toCharArray(), RadixConversionUtils.Radix.Binary) == (short) -1);
            assertTrue(RadixConversionUtils.toShort("000000000000177".toCharArray(), RadixConversionUtils.Radix.Octal) == (short) 127);
            assertTrue(RadixConversionUtils.toShort("55".toCharArray(), RadixConversionUtils.Radix.Octal) == (short) 45);
            assertTrue(RadixConversionUtils.toShort("000000000000ff".toCharArray(), RadixConversionUtils.Radix.Hex) == (short) 255);
            assertTrue(RadixConversionUtils.toShort("00005f".toCharArray(), RadixConversionUtils.Radix.Hex) == (short) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToInt() {
        try {
            assertTrue(RadixConversionUtils.toInt("00001111".toCharArray(), RadixConversionUtils.Radix.Binary) == (int) 15);
            assertTrue(RadixConversionUtils
                    .toShort("1111111111111111".toCharArray(), RadixConversionUtils.Radix.Binary) == (int) -1);
            assertTrue(RadixConversionUtils.toInt("000000000000177".toCharArray(), RadixConversionUtils.Radix.Octal) == (int) 127);
            assertTrue(RadixConversionUtils.toInt("55".toCharArray(), RadixConversionUtils.Radix.Octal) == (int) 45);
            assertTrue(RadixConversionUtils.toInt("000000000000ff".toCharArray(), RadixConversionUtils.Radix.Hex) == (int) 255);
            assertTrue(RadixConversionUtils.toInt("00005f".toCharArray(), RadixConversionUtils.Radix.Hex) == (int) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToLong() {
        try {
            assertTrue(RadixConversionUtils.toLong("00001111".toCharArray(), RadixConversionUtils.Radix.Binary) == (long) 15);
            assertTrue(RadixConversionUtils
                    .toShort("1111111111111111".toCharArray(), RadixConversionUtils.Radix.Binary) == (long) -1);
            assertTrue(RadixConversionUtils.toLong("000000000000177".toCharArray(), RadixConversionUtils.Radix.Octal) == (long) 127);
            assertTrue(RadixConversionUtils.toLong("55".toCharArray(), RadixConversionUtils.Radix.Octal) == (long) 45);
            assertTrue(RadixConversionUtils.toLong("000000000000ff".toCharArray(), RadixConversionUtils.Radix.Hex) == (long) 255);
            assertTrue(RadixConversionUtils.toLong("00005f".toCharArray(), RadixConversionUtils.Radix.Hex) == (long) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
