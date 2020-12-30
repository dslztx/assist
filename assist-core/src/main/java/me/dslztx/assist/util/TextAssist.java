package me.dslztx.assist.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dslztx
 */
public class TextAssist {

    private static final Character.UnicodeScript[] cus = new Character.UnicodeScript[] {Character.UnicodeScript.HAN,
        Character.UnicodeScript.HANGUL, Character.UnicodeScript.HIRAGANA, Character.UnicodeScript.GREEK,
        Character.UnicodeScript.LATIN, Character.UnicodeScript.ARABIC, Character.UnicodeScript.CYRILLIC};

    private static final Map<Character.UnicodeScript, Language> map = new HashMap<>();

    static {
        map.put(Character.UnicodeScript.HAN, Language.CHINESE);
        map.put(Character.UnicodeScript.HANGUL, Language.KOREA);
        map.put(Character.UnicodeScript.HIRAGANA, Language.JAPAN);
        map.put(Character.UnicodeScript.GREEK, Language.GREEK);
        map.put(Character.UnicodeScript.LATIN, Language.LATIN);
        map.put(Character.UnicodeScript.ARABIC, Language.ARABIC);
        map.put(Character.UnicodeScript.CYRILLIC, Language.CYRILLIC);
    }

    public static Language guessLanguage(String text) {
        if (StringAssist.isBlank(text)) {
            return Language.UNKNOWN;
        }

        Map<Character.UnicodeScript, Integer> cnt = new HashMap<>();
        char c;
        Character.UnicodeScript cu;

        for (int index = 0; index < text.length(); index++) {
            c = text.charAt(index);

            cu = Character.UnicodeScript.of((int)c);

            if (ObjectAssist.isNotNull(cu)) {
                if (ObjectAssist.isNull(cnt.get(cu))) {
                    cnt.put(cu, 0);
                }

                cnt.put(cu, cnt.get(cu) + 1);
            }
        }

        int maxv = -1;
        Character.UnicodeScript selected = null;

        for (Character.UnicodeScript cu2 : cus) {
            if (ObjectAssist.isNotNull(cnt.get(cu2)) && cnt.get(cu2) > maxv) {
                maxv = cnt.get(cu2);
                selected = cu2;
            }
        }

        if (ObjectAssist.isNull(selected)) {
            return Language.UNKNOWN;
        } else {
            if (maxv * 10 > text.length() * 3) {
                return map.get(selected);
            } else {
                return Language.UNKNOWN;
            }
        }
    }

    /**
     * 拉丁字母、阿拉伯字母、斯拉夫字母是世界三大拼音文字系统
     */
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
