package me.dslztx.assist.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
