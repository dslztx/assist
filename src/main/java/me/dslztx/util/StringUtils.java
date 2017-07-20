package me.dslztx.util;

/**
 * 字符串工具类
 * 
 * @author dslztx
 * @date 2015年08月17日
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
            if (!CharUtil.isHexChar(s.charAt(index)))
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
            if (!CharUtil.isOctChar(s.charAt(index)))
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
            if (!CharUtil.isDecimalChar(s.charAt(index)))
                return false;
        }
        return true;
    }
}
