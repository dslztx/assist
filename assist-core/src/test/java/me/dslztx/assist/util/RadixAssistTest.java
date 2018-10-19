package me.dslztx.assist.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.*;

/**
 * @author dslztx
 * @date 2015年08月11日
 */
public class RadixAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(RadixAssistTest.class);

    @Test
    public void testFromByte() {
        try {
            assertTrue(RadixAssist.fromByte((byte) -1, RadixAssist.Radix.Binary).equals("11111111"));
            assertTrue(RadixAssist.fromByte((byte) -1, RadixAssist.Radix.Octal).equals("377"));
            assertTrue(RadixAssist.fromByte((byte) -1, RadixAssist.Radix.Hex).equals("ff"));
            assertTrue(RadixAssist.fromByte((byte) 127, RadixAssist.Radix.Binary).equals("01111111"));
            assertTrue(RadixAssist.fromByte((byte) 127, RadixAssist.Radix.Octal).equals("177"));
            assertTrue(RadixAssist.fromByte((byte) 127, RadixAssist.Radix.Hex).equals("7f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromShort() {
        try {
            assertTrue(RadixAssist.fromShort((short) -1, RadixAssist.Radix.Binary).equals(
                    "1111111111111111"));
            assertTrue(RadixAssist.fromShort((short) -1, RadixAssist.Radix.Octal).equals("177777"));
            assertTrue(RadixAssist.fromShort((short) -1, RadixAssist.Radix.Hex).equals("ffff"));
            assertTrue(RadixAssist.fromShort((short) 127, RadixAssist.Radix.Binary).equals(
                    "0000000001111111"));
            assertTrue(RadixAssist.fromShort((short) 127, RadixAssist.Radix.Octal).equals("000177"));
            assertTrue(RadixAssist.fromShort((short) 127, RadixAssist.Radix.Hex).equals("007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromInt() {
        try {
            assertTrue(RadixAssist.fromInt(-1, RadixAssist.Radix.Binary).equals(
                    "11111111111111111111111111111111"));
            assertTrue(RadixAssist.fromInt(-1, RadixAssist.Radix.Octal).equals("37777777777"));
            assertTrue(RadixAssist.fromInt(-1, RadixAssist.Radix.Hex).equals("ffffffff"));
            assertTrue(RadixAssist.fromInt(127, RadixAssist.Radix.Binary).equals(
                    "00000000000000000000000001111111"));
            assertTrue(RadixAssist.fromInt(127, RadixAssist.Radix.Octal).equals("00000000177"));
            assertTrue(RadixAssist.fromInt(127, RadixAssist.Radix.Hex).equals("0000007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testFromLong() {
        try {
            assertTrue(RadixAssist.fromLong(-1L, RadixAssist.Radix.Binary).equals(
                    "1111111111111111111111111111111111111111111111111111111111111111"));
            assertTrue(RadixAssist.fromLong(-1L, RadixAssist.Radix.Octal).equals(
                    "1777777777777777777777"));
            assertTrue(RadixAssist.fromLong(-1L, RadixAssist.Radix.Hex).equals("ffffffffffffffff"));
            assertTrue(RadixAssist.fromLong(127L, RadixAssist.Radix.Binary).equals(
                    "0000000000000000000000000000000000000000000000000000000001111111"));
            assertTrue(RadixAssist.fromLong(127L, RadixAssist.Radix.Octal).equals(
                    "0000000000000000000177"));
            assertTrue(RadixAssist.fromLong(127L, RadixAssist.Radix.Hex).equals("000000000000007f"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToByte() {
        try {
            assertTrue(RadixAssist.toByte("00001111".toCharArray(), RadixAssist.Radix.Binary) == (byte) 15);
            assertTrue(RadixAssist.toByte("11111111".toCharArray(), RadixAssist.Radix.Binary) == (byte) -1);
            assertTrue(
                RadixAssist.toByte("000000000000177".toCharArray(), RadixAssist.Radix.Octal) == (byte) 127);
            assertTrue(RadixAssist.toByte("71".toCharArray(), RadixAssist.Radix.Octal) == (byte) 57);
            assertTrue(RadixAssist.toByte("000000000000ff".toCharArray(), RadixAssist.Radix.Hex) == (byte) -1);
            assertTrue(RadixAssist.toByte("00005f".toCharArray(), RadixAssist.Radix.Hex) == (byte) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToShort() {
        try {
            assertTrue(RadixAssist.toShort("00001111".toCharArray(), RadixAssist.Radix.Binary) == (short) 15);
            assertTrue(RadixAssist
                    .toShort("1111111111111111".toCharArray(), RadixAssist.Radix.Binary) == (short) -1);
            assertTrue(
                RadixAssist.toShort("000000000000177".toCharArray(), RadixAssist.Radix.Octal) == (short) 127);
            assertTrue(RadixAssist.toShort("55".toCharArray(), RadixAssist.Radix.Octal) == (short) 45);
            assertTrue(RadixAssist.toShort("000000000000ff".toCharArray(), RadixAssist.Radix.Hex) == (short) 255);
            assertTrue(RadixAssist.toShort("00005f".toCharArray(), RadixAssist.Radix.Hex) == (short) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToInt() {
        try {
            assertTrue(RadixAssist.toInt("00001111".toCharArray(), RadixAssist.Radix.Binary) == (int) 15);
            assertTrue(RadixAssist
                    .toShort("1111111111111111".toCharArray(), RadixAssist.Radix.Binary) == (int) -1);
            assertTrue(
                RadixAssist.toInt("000000000000177".toCharArray(), RadixAssist.Radix.Octal) == (int) 127);
            assertTrue(RadixAssist.toInt("55".toCharArray(), RadixAssist.Radix.Octal) == (int) 45);
            assertTrue(RadixAssist.toInt("000000000000ff".toCharArray(), RadixAssist.Radix.Hex) == (int) 255);
            assertTrue(RadixAssist.toInt("00005f".toCharArray(), RadixAssist.Radix.Hex) == (int) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testToLong() {
        try {
            assertTrue(RadixAssist.toLong("00001111".toCharArray(), RadixAssist.Radix.Binary) == (long) 15);
            assertTrue(RadixAssist
                    .toShort("1111111111111111".toCharArray(), RadixAssist.Radix.Binary) == (long) -1);
            assertTrue(
                RadixAssist.toLong("000000000000177".toCharArray(), RadixAssist.Radix.Octal) == (long) 127);
            assertTrue(RadixAssist.toLong("55".toCharArray(), RadixAssist.Radix.Octal) == (long) 45);
            assertTrue(RadixAssist.toLong("000000000000ff".toCharArray(), RadixAssist.Radix.Hex) == (long) 255);
            assertTrue(RadixAssist.toLong("00005f".toCharArray(), RadixAssist.Radix.Hex) == (long) 95);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
