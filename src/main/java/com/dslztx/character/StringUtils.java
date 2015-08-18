package com.dslztx.character;

/**
 * 关于字符串的工具类
 * 
 * @author dslztx
 */
public class StringUtils {

    /**
     * 是否是一个合法的十六进制字符串
     * 
     * @param s
     * @return
     */
    public static boolean isHexStr(String s) {
        for (int index = 0; index < s.length(); index++) {
            if (!CharacterUtils.isHexChar(s.charAt(index)))
                return false;
        }
        return true;
    }

    /**
     * 是否是一个合法的八进制字符串
     * 
     * @param s
     * @return
     */
    public static boolean isOctStr(String s) {
        for (int index = 0; index < s.length(); index++) {
            if (!CharacterUtils.isOctChar(s.charAt(index)))
                return false;
        }
        return true;
    }

    /**
     * 是否是一个合法的十进制字符串
     * 
     * @param s
     * @return
     */
    public static boolean isDecimalStr(String s) {
        for (int index = 0; index < s.length(); index++) {
            if (!CharacterUtils.isDecimalChar(s.charAt(index)))
                return false;
        }
        return true;
    }
}
