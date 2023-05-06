package me.dslztx.assist.util;

import java.util.regex.Pattern;

import org.apache.commons.configuration2.Configuration;

import sun.net.util.IPAddressUtil;

public class IPAssist {

    private static Pattern IPV4_PATTERN = null;

    static {
        loadPatternRegexFromFile();
    }

    private static void loadPatternRegexFromFile() {
        Configuration configuration = ConfigLoadAssist.propConfig("regex.list");

        if (ObjectAssist.isNull(configuration)) {
            throw new RuntimeException("no regex.list file in classpath");
        }

        String ipV4Regex = configuration.getString("ipv4");
        if (StringAssist.isBlank(ipV4Regex)) {
            throw new RuntimeException("no ipv4 regex");
        }
        IPV4_PATTERN = Pattern.compile(ipV4Regex, Pattern.CASE_INSENSITIVE);
    }

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

    protected static byte[] obtainNetAddressByteArrayIPv4(String ip, int mask) {
        if (mask < 0 || mask > 32) {
            return null;
        }

        if (!isIPv4(ip)) {
            return null;
        }

        byte[] bb = IPAddressUtil.textToNumericFormatV4(ip);

        byte c = 0;
        for (int pos = 0; pos < 4; pos++, mask -= 8) {
            if (mask >= 8) {
                c = (byte)0xff;
            } else if (mask <= 0) {
                c = (byte)0x00;
            } else {
                switch (mask) {
                    case 1:
                        c = (byte)0x80;
                        break;
                    case 2:
                        c = (byte)0xc0;
                        break;
                    case 3:
                        c = (byte)0xe0;
                        break;
                    case 4:
                        c = (byte)0xf0;
                        break;
                    case 5:
                        c = (byte)0xf8;
                        break;
                    case 6:
                        c = (byte)0xfc;
                        break;
                    case 7:
                        c = (byte)0xfe;
                        break;
                }
            }

            bb[pos] = (byte)(bb[pos] & c);
        }

        return bb;
    }

    public static String obtainNetAddressIPv4(String ip, int mask) {
        byte[] bb = obtainNetAddressByteArrayIPv4(ip, mask);

        if (ArrayAssist.isEmpty(bb) || bb.length != 4) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append((bb[0] & 0xff));

        for (int pos = 1; pos < 4; pos++) {
            sb.append(".");
            sb.append((bb[pos] & 0xff));
        }

        return sb.toString();
    }
}
