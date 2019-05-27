package me.dslztx.assist.util;

import java.util.regex.Pattern;

public class IPAssist {

    private static final Pattern IPV4_PATTERN =
        Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    /**
     * 是否是合法的IPv4地址
     */
    public static boolean isIPv4(String ip) {
        return IPV4_PATTERN.matcher(ip).matches();
    }

    /**
     * 是否是合法的IPv6地址
     */
    public static boolean isIPv6(String ip) {
        // todo
        return false;
    }

    /**
     * 是否是合法的IPv4地址或者IPv6地址
     */
    public static boolean isIP(String ip) {
        return isIPv4(ip) || isIPv6(ip);
    }

    public static String obtainIPCIPv4(String ip) {
        if (StringAssist.isBlank(ip)) {
            return null;
        }

        if (!IPV4_PATTERN.matcher(ip).matches()) {
            return null;
        }

        // can not be -1 here
        int index = ip.lastIndexOf('.');
        return ip.substring(0, index);
    }

    public static String obtainIPCIPv4Simply(String ip) {
        if (StringAssist.isBlank(ip)) {
            return null;
        }

        int index = ip.lastIndexOf('.');
        if (index == -1)
            return null;

        return ip.substring(0, index);
    }

    public static Long encodeIP(String ip) {
        if (StringAssist.isBlank(ip)) {
            return null;
        }

        if (!IPV4_PATTERN.matcher(ip).matches()) {
            return null;
        }

        String[] ipSegments = ip.split("\\.");

        return (Long.parseLong(ipSegments[0]) << 24) + (Long.parseLong(ipSegments[1]) << 16)
            + (Long.parseLong(ipSegments[2]) << 8) + Long.parseLong(ipSegments[3]);
    }

    public static String decodeIP(Long encodedIPValue) {
        if (ObjectAssist.isNull(encodedIPValue)) {
            return null;
        }

        Long ipSegment0 = encodedIPValue >>> 24;
        if (ipSegment0 > 255 || ipSegment0 < 0) {
            return null;
        }

        Long ipSegment1 = (encodedIPValue & 0x00FFFFFF) >>> 16;
        if (ipSegment1 > 255 || ipSegment1 < 0) {
            return null;
        }

        Long ipSegment2 = (encodedIPValue & 0x0000FFFF) >>> 8;
        if (ipSegment2 > 255 || ipSegment2 < 0) {
            return null;
        }

        Long ipSegment3 = (encodedIPValue & 0x000000FF);
        if (ipSegment3 > 255 || ipSegment3 < 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(ipSegment0).append(".");
        sb.append(ipSegment1).append(".");
        sb.append(ipSegment2).append(".");
        sb.append(ipSegment3);

        return sb.toString();
    }

    public static boolean isLanIPv4(String ip) {
        if (StringAssist.isBlank(ip)) {
            return false;
        }

        if (!IPV4_PATTERN.matcher(ip).matches()) {
            return false;
        }

        String[] ss = StringAssist.split(ip, '.', false);

        // 通过上面的正则表达式验证，这里不会再报错
        int ip0 = Integer.valueOf(ss[0]);
        int ip1 = Integer.valueOf(ss[1]);

        return ip0 == 10 || (ip0 == 172 && (ip1 >= 16 && ip1 <= 31)) || (ip0 == 192 && ip1 == 168);
    }
}
