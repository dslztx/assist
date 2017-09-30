package me.dslztx.assist.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dslztx
 * @date 2015年08月11日
 */
public class RadixConversionUtils {

    /**
     * 以二进制形式表示byte类型值
     * 
     * @param b
     * @return
     */
    private static char[] byteToBinary(byte b) {
        char[] values = new char[8];
        for (int pos = 7; pos >= 0; pos--, b >>= 1) {
            if ((b & (byte) 0x01) == (byte) 1)
                values[pos] = '1';
            else
                values[pos] = '0';
        }
        return values;
    }

    /**
     * 以二进制形式表示short类型值
     * 
     * @param s
     * @return
     */
    private static char[] shortToBinary(short s) {
        char[] values = new char[16];
        for (int pos = 15; pos >= 0; pos--, s >>= 1) {
            if ((s & (short) 0x01) == (short) 1)
                values[pos] = '1';
            else
                values[pos] = '0';
        }
        return values;
    }

    /**
     * 以二进制形式表示int类型值
     * 
     * @param i
     * @return
     */
    private static char[] intToBinary(int i) {
        char[] values = new char[32];
        for (int pos = 31; pos >= 0; pos--, i >>= 1) {
            if ((i & (int) 0x01) == (int) 1)
                values[pos] = '1';
            else
                values[pos] = '0';
        }
        return values;
    }

    /**
     * 以二进制形式表示long类型值
     * 
     * @param l
     * @return
     */
    private static char[] longToBinary(long l) {
        char[] values = new char[64];
        for (int pos = 63; pos >= 0; pos--, l >>= 1) {
            if ((l & (long) 0x1) == (long) 1)
                values[pos] = '1';
            else
                values[pos] = '0';
        }
        return values;
    }

    /**
     * 将二进制形式字符串转换成八进制形式
     * 
     * @param binaryStr
     * @return
     */
    private static char[] binaryToOctal(char[] binaryStr) {
        return binaryToChar(binaryStr, 3);
    }

    /**
     * 将二进制形式字符串转换成十六进制形式
     * 
     * @param binaryStr
     * @return
     */
    private static char[] binaryToHex(char[] binaryStr) {
        return binaryToChar(binaryStr, 4);
    }

    /**
     * 将二进制形式的01字符串表示成由“0-9,a-f”字符或者“0-7”字符组成的字符串，每个字符对应“charLen”个二进制位
     * 
     * @param binaryStr
     * @param charLen
     * @return
     */
    private static char[] binaryToChar(char[] binaryStr, int charLen) {
        int len = binaryStr.length;
        int num = 0;
        if (len % charLen == 0)
            num = len / charLen;
        else
            num = len / charLen + 1;

        char[] values = new char[num];

        int end = len - 1;
        for (int pos = num - 1; pos >= 0; pos--) {
            values[pos] = getCharDigit(binaryStr, end - charLen + 1 >= 0 ? end - charLen + 1 : 0, end);
            end -= charLen;
        }
        return values;
    }

    /**
     * 将binaryStr中的[start,end]范围内的二进制字符串转换成对应的“0-9,a-f”中或者“0-7”中的字符
     * 
     * @param binaryStr
     * @param start
     * @param end
     * @return
     */
    private static char getCharDigit(char[] binaryStr, int start, int end) {
        int sum = 0;
        int multiplier = 1;
        for (int index = end; index >= start; index--) {
            if (binaryStr[index] == '1')
                sum += multiplier;
            multiplier *= 2;
        }
        if (sum < 10)
            return (char) ('0' + sum);
        else
            return (char) ('a' + sum - 10);
    }

    /**
     * 将二进制形式的字符串转换成toRadix所表示的进制形式的字符串
     * 
     * @param binaryStr
     * @param toRadix
     * @return
     */
    private static char[] fromBinary(char[] binaryStr, Radix toRadix) {
        if (toRadix == Radix.Binary)
            return binaryStr;
        else if (toRadix == Radix.Octal)
            return binaryToOctal(binaryStr);
        else
            return binaryToHex(binaryStr);
    }

    /**
     * 将整数表示成toRadix所表示的进制形式的字符串，整数为byte类型
     * 
     * @param b
     * @param toRadix
     * @return
     */
    public static String fromByte(byte b, Radix toRadix) {
        return new String(fromBinary(byteToBinary(b), toRadix));
    }

    /**
     * 将整数表示成toRadix所表示的进制形式的字符串，整数为short类型
     * 
     * @param s
     * @param toRadix
     * @return
     */
    public static String fromShort(short s, Radix toRadix) {
        return new String(fromBinary(shortToBinary(s), toRadix));
    }

    /**
     * 将整数表示成toRadix所表示的进制形式的字符串，整数为int类型
     * 
     * @param i
     * @param toRadix
     * @return
     */
    public static String fromInt(int i, Radix toRadix) {
        return new String(fromBinary(intToBinary(i), toRadix));
    }

    /**
     * 将整数表示成toRadix所表示的进制形式的字符串，整数为long类型
     * 
     * @param l
     * @param toRadix
     * @return
     */
    public static String fromLong(long l, Radix toRadix) {
        return new String(fromBinary(longToBinary(l), toRadix));
    }

    /**
     * chars是fromRadix所表示进制形式的字符串，转换成以二进制形式表示
     * 
     * @param chars
     * @param fromRadix
     * @return
     */
    private static char[] toBinary(char[] chars, Radix fromRadix) {
        if (fromRadix == Radix.Binary)
            return chars;
        else if (fromRadix == Radix.Octal)
            return toBinary(chars, 3);
        else
            return toBinary(chars, 4);
    }

    /**
     * 将十六进制，八进制形式表示的字符串转换成以二进制形式表示，charLen=3，表示原来以八进制形式表示，charLen=4，表示原来以十六进制形式表示
     * 
     * @param chars
     * @param charLen
     * @return
     */
    private static char[] toBinary(char[] chars, int charLen) {
        char[] values = new char[chars.length * charLen];
        int pos = 0;
        char[] binaryDigits;
        for (int index = 0; index < chars.length; index++) {
            binaryDigits = getBinaryDigits(chars[index], charLen);
            for (int index2 = 0; index2 < charLen; index2++)
                values[pos++] = binaryDigits[index2];
        }
        return values;
    }

    /**
     * 将某个十六进制，八进制字符转换成二进制串
     * 
     * @param c
     * @param charLen
     * @return
     */
    private static char[] getBinaryDigits(char c, int charLen) {
        char[] binaryDigits = new char[charLen];

        Arrays.fill(binaryDigits, '0');

        int value = 0;
        if (c <= '9' && c >= '0')
            value = c - '0';
        else
            value = c - 'a' + 10;

        int pos = charLen - 1;
        while (value != 0) {
            binaryDigits[pos--] = (value % 2 == 1) ? '1' : '0';
            value /= 2;
        }
        return binaryDigits;
    }

    /**
     * 将二进制字符串转换成byte类型的对象
     * 
     * @param binaryStr
     * @return
     */
    private static byte toByte(char[] binaryStr) {
        int start = binaryStr.length - 1;

        for (int index = 0; index < binaryStr.length - 1; index++) {
            if (binaryStr[index] == '1') {
                start = index;
                break;
            }
        }

        int end = binaryStr.length - 1;

        if (end - start + 1 > 8) {
            throw new RuntimeException("超出byte型的位数");
        } else if (end - start + 1 == 8) {
            // 第一位是符号位
            start++;

            byte sum = 0;
            byte multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }

            // “取反加一”等价于“减一取反”
            return (byte) (-(0x7f & (~(sum - 1))));
        } else {
            byte sum = 0;
            byte multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }
            return sum;
        }
    }

    /**
     * 将二进制字符串转换成short类型的对象
     * 
     * @param binaryStr
     * @return
     */
    private static short toShort(char[] binaryStr) {
        int start = binaryStr.length - 1;

        for (int index = 0; index < binaryStr.length - 1; index++) {
            if (binaryStr[index] == '1') {
                start = index;
                break;
            }
        }

        int end = binaryStr.length - 1;

        if (end - start + 1 > 16) {
            throw new RuntimeException("超出short型的位数");
        } else if (end - start + 1 == 16) {
            // 第一位是符号位
            start++;

            short sum = 0;
            short multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }

            // “取反加一”等价于“减一取反”
            return (short) -((0x7fff & (~(sum - 1))));
        } else {
            short sum = 0;
            short multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }
            return sum;
        }
    }

    /**
     * 将二进制字符串转换成int类型的对象
     * 
     * @param binaryStr
     * @return
     */
    private static int toInt(char[] binaryStr) {
        int start = binaryStr.length - 1;
        for (int index = 0; index < binaryStr.length - 1; index++) {
            if (binaryStr[index] == '1') {
                start = index;
                break;
            }
        }
        int end = binaryStr.length - 1;

        if (end - start + 1 > 32) {
            throw new RuntimeException("超出int型的位数");
        } else if (end - start + 1 == 32) {
            // 第一位是符号位
            start++;

            int sum = 0;
            int multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }

            // “取反加一”等价于“减一取反”
            return -(~(sum - 1));
        } else {
            int sum = 0;
            int multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }
            return sum;
        }
    }

    /**
     * 将二进制字符串转换成long类型的对象
     * 
     * @param binaryStr
     * @return
     */
    private static long toLong(char[] binaryStr) {
        int start = binaryStr.length - 1;
        for (int index = 0; index < binaryStr.length - 1; index++) {
            if (binaryStr[index] == '1') {
                start = index;
                break;
            }
        }
        int end = binaryStr.length - 1;

        if (end - start + 1 > 64) {
            throw new RuntimeException("超出long型的位数");
        } else if (end - start + 1 == 64) {
            // 第一位是符号位
            start++;

            long sum = 0;
            long multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }

            // “取反加一”等价于“减一取反”
            return -(~(sum - 1));
        } else {
            long sum = 0;
            long multiplier = 1;
            for (int index = end; index >= start; index--) {
                if (binaryStr[index] == '1')
                    sum += multiplier;
                multiplier *= 2;
            }
            return sum;
        }
    }


    /**
     * 将以fromRadix所表示进制形式表示的字符串转换成int类型对象
     * 
     * @param chars
     * @param fromRadix
     * @return
     */
    public static int toInt(char[] chars, Radix fromRadix) {
        return toInt(toBinary(chars, fromRadix));
    }

    /**
     * 将以fromRadix所表示进制形式表示的字符串转换成byte类型对象
     * 
     * @param chars
     * @param fromRadix
     * @return
     */
    public static byte toByte(char[] chars, Radix fromRadix) {
        return toByte(toBinary(chars, fromRadix));
    }

    /**
     * 将以fromRadix所表示进制形式表示的字符串转换成short类型对象
     * 
     * @param chars
     * @param fromRadix
     * @return
     */
    public static short toShort(char[] chars, Radix fromRadix) {
        return toShort(toBinary(chars, fromRadix));
    }

    /**
     * 将以fromRadix所表示进制形式表示的字符串转换成long类型对象
     * 
     * @param chars
     * @param fromRadix
     * @return
     */
    public static long toLong(char[] chars, Radix fromRadix) {
        return toLong(toBinary(chars, fromRadix));
    }

    /**
     * 进制类型枚举
     */
    public enum Radix {
        Binary(2), Octal(8), Hex(16);

        private int radix;

        Radix(int radix) {
            this.radix = radix;
        }

        public int getRadix() {
            return radix;
        }
    }
}
