package me.dslztx.assist.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration2.Configuration;

/**
 * URI RFC：https://datatracker.ietf.org/doc/html/rfc3986#section-1.1.3
 */
public class URLAssist {

    protected static final Set<String> PROTOCOLS = new HashSet<>();

    private static final Map<Character, Character> ILLEGAL_CHAR_MAP = new HashMap<Character, Character>();

    public static Set<String> cnSubdomains = new HashSet<String>();

    private static Pattern ipPattern = null;
    private static Pattern domainPattern = null;
    private static Pattern urlPattern = null;
    private static Pattern urlProtocols = null;

    static {
        loadPatternRegexFromFile();

        cnSubdomains.add("ac");
        cnSubdomains.add("com");
        cnSubdomains.add("edu");
        cnSubdomains.add("gov");
        cnSubdomains.add("net");
        cnSubdomains.add("org");
        cnSubdomains.add("co");

        cnSubdomains.add("TRAVEL".toLowerCase());
        cnSubdomains.add("TEL".toLowerCase());
        cnSubdomains.add("MUSEUM".toLowerCase());
        cnSubdomains.add("MOBI".toLowerCase());
        cnSubdomains.add("MIL".toLowerCase());
        cnSubdomains.add("JOBS".toLowerCase());
        cnSubdomains.add("INT".toLowerCase());
        cnSubdomains.add("INFO".toLowerCase());
        cnSubdomains.add("COOP".toLowerCase());
        cnSubdomains.add("BIZ".toLowerCase());
        cnSubdomains.add("ASIA".toLowerCase());
        cnSubdomains.add("AERO".toLowerCase());

        PROTOCOLS.add("http");
        PROTOCOLS.add("https");
        PROTOCOLS.add("ftp");
        PROTOCOLS.add("gopher");
        PROTOCOLS.add("mailto");
        PROTOCOLS.add("news");
        PROTOCOLS.add("nntp");
        PROTOCOLS.add("telnet");
        PROTOCOLS.add("wais");
        PROTOCOLS.add("file");
        PROTOCOLS.add("prospero");

        ILLEGAL_CHAR_MAP.put('。', '.');
    }

    public static Pattern getIpPattern() {
        return ipPattern;
    }

    public static Pattern getDomainPattern() {
        return domainPattern;
    }

    public static Pattern getUrlPattern() {
        return urlPattern;
    }

    public static Pattern getUrlProtocols() {
        return urlProtocols;
    }

    private static void loadPatternRegexFromFile() {
        Configuration configuration = ConfigLoadAssist.propConfig("regex.list");

        if (ObjectAssist.isNull(configuration)) {
            throw new RuntimeException("no regex.list file in classpath");
        }

        String ipRegex = configuration.getString("ip");
        if (StringAssist.isBlank(ipRegex)) {
            throw new RuntimeException("no ip regex");
        }
        ipPattern = Pattern.compile(ipRegex, Pattern.CASE_INSENSITIVE);

        String domainRegex = configuration.getString("domain");
        if (StringAssist.isBlank(domainRegex)) {
            throw new RuntimeException("no domain regex");
        }
        domainPattern = Pattern.compile(domainRegex, Pattern.CASE_INSENSITIVE);

        String urlRegex = configuration.getString("url");
        if (StringAssist.isBlank(urlRegex)) {
            throw new RuntimeException("no url regex");
        }
        urlPattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);

        String urlProtocolRegex = configuration.getString("urlProtocol");
        if (StringAssist.isBlank(urlProtocolRegex)) {
            throw new RuntimeException("no urlProtocol regex");
        }
        urlProtocols = Pattern.compile(urlProtocolRegex, Pattern.CASE_INSENSITIVE);
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }

    public static boolean isLegalURL(String url) {
        if (StringAssist.isBlank(url)) {
            return false;
        }

        return urlPattern.matcher(url).matches();
    }

    public static String removeProtocol(String url) {
        if (StringAssist.isBlank(url)) {
            return url;
        }

        int index = url.indexOf(":");
        if (index == -1 || index == 0) {
            return url;
        }

        if (!PROTOCOLS.contains(url.substring(0, index).toLowerCase())) {
            return url;
        }

        int start = index + 1;

        while (start < url.length() && url.charAt(start) == '/') {
            start++;
        }

        if (start >= url.length()) {
            return "";
        } else {
            return url.substring(start);
        }
    }

    public static String extractLegalURL(String url) {
        if (StringAssist.isBlank(url)) {
            return null;
        }

        Matcher matcher = urlPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

    /**
     * 提取出明文的疑似跳转URL
     * 
     * @param s 要么是空白，要么是合法的URL
     * @return
     */
    public static Set<String> extractSuspectRedirectURLs(String s) {
        Set<String> urls = new HashSet<>();

        if (StringAssist.isBlank(s)) {
            return urls;
        }

        Matcher matcher = urlProtocols.matcher(s);

        int start = 0;

        int end = -1;
        while (matcher.find()) {
            end = matcher.start();

            if (start == end) {
                continue;
            }

            urls.add(s.substring(start, end));

            start = end;
        }

        urls.add(s.substring(start));

        return urls;
    }

    public static String obtainURLDomain(String url) {
        String domain = getDomain(url);
        domain = domain.trim();
        domain = domain.replaceAll("%20", "");
        String[] ss = domain.split("\\.");
        if (ss.length == 2 && ss[1].length() == 2 && cnSubdomains.contains(ss[0])) {
            return null;
        }
        return domain != null && domainPattern.matcher(domain).matches() ? domain : null;
    }

    public static String removeUserPassword(String url) {

        URLPart urlPart = URLPart.parseOf(url);

        if (ObjectAssist.isNull(urlPart)) {
            return url;
        }

        return urlPart.obtainURLNoUserPassword();
    }

    public static String obtainURLPathPart(String url) {
        if (StringAssist.isBlank(url)) {
            return null;
        }

        url = removeHTTP(url);

        int idx0 = url.indexOf("/");
        int idx1 = url.indexOf("\\");

        if (idx0 == -1 && idx1 == -1) {
            return null;
        } else if (idx0 != -1 && idx1 != -1) {
            int idx2 = Math.min(idx0, idx1);
            return url.substring(idx2 + 1);
        } else if (idx0 != -1) {
            return url.substring(idx0 + 1);
        } else {
            return url.substring(idx1 + 1);
        }
    }

    public static String getDomain(String url) {
        if (url == null) {
            return null;
        }
        // 去掉参数
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }
        // 去掉协议
        url = removeHTTP(url);
        // 去掉path部分;
        int idx = url.indexOf("/");
        if (idx >= 0) {
            url = url.substring(0, idx);
        }
        idx = url.indexOf("\\");
        if (idx >= 0) {
            url = url.substring(0, idx);
        }
        Matcher m = ipPattern.matcher(url);
        if (m.matches()) {
            return m.group(1);
        }
        // 去掉端口
        idx = url.lastIndexOf(":");
        if (idx >= 0) {
            url = url.substring(0, idx);
        }
        String[] ss = url.split("\\.");
        // boolean isCnSubDomain = ss.length > 2 && ss[ss.length - 1].length() == 2 && ss[ss.length - 2].length() < 4;
        boolean isCnSubDomain = false;
        if (!isCnSubDomain) {
            isCnSubDomain = ss.length > 2 && cnSubdomains.contains(ss[ss.length - 2]);
        }
        if (isCnSubDomain) {
            return ss[ss.length - 3] + "." + ss[ss.length - 2] + "." + ss[ss.length - 1];
        } else if (ss.length >= 2) {
            return ss[ss.length - 2] + "." + ss[ss.length - 1];
        } else {
            return url;
        }
    }

    public static String removeHTTP(String url) {
        int start = removeHTTPStart(url);
        if (start > 0)
            return url.substring(start);
        return url;
    }

    public static String illegalCharacterTolerant(String url) {
        if (StringAssist.isBlank(url)) {
            return url;
        }

        StringBuilder sb = new StringBuilder();
        char c;
        for (int index = 0; index < url.length(); index++) {
            c = url.charAt(index);

            if (!ILLEGAL_CHAR_MAP.keySet().contains(c)) {
                sb.append(c);
            } else {
                sb.append(ILLEGAL_CHAR_MAP.get(c));
            }
        }

        return sb.toString();
    }

    public static int removeHTTPStart(String url) {
        String lowUrl = url.toLowerCase();
        int start = 0;
        if (lowUrl.startsWith("http:")) {
            start = "http:".length();
            while (start < lowUrl.length()) {
                char c = lowUrl.charAt(start);
                if (c == '/' || c == '\\')
                    start++;
                else
                    break;
            }
        }
        if (lowUrl.startsWith("https:")) {
            start = "https:".length();
            while (start < lowUrl.length()) {
                char c = lowUrl.charAt(start);
                if (c == '/' || c == '\\')
                    start++;
                else
                    break;
            }
        }
        if (lowUrl.startsWith("ftp:")) {
            start = "ftp:".length();
            while (start < lowUrl.length()) {
                char c = lowUrl.charAt(start);
                if (c == '/' || c == '\\')
                    start++;
                else
                    break;
            }
        }
        return start;
    }
}

class URLPart {
    String protocol;

    String mainPart;

    char separator;

    String urlPath;

    private URLPart() {}

    public static URLPart parseOf(String url) {
        if (StringAssist.isBlank(url)) {
            return null;
        }

        int index = url.indexOf(":");

        int head;

        URLPart urlPart = new URLPart();

        if (index == -1 || !URLAssist.PROTOCOLS.contains(url.substring(0, index).toLowerCase())) {
            urlPart.protocol = null;
            head = 0;
        } else {
            urlPart.protocol = url.substring(0, index);
            head = index + 1;
        }

        int start = head;
        while (start < url.length() && url.charAt(start) == '/') {
            start++;
        }

        if (start == url.length()) {
            return null;
        }

        int slash = url.indexOf("/", start + 1);
        int questionMark = url.indexOf("?", start + 1);
        int numberSign = url.indexOf("#", start + 1);

        if (slash == -1 && questionMark == -1 && numberSign == -1) {
            urlPart.mainPart = url.substring(start);
            urlPart.separator = (char)0;
            urlPart.urlPath = null;
        } else {
            int legalSeparatorPos = Integer.MAX_VALUE;

            if (slash != -1 && slash < legalSeparatorPos) {
                legalSeparatorPos = slash;
            }

            if (questionMark != -1 && questionMark < legalSeparatorPos) {
                legalSeparatorPos = questionMark;
            }

            if (numberSign != -1 && numberSign < legalSeparatorPos) {
                legalSeparatorPos = numberSign;
            }

            urlPart.mainPart = url.substring(start, legalSeparatorPos);
            urlPart.separator = url.charAt(legalSeparatorPos);
            urlPart.urlPath = url.substring(legalSeparatorPos + 1);
        }

        return urlPart;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getMainPart() {
        return mainPart;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String obtainURL() {
        StringBuilder sb = new StringBuilder();

        if (StringAssist.isNotBlank(protocol)) {
            sb.append(protocol);
            sb.append("://");
        }

        sb.append(mainPart);

        if (separator == (char)0) {
            return sb.toString();
        } else {
            sb.append(separator);

            if (StringAssist.isNotBlank(urlPath)) {
                sb.append(urlPath);
            }
        }

        return sb.toString();
    }

    public String obtainURLNoUserPassword() {
        StringBuilder sb = new StringBuilder();

        if (StringAssist.isNotBlank(protocol)) {
            sb.append(protocol);
            sb.append("://");
        }

        int index = mainPart.lastIndexOf("@");

        if (index == -1) {
            sb.append(mainPart);
        } else {
            sb.append(mainPart.substring(index + 1));
        }

        if (separator == (char)0) {
            return sb.toString();
        } else {
            sb.append(separator);
            if (StringAssist.isNotBlank(urlPath)) {
                sb.append(urlPath);
            }
        }

        return sb.toString();
    }

    public char getSeparator() {
        return separator;
    }
}