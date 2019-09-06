package me.dslztx.assist.util;

import java.util.*;

/**
 * @author dslztx
 */
public class StringAssist {

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
}
