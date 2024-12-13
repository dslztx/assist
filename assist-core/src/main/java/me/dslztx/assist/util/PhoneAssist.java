package me.dslztx.assist.util;

import java.util.regex.Pattern;

public class PhoneAssist {

    private static final Pattern regex =
        Pattern.compile("1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}");

    public static boolean isPhone(String s) {
        if (StringAssist.isBlank(s) || s.length() != 11) {
            return false;
        }

        if (s.charAt(0) != '1') {
            return false;
        }

        char c;
        for (int index = 1; index < s.length(); index++) {
            c = s.charAt(index);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPhoneRegex(String s) {
        if (StringAssist.isBlank(s) || s.length() != 11) {
            return false;
        }

        return regex.matcher(s).matches();
    }
}
