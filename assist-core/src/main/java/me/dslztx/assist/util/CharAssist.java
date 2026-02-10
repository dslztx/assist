package me.dslztx.assist.util;

import java.util.stream.Collectors;

/**
 * @author dslztx
 */
public class CharAssist {

    public static boolean isHexChar(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    public static boolean isOctChar(char c) {
        return (c >= '0' && c <= '7');
    }

    public static boolean isDecimalChar(char c) {
        return isDigitChar(c);
    }

    public static boolean isDigitChar(char c) {
        return (c >= '0' && c <= '9');
    }

    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRLOControlChar(char c) {
        return c == '\u202e';
    }

    public static boolean isLROControlChar(char c) {
        return c == '\u202d';
    }

    public static boolean isEnglishChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEnglishPunctuation(char c) {
        return (c >= 32 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 126);
    }

    public static boolean isControlChar(char c) {
        int type = Character.getType(c);

        if (type == 15 || type == 16) {
            return true;
        }

        return false;
    }

    /**
     * 移除字符串中的所有不可见字符（控制字符、零宽字符等）
     *
     * @param input 待处理的原始字符串
     * @return 移除不可见字符后的新字符串（null/空字符串返回原内容）
     */
    public static String removeInvisibleCharacters(String input) {
        // 处理空值和空字符串，避免空指针
        if (input == null || input.isEmpty()) {
            return input;
        }

        // 遍历字符串的每个码点，过滤不可见字符后拼接
        return input.codePoints().filter(codePoint -> isVisibleCharacter(codePoint))
            .mapToObj(codePoint -> new String(Character.toChars(codePoint))).collect(Collectors.joining());
    }

    /**
     * 判断单个 Unicode 码点是否为可见字符
     *
     * @param codePoint 字符的 Unicode 码点
     * @return true=可见，false=不可见
     */
    private static boolean isVisibleCharacter(int codePoint) {
        // 排除空白字符（空格、制表符、换行、回车等）
        // if (Character.isWhitespace(codePoint)) {
        // return false;
        // }
        // 空白字符属于可见字符

        // 排除控制字符（如 \0、\b 等）
        if (Character.isISOControl(codePoint)) {
            return false;
        }

        // 补充排除常见的零宽字符（零宽空格、零宽非连接符、零宽连接符）
        switch (codePoint) {
            case 0x200B: // 零宽空格 (ZWSP)
            case 0x200C: // 零宽非连接符 (ZWNJ)
            case 0x200D: // 零宽连接符 (ZWJ)
            case 0xFEFF: // 字节顺序标记 (BOM)
                return false;
            default:
                // 其余字符判定为可见（包括字母、数字、符号、Emoji、汉字等）
                return true;
        }
    }

}
