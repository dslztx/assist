package me.dslztx.assist.util;

public class NumberAssist {

    /**
     * 先不考虑溢出情形
     */
    public static int hexStrToDec(String s, int start, int end) {
        int value = 0;

        if (StringAssist.isEmpty(s) || start >= end || end >= s.length()) {
            return value;
        }

        char c;
        for (int index = start; index <= end; index++) {
            c = s.charAt(index);

            if (c >= '0' && c <= '9') {
                value = 16 * value + (c - '0');
            } else if (c >= 'a' && c <= 'f') {
                value = 16 * value + (c - 'a' + 10);
            } else if (c >= 'A' && c <= 'F') {
                value = 16 * value + (c - 'A' + 10);
            } else {
                throw new NumberFormatException("illegal hex char");
            }
        }

        return value;
    }

    /**
     * 先不考虑溢出情形
     */
    public static int decStrToDec(String s, int start, int end) throws NumberFormatException {
        int value = 0;

        if (StringAssist.isEmpty(s) || start >= end || end > s.length()) {
            return value;
        }

        char c;
        for (int index = start; index <= end; index++) {
            c = s.charAt(index);

            if (c >= '0' && c <= '9') {
                value = 10 * value + (c - '0');
            } else {
                throw new NumberFormatException("illegal dec char");
            }
        }

        return value;
    }
}
