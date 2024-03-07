package me.dslztx.assist.nlp;

import java.util.regex.Pattern;

import me.dslztx.assist.util.StringAssist;

/**
 * 语言识别工具类
 */
public class LanguageRecognizeAssist {

    /**
     * 中文汉字（不包括中文标点符号）UnicodeRange下界值，其对应十进制值为19968
     */
    private static int CHINESE_CHAR_UNICODE_RANGE_LOWER_BOUND = 0x4e00;

    /**
     * 中文汉字（不包括中文标点符号）UnicodeRange上界值，其对应十进制值为40869
     */
    private static int CHINESE_CHAR_UNICODE_RANGE_UPPER_BOUND = 0x9fa5;

    /**
     * 根据UnicodeRange识别中文汉字（不包括中文标点符号）
     */
    public static boolean isChineseCharByUnicodeRange(char c) {
        return c >= CHINESE_CHAR_UNICODE_RANGE_LOWER_BOUND && c <= CHINESE_CHAR_UNICODE_RANGE_UPPER_BOUND;
    }

    /**
     * 根据UnicodeBlock识别中文汉字（不包括中文标点符号）
     */
    public static boolean isChineseCharByUnicodeBlock(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据UnicodeScript识别中文汉字（不包括中文标点符号）
     */
    public static boolean isChineseCharByUnicodeScript(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);

        if (Character.UnicodeScript.HAN == sc) {
            return true;
        }

        return false;
    }

    /**
     * 识别中文标点符号，内部是根据UnicodeBlock
     */
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 整个文本是否全是中文汉字（不包括中文标点符号）
     */
    public static boolean isChineseCharWhole(String str) {
        if (StringAssist.isBlank(str)) {
            return false;
        }

        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(str.trim()).matches();
    }

    /**
     * 整个文本是否存在中文汉字（不包括中文标点符号）
     */
    public static boolean existChineseChar(String str) {
        if (StringAssist.isBlank(str)) {
            return false;
        }

        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 整个文本是否全是中文汉字或者中文标点符号
     */
    public static boolean isChineseCharOrPunctuationWhole(String str) {
        if (StringAssist.isBlank(str)) {
            return false;
        }

        char c;

        for (int index = 0; index < str.length(); index++) {
            c = str.charAt(index);

            if (!(isChineseCharByUnicodeRange(c) || isChinesePunctuation(c))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 整个文本是否存在中文汉字或者中文标点符号
     */
    public static boolean existChineseCharOrPunctuation(String str) {
        if (StringAssist.isBlank(str)) {
            return false;
        }
        char c;

        for (int index = 0; index < str.length(); index++) {
            c = str.charAt(index);

            if (isChineseCharByUnicodeRange(c) || isChinesePunctuation(c)) {
                return true;
            }
        }

        return false;
    }
}
