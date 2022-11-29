package me.dslztx.assist.algorithm;

import org.apache.commons.codec.binary.Base64;

import me.dslztx.assist.util.ArrayAssist;
import me.dslztx.assist.util.StringAssist;

public class Base64Assist {

    private static final boolean[] legalBase64Char = new boolean[128];

    static {
        char c;
        for (c = 'A'; c <= 'Z'; c++) {
            legalBase64Char[c] = true;
        }

        for (c = 'a'; c <= 'z'; c++) {
            legalBase64Char[c] = true;
        }

        for (c = '0'; c <= '9'; c++) {
            legalBase64Char[c] = true;
        }

        legalBase64Char['+'] = true;
        legalBase64Char['/'] = true;
        legalBase64Char['='] = true;
    }

    public static String encodeBase64(byte[] bb) {
        if (ArrayAssist.isEmpty(bb)) {
            return null;
        }

        return Base64.encodeBase64String(bb);
    }

    public static byte[] decodeBase64(String s) {
        if (StringAssist.isBlank(s)) {
            return null;
        }

        return Base64.decodeBase64(s);
    }

    public static boolean isLegalBase64Len(String s) {
        if (StringAssist.isEmpty(s)) {
            return true;
        }

        if (s.length() * 6 % 8 == 0) {
            return true;
        }

        return false;
    }

    public static boolean isLegalBase64Char(String s) {
        if (StringAssist.isBlank(s)) {
            return true;
        }

        char c;
        for (int index = 0; index < s.length(); index++) {
            c = s.charAt(index);
            if (c < 128 && legalBase64Char[c]) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }
}
