package me.dslztx.assist.util;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class StringAssist {
    private static final Logger logger = LoggerFactory.getLogger(StringAssist.class);

    /**
     * judge whether legal hexadecimal number string
     */
    public static boolean isHexStr(String s) {
        if (isBlank(s)) {
            return false;
        }

        for (int index = 0; index < s.length(); index++) {
            if (!CharAssist.isHexChar(s.charAt(index))) {
                return false;
            }
        }
        return true;
    }

    /**
     * judge whether legal octal number string
     */
    public static boolean isOctStr(String s) {
        if (isBlank(s)) {
            return false;
        }

        for (int index = 0; index < s.length(); index++) {
            if (!CharAssist.isOctChar(s.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    /**
     * judge whether legal decimal number string
     */
    public static boolean isDecimalStr(String s) {
        if (isBlank(s)) {
            return false;
        }

        for (int index = 0; index < s.length(); index++) {
            if (!CharAssist.isDecimalChar(s.charAt(index))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        char c;
        for (int index = 0; index < s.length(); index++) {
            c = s.charAt(index);
            if (c != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static boolean isEmpty(String s) {
        if (s == null || s.length() == 0)
            return true;
        return false;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String toLowerCase(String s) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase();
    }

    public static String toUpperCase(String s) {
        if (s == null) {
            return null;
        }
        return s.toUpperCase();
    }

    public static String[] split(String s, char separator, boolean excludeEmpty) {
        if (excludeEmpty) {
            if (s == null || s.length() == 0) {
                return new String[0];
            }

            List<String> result = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            char c;
            for (int index = 0; index < s.length(); index++) {
                c = s.charAt(index);
                if (c == separator) {
                    if (sb.length() != 0) {
                        result.add(sb.toString());
                    }
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }

            if (sb.length() != 0) {
                result.add(sb.toString());
            }

            return result.toArray(new String[result.size()]);
        } else {
            if (s == null) {
                return new String[0];
            }

            List<String> result = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            char c;
            for (int index = 0; index < s.length(); index++) {
                c = s.charAt(index);
                if (c == separator) {
                    result.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }

            result.add(sb.toString());

            return result.toArray(new String[result.size()]);
        }
    }

    public static String splitLastPart(String s, char separator) {
        if (StringAssist.isBlank(s)) {
            return "";
        }

        int index = s.lastIndexOf(separator);
        if (index == -1) {
            return s;
        }

        return s.substring(index + 1);
    }

    public static String splitFirstPart(String s, char separator) {
        if (StringAssist.isBlank(s)) {
            return "";
        }

        int index = s.indexOf(separator);
        if (index == -1) {
            return s;
        }

        return s.substring(0, index);
    }

    public static String removeChar(String s, char... cc) {
        if (StringAssist.isBlank(s)) {
            return s;
        }
        Set<Character> set = new HashSet<Character>();
        for (char c : cc) {
            set.add(c);
        }
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (!set.contains(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String joinUseSeparator(Collection<String> set, char separator) {
        if (CollectionAssist.isEmpty(set)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String element : set) {
            if (ObjectAssist.isNull(element)) {
                sb.append("NULL");
            } else {
                sb.append(element);
            }
            sb.append(separator);
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String concat(Collection<String> set) {
        if (CollectionAssist.isEmpty(set)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String element : set) {
            if (ObjectAssist.isNull(element)) {
                sb.append("NULL");
            } else {
                sb.append(element);
            }
        }

        return sb.toString();
    }

    public static String joinUseSeparator(char separator, String... elements) {
        if (ArrayAssist.isEmpty(elements)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (String element : elements) {
            if (ObjectAssist.isNull(element)) {
                sb.append("NULL");
            } else {
                sb.append(element);
            }
            sb.append(separator);
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String joinUseSeparator(char separator, Object... elements) {
        if (ArrayAssist.isEmpty(elements)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (Object element : elements) {
            if (ObjectAssist.isNull(element)) {
                sb.append("NULL");
            } else {
                sb.append(element);
            }
            sb.append(separator);
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String joinUseSeparator(int[] intArray, char separator) {
        if (ArrayAssist.isEmpty(intArray)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int element : intArray) {
            sb.append(element);
            sb.append(separator);
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String joinUseSeparator(String[] strArray, char separator) {
        if (ArrayAssist.isEmpty(strArray)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String element : strArray) {
            sb.append(element);
            sb.append(separator);
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 参考日志框架的格式字符串语义
     */
    public static String format(String formatStr, Object... elements) {
        if (StringAssist.isBlank(formatStr)) {
            return formatStr;
        }

        if (elements == null || elements.length == 0) {
            return formatStr;
        }

        Object[] elementArray = new Object[elements.length];
        int eIndex = 0;
        for (Object element : elements) {
            elementArray[eIndex++] = element;
        }

        StringBuilder sb = new StringBuilder();
        eIndex = 0;
        for (int index = 0; index < formatStr.length(); index++) {
            if (formatStr.charAt(index) == '{') {
                if (index + 1 < formatStr.length() && formatStr.charAt(index + 1) == '}') {
                    sb.append(elementArray[eIndex++]);
                    index++;

                    if (eIndex == elementArray.length) {
                        sb.append(formatStr.substring(index + 1));
                        return sb.toString();
                    }
                } else {
                    sb.append(formatStr.charAt(index));
                }
            } else {
                sb.append(formatStr.charAt(index));
            }
        }

        return sb.toString();
    }

    public static String truncateWhitespace(String s) {
        if (StringAssist.isEmpty(s)) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        char c;
        for (int index = 0; index < s.length(); index++) {
            c = s.charAt(index);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static List<String> allPossibleSuffixesAtLeastNParts(String[] parts, int n, char separator) {
        List<String> result = new ArrayList<String>();

        if (n < 1) {
            logger.info("n must >= 1");
            return result;
        }

        if (ArrayAssist.isEmpty(parts) || parts.length < n) {
            return result;
        }

        StringBuilder sb = new StringBuilder();

        String suffix = null;
        if (n == 1) {
            suffix = parts[parts.length - 1];
        } else {
            int startPos = parts.length - n;

            sb.setLength(0);

            for (int index = startPos; index < parts.length; index++) {
                sb.append(parts[index]);
                sb.append(separator);
            }

            sb.setLength(sb.length() - 1);

            suffix = sb.toString();
        }

        result.add(suffix);

        for (int index = parts.length - n - 1; index >= 0; index--) {

            sb.setLength(0);

            sb.append(parts[index]);
            sb.append(separator);
            sb.append(suffix);

            suffix = sb.toString();

            result.add(suffix);
        }

        return result;
    }

    public static String removeControlChars(String s) {
        if (StringAssist.isBlank(s)) {
            return s;
        }

        return s.replaceAll("[\u200b-\u200f\u2029-\u202e\u2061-\u2064\u206a-\u206f]", "");
    }

    /**
     * 处理\u202e和\u202d这两个文本顺序控制字符
     * 
     * @param s
     * @return
     */
    public static String processTextOrderChars(String s) {
        if (StringAssist.isBlank(s)) {
            return s;
        }

        Deque<Character> stack = new ArrayDeque<>(s.length());

        for (int index = 0; index < s.length(); index++) {
            stack.push(s.charAt(index));
        }

        Object[] aa = new Object[s.length()];
        int size = 0;

        char c;
        StringBuilder tmp = new StringBuilder();

        while (!stack.isEmpty()) {
            c = stack.pop();

            if (CharAssist.isRLOControlChar(c) || CharAssist.isLROControlChar(c)) {
                if (size == 0) {
                    continue;
                }

                if (CharAssist.isRLOControlChar(c)) {
                    tmp.setLength(0);
                    for (int index = 0; index < size; index++) {
                        tmp.append(aa[index]);
                    }
                    size = 0;
                    aa[size++] = tmp.toString();
                } else {
                    tmp.setLength(0);
                    for (int index = size - 1; index >= 0; index--) {
                        tmp.append(aa[index]);
                    }
                    size = 0;
                    aa[size++] = tmp.toString();
                }
            } else {
                aa[size++] = c;
            }
        }

        if (size == 0) {
            return "";
        } else if (size == 1) {
            return aa[0].toString();
        } else {
            StringBuilder sb = new StringBuilder();
            for (int index = size - 1; index >= 0; index--) {
                sb.append(aa[index]);
            }
            return sb.toString();
        }
    }
}
