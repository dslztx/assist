package com.dslztx.util;

/**
 * 字符工具类
 * 
 * @author dslztx
 * @date 2015年08月17日
 */
public class CharUtil {
    /**
     * 是否是十六进制字符
     * 
     * @param c
     * @return
     */
    public static boolean isHexChar(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    /**
     * 是否是八进制字符
     * 
     * @param c
     * @return
     */
    public static boolean isOctChar(char c) {
        return (c >= '0' && c <= '7');
    }

    /**
     * 是否是十进制字符
     * 
     * @param c
     * @return
     */
    public static boolean isDecimalChar(char c) {
        return isDigitChar(c);
    }

    /**
     * 是否是数字字符
     * 
     * @param c
     * @return
     */
    public static boolean isDigitChar(char c) {
        return (c >= '0' && c <= '9');
    }
}
