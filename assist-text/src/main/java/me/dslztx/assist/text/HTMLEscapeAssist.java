package me.dslztx.assist.text;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * “&”的正常转义映射是"&amp;"
 * </p>
 * 反转义时，除了上述正常转义映射，还有"&#38;" -> "&"转义映射
 */
public class HTMLEscapeAssist {

    public static String unescape(String input) {
        return StringEscapeUtils.unescapeHtml4(input);
    }

    public static String escape(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }

}
