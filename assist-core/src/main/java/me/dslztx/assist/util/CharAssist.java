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
        return input.codePoints().filter(codePoint -> !isInvisibleCharacter(codePoint))
            .mapToObj(codePoint -> new String(Character.toChars(codePoint))).collect(Collectors.joining());
    }

    /**
     * 字符串中是否存在不可见字符
     *
     * @param input
     * @return
     */
    public static boolean existInvisibleCharacter(String input) {

        // 处理空值和空字符串，避免空指针
        if (input == null || input.isEmpty()) {
            return false;
        }

        int[] strCodePointArray = input.codePoints().toArray();

        for (int charCodePoint : strCodePointArray) {
            if (isInvisibleCharacter(charCodePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断单个 Unicode 码点是否为不可见字符
     *
     * @param codePoint 字符的 Unicode 码点
     * @return true=不可见，false=可见
     */
    protected static boolean isInvisibleCharacter(int codePoint) {
        // 排除空白字符（空格、制表符、换行、回车等）
        // if (Character.isWhitespace(codePoint)) {
        // return true;
        // }
        // 空白字符属于可见字符

        // 1. 格式控制字符（Unicode类别：Cf）
        if (Character.getType(codePoint) == Character.FORMAT) {
            return true;
        }

        // 排除控制字符（如 \0、\b 等）
        if (Character.isISOControl(codePoint)) {
            return true;
        }

        // 3. 其他常见不可见字符（补充判断，防止遗漏）
        // 零宽字符范围：U+2000~U+200F、U+2028~U+202F、U+2060~U+206F 等
        return (codePoint >= 0x2000 && codePoint <= 0x200F) || (codePoint >= 0x2028 && codePoint <= 0x202F)
            || (codePoint >= 0x2060 && codePoint <= 0x206F) || (codePoint >= 0x1800 && codePoint <= 0x180F);
    }

}
