package me.dslztx.assist.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dslztx
 */
@Deprecated
public class TextAssist {

    private static final Language[] lls = new Language[] {Language.CHINESE, Language.KOREA, Language.JAPAN,
        Language.GREEK, Language.LATIN, Language.ARABIC, Language.CYRILLIC};

    private static final Map<Character.UnicodeScript, List<Language>> map = new HashMap<>();

    static {
        map.put(Character.UnicodeScript.HAN, Arrays.asList(Language.CHINESE, Language.JAPAN));
        map.put(Character.UnicodeScript.HANGUL, Arrays.asList(Language.KOREA));
        map.put(Character.UnicodeScript.HIRAGANA, Arrays.asList(Language.JAPAN));
        map.put(Character.UnicodeScript.GREEK, Arrays.asList(Language.GREEK));
        map.put(Character.UnicodeScript.LATIN, Arrays.asList(Language.LATIN));
        map.put(Character.UnicodeScript.ARABIC, Arrays.asList(Language.ARABIC));
        map.put(Character.UnicodeScript.CYRILLIC, Arrays.asList(Language.CYRILLIC));
    }

    public static Language guessLanguage(String text) {
        if (StringAssist.isBlank(text)) {
            return Language.UNKNOWN;
        }

        Map<Language, Integer> cnt = new HashMap<>();
        char c;
        Character.UnicodeScript cu;
        List<Language> languages;

        for (int index = 0; index < text.length(); index++) {
            c = text.charAt(index);

            cu = Character.UnicodeScript.of((int)c);

            if (ObjectAssist.isNotNull(cu)) {
                languages = map.get(cu);

                if (CollectionAssist.isNotEmpty(languages)) {
                    for (Language language : languages) {
                        if (ObjectAssist.isNull(cnt.get(language))) {
                            cnt.put(language, 0);
                        }

                        cnt.put(language, cnt.get(language) + 1);
                    }
                }

            }
        }

        int maxv = -1;
        Language selected = null;

        for (Language language : lls) {
            if (ObjectAssist.isNotNull(cnt.get(language)) && cnt.get(language) > maxv) {
                maxv = cnt.get(language);
                selected = language;
            }
        }

        if (ObjectAssist.isNull(selected)) {
            return Language.UNKNOWN;
        } else {
            if (maxv * 10 > text.length() * 3) {
                return selected;
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
