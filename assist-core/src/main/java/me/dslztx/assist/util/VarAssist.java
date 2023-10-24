package me.dslztx.assist.util;

public class VarAssist {

    public static String parse(String s) {

        if (StringAssist.isBlank(s) || s.length() < 3) {
            return s;
        }

        if (s.startsWith("${") && s.endsWith("}")) {
            s = s.substring(2, s.length() - 1);

            int pos = s.indexOf(":");
            if (pos == -1) {
                return System.getenv(s);
            } else {
                String name = s.substring(0, pos);
                String defaultValue = s.substring(pos + 1);

                String result = System.getenv(name);
                if (StringAssist.isBlank(result)) {
                    return defaultValue;
                } else {
                    return result;
                }
            }
        } else {
            return s;
        }
    }
}
