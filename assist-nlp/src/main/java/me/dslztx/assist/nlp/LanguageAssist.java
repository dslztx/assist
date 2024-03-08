package me.dslztx.assist.nlp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.ObjectAssist;
import me.dslztx.assist.util.StringAssist;

/**
 * 语言识别工具类
 */
public class LanguageAssist {

    /**
     * 中文汉字（不包括中文标点符号）UnicodeRange下界值，其对应十进制值为19968
     */
    private static final int CHINESE_CHAR_UNICODE_RANGE_LOWER_BOUND = 0x4e00;
    /**
     * 中文汉字（不包括中文标点符号）UnicodeRange上界值，其对应十进制值为40869
     */
    private static final int CHINESE_CHAR_UNICODE_RANGE_UPPER_BOUND = 0x9fa5;

    private static final LanguageAssist.Language[] lls = new LanguageAssist.Language[] {LanguageAssist.Language.CHINESE,
        LanguageAssist.Language.KOREA, LanguageAssist.Language.JAPAN, LanguageAssist.Language.GREEK,
        LanguageAssist.Language.LATIN, LanguageAssist.Language.ARABIC, LanguageAssist.Language.CYRILLIC};

    private static final Map<Character.UnicodeScript, List<LanguageAssist.Language>> map = new HashMap<>();

    static {
        map.put(Character.UnicodeScript.HAN,
            Arrays.asList(LanguageAssist.Language.CHINESE, LanguageAssist.Language.JAPAN));
        map.put(Character.UnicodeScript.HANGUL, Arrays.asList(LanguageAssist.Language.KOREA));
        map.put(Character.UnicodeScript.HIRAGANA, Arrays.asList(LanguageAssist.Language.JAPAN));
        map.put(Character.UnicodeScript.GREEK, Arrays.asList(LanguageAssist.Language.GREEK));
        map.put(Character.UnicodeScript.LATIN, Arrays.asList(LanguageAssist.Language.LATIN));
        map.put(Character.UnicodeScript.ARABIC, Arrays.asList(LanguageAssist.Language.ARABIC));
        map.put(Character.UnicodeScript.CYRILLIC, Arrays.asList(LanguageAssist.Language.CYRILLIC));
    }

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

    public static LanguageAssist.Language guessLanguage(String text) {
        if (StringAssist.isBlank(text)) {
            return LanguageAssist.Language.UNKNOWN;
        }

        Map<LanguageAssist.Language, Integer> cnt = new HashMap<>();
        char c;
        Character.UnicodeScript cu;
        List<LanguageAssist.Language> languages;

        for (int index = 0; index < text.length(); index++) {
            c = text.charAt(index);

            cu = Character.UnicodeScript.of((int)c);

            if (ObjectAssist.isNotNull(cu)) {
                languages = map.get(cu);

                if (CollectionAssist.isNotEmpty(languages)) {
                    for (LanguageAssist.Language language : languages) {
                        if (ObjectAssist.isNull(cnt.get(language))) {
                            cnt.put(language, 0);
                        }

                        cnt.put(language, cnt.get(language) + 1);
                    }
                }

            }
        }

        int maxv = -1;
        LanguageAssist.Language selected = null;

        for (LanguageAssist.Language language : lls) {
            if (ObjectAssist.isNotNull(cnt.get(language)) && cnt.get(language) > maxv) {
                maxv = cnt.get(language);
                selected = language;
            }
        }

        if (ObjectAssist.isNull(selected)) {
            return LanguageAssist.Language.UNKNOWN;
        } else {
            if (maxv * 10 > text.length() * 3) {
                return selected;
            } else {
                return LanguageAssist.Language.UNKNOWN;
            }
        }
    }

    public static enum Language {
        /**
         * 汉语
         */
        CHINESE,

        /**
         * 朝鲜语
         */
        KOREA,

        /**
         * 日本语
         */
        JAPAN,

        /**
         * 希腊语
         */
        GREEK,

        /**
         * 拉丁字母语系，可能是英语、法语、德语、西班牙语、意大利语等
         */
        LATIN,

        /**
         * 阿拉伯字母语系，可能是阿拉伯语、哈萨克语、吉尔吉斯语、维吾尔语等
         */
        ARABIC,

        /**
         * 斯拉夫字母语系，可能是俄语、乌克兰语、卢森尼亚语、白俄罗斯语、保加利亚语、塞尔维亚语、马其顿语等
         */
        CYRILLIC,

        UNKNOWN;
    }
}
