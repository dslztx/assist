package me.dslztx.assist.util;

import java.util.regex.Pattern;

public class IPAssist {

    private static final Pattern IPV4_PATTERN =
        Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public static String obtainIPCIPv4(String ip) {
        if (StringAssist.isBlank(ip)) {
            return null;
        }

        if (!IPV4_PATTERN.matcher(ip).matches()) {
            return null;
        }

        // can not be -1 here
        int index = ip.lastIndexOf(".");
        return ip.substring(0, index);
    }

    public static String obtainIPCIPv4Simply(String ip) {
        if (StringAssist.isBlank(ip)) {
            return null;
        }

        int index = ip.lastIndexOf(".");
        if (index == -1)
            return null;

        return ip.substring(0, index);
    }
}
