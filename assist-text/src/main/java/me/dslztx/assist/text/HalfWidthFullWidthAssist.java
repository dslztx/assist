package me.dslztx.assist.text;

import me.dslztx.assist.util.StringAssist;

/**
 * https://topic.alibabacloud.com/a/java-full-angle-half-width-character-relationships-and-conversion-details-_java_1_27_20134860.html
 *
 * 合法的半角字符：32-126
 *
 * 不是所有的全角字符都有相应的半角字符
 * 
 * 不能转换的保留原字符
 */
public class HalfWidthFullWidthAssist {

    public static String fullWidth2HalfWidth(String fullWidthStr) {
        if (StringAssist.isBlank(fullWidthStr)) {
            return "";
        }

        char[] charArray = fullWidthStr.toCharArray();

        for (int i = 0; i < charArray.length; ++i) {
            int charValue = (int)charArray[i];

            if (charValue >= 65281 && charValue <= 65374) {
                charArray[i] = (char)(charValue - 65248);
            } else if (charValue == 12288) {
                charArray[i] = (char)32;
            } else {
                // 即保持不变
            }
        }

        return new String(charArray);
    }

    public static String halfWidth2FullWidth(String halfWidthStr) {
        if (StringAssist.isBlank(halfWidthStr)) {
            return "";
        }

        char[] charArray = halfWidthStr.toCharArray();

        for (int i = 0; i < charArray.length; ++i) {
            int charValue = (int)charArray[i];

            if (charValue >= 33 && charValue <= 126) {
                charArray[i] = (char)(charValue + 65248);
            } else if (charValue == 32) {
                charArray[i] = (char)12288;
            } else {
                // 即保持不变
            }
        }

        return new String(charArray);
    }

}
