package me.dslztx.assist.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration2.Configuration;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.domain.URLParseBean;

/**
 * URL RFC：https://datatracker.ietf.org/doc/html/rfc1738<br/>
 * <p>
 * URI RFC：https://datatracker.ietf.org/doc/html/rfc3986#section-1.1.3<br/>
 */
@Slf4j
public class URLAssist {

    public static final Pattern MAIL_PATTERN =
        Pattern.compile("[A-Za-z0-9][A-Za-z0-9_.-]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}");
    protected static final Set<String> PROTOCOLS = new HashSet<>();

    private static final Map<Character, Character> ILLEGAL_CHAR_MAP = new HashMap<Character, Character>();

    private static final Set<String> SUFFIX_SET = new HashSet<>();

    private static final String HTTP_PREFIX = "http";

    private static final String HTTPS_PREFIX = "https";

    private static final String FTP_PREFIX = "ftp";

    private static final String WWW_PREFIX = "www.";

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

        initSuffixSet();
    }

    private static void initSuffixSet() {
        SUFFIX_SET.add("png");
        SUFFIX_SET.add("jpg");
        SUFFIX_SET.add("gif");
        SUFFIX_SET.add("svg");
        SUFFIX_SET.add("doc");
        SUFFIX_SET.add("docx");
        SUFFIX_SET.add("ppt");
        SUFFIX_SET.add("pptx");
        SUFFIX_SET.add("xls");
        SUFFIX_SET.add("xlsx");
        SUFFIX_SET.add("pdf");
        SUFFIX_SET.add("exe");
        SUFFIX_SET.add("rar");
        SUFFIX_SET.add("zip");
        SUFFIX_SET.add("7z");
        SUFFIX_SET.add("tar");
        SUFFIX_SET.add("gz");
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
        int idx2 = url.indexOf("#");

        if (idx0 == -1) {
            idx0 = Integer.MAX_VALUE;
        }
        if (idx1 == -1) {
            idx1 = Integer.MAX_VALUE;
        }
        if (idx2 == -1) {
            idx2 = Integer.MAX_VALUE;
        }

        int idxv = Math.min(idx0, idx1);
        idxv = Math.min(idx2, idxv);

        if (idxv >= url.length()) {
            return null;
        } else {
            return url.substring(idxv + 1);
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

    /**
     * URL格式见https://datatracker.ietf.org/doc/html/rfc1738<br/>
     * 本方法暂只支持HTTP，HTTPS，FTP协议URL的解析<br/>
     *
     * HTTP，HTTPS，FTP协议URL：<br/>
     * 1、分为5部分：protocol header，username/password，host，port和url-path，本方法处理后只保留host和url-path<br/>
     * 2、protocol
     * header和port完全不区分大小写；username/password和url-path完全区分大小写；host从域名解析角度不区分大小写，从Nginx配置转发策略区分大小写，本方法处理中不区分大小写<br/>
     * <p>
     * <p>
     * 本方法的几个核心要点：<br/>
     * 1、维持 start <= endBeforeUrlPath <= end的性质<br/>
     * 2、存在很多不规范URL情形，统一以host合法性进行兜底判断，又分为3种情况：1）正常url -> host合法；2）部分不规范情形 -> host也是合法的；3）部分不兼容情况 -> host非法<br/>
     * 3、url.substring(endBeforeUrlPath + 1, end + 1)必不会抛出异常的，字段定义得很好<br/>
     *
     * @param url
     * @return
     */
    public static URLParseBean retainHostAndUrlPath(String url) {
        if (StringAssist.isBlank(url)) {
            return null;
        }

        try {
            url = URLDecoder.decode(url);
        } catch (Exception e) {
            log.warn("", e);

            return null;
        }

        String originUrl = url;

        // 路径需要区分大小写，比如路径中带有Base64编码的帐号
        url = url.toLowerCase();

        if (originUrl.length() != url.length()) {
            // 一般情况下，小写转化后，前后长度一致，只有url中带有乱码，前后长度才可能不一致，比如"http://tangy2010.c.wao360.com/%D4%AA%B5%A9%BA%D8%BF%A8%D6%D0%CE%C4%B0%E6.JPG"
            return null;
        }

        int start = 0;
        int end = url.length() - 1;

        // 去掉前导和后续空格
        while (start <= end) {
            if (url.charAt(start) == ' ') {
                start++;
            } else {
                break;
            }
        }

        if (start > end) {
            return null;
        }

        while (start <= end) {
            if (url.charAt(end) == ' ') {
                end--;
            } else {
                break;
            }
        }

        if (start > end) {
            return null;
        }

        if (url.startsWith(HTTPS_PREFIX, start)) {
            // https和http必须https的判断在前面，否则会出错
            start += HTTPS_PREFIX.length();
        } else if (url.startsWith(HTTP_PREFIX, start)) {
            start += HTTP_PREFIX.length();
        } else if (url.startsWith(FTP_PREFIX, start)) {
            start += FTP_PREFIX.length();
        }

        if (start > end) {
            return null;
        }

        char c;
        while (start <= end) {
            c = url.charAt(start);
            if (c == '/' || c == ':' || c == '\\') {
                // 兼容/// \\\ :// http//等情形
                start++;
            } else {
                break;
            }
        }

        if (start > end) {
            return null;
        }

        // 走到这里有两类URL
        // 1）正常的去掉协议头的URL，形如"(http|https|ftp)[:/\]+"都能兼容，最后形如"www.baidu.com"
        // 2）非正常URL，又分为两类：1）非HTTP，HTTPS，FTP协议的URL，协议头去掉失败，比如telnet://www.baidu.com；2）其他非法形式，比如http+//www.baidu.com

        // 先找到与urlPath的分界点，因为urlPath中可以存在@，:等字符，所有后续找寻@，:字符以endBeforeUrlPath为界限
        int endBeforeUrlPath = end;

        int pos0 = url.indexOf("/", start);
        int pos1 = url.indexOf("?", start);
        int pos2 = url.indexOf("#", start);

        if (pos0 != -1 && pos0 - 1 < endBeforeUrlPath) {
            endBeforeUrlPath = pos0 - 1;
        }

        if (pos1 != -1 && pos1 - 1 < endBeforeUrlPath) {
            endBeforeUrlPath = pos1 - 1;
        }

        if (pos2 != -1 && pos2 - 1 < endBeforeUrlPath) {
            endBeforeUrlPath = pos2 - 1;
        }

        int pos = url.indexOf("@", start);

        // 分为3种情况：
        // 1）不存在
        // 2）存在且合法，比如http://testuser:testpass@www.aspxfans.com:8080
        // 3）存在但是非法，比如http://www.aspxftestuser@testpassans.com:8080
        if (pos != -1 && pos <= endBeforeUrlPath) {
            start = pos + 1;
        }

        if (start > endBeforeUrlPath) {
            return null;
        }

        // 去掉可能存在的www
        if (url.startsWith(WWW_PREFIX, start)) {
            start += WWW_PREFIX.length();
        }

        if (start > endBeforeUrlPath) {
            return null;
        }

        int portStart = url.indexOf(":", start);

        // 分为3种情况：
        // 1）不存在
        // 2）存在且合法，比如http://www.aspxfans.com:8080/path
        // 3）存在但是非法，比如http://www.aspx:passfans.com/testpath
        if (portStart != -1 && portStart <= endBeforeUrlPath) {
            String host = url.substring(start, portStart);

            if (!isValidHostSimply(host)) {
                // 很好的一个思想：前面有太多异常情形，穷举较困难，这里统一作个验证
                return null;
            }

            // 注意url-path是基于原url生成的，因为需要保留大小写信息
            return new URLParseBean(host, originUrl.substring(endBeforeUrlPath + 1, end + 1));
        } else {
            String host = url.substring(start, endBeforeUrlPath + 1);
            if (!isValidHostSimply(host)) {
                // 很好的一个思想：前面有太多异常情形，穷举较困难，这里统一作个验证
                return null;
            }

            // 注意url-path是基于原url生成的，因为需要保留大小写信息
            return new URLParseBean(host, originUrl.substring(endBeforeUrlPath + 1, end + 1));
        }
    }

    /**
     * 判断url host最准确的方案是使用正则表达式，但是性能低，这里简单化处理：点号分割部分数量>=2即认为合法
     *
     * @param host
     * @return
     */
    public static boolean isValidHostSimply(String host) {
        if (StringAssist.isBlank(host)) {
            return false;
        }

        String[] urlParts = StringAssist.split(host, '.', true);

        if (ArrayAssist.isEmpty(urlParts) || urlParts.length <= 1) {
            return false;
        }

        return true;
    }

    /**
     * 去掉urlPath中的显式和Base64编码的邮箱帐号
     * 
     * @param urlPath 从retainHostAndUrlPath方法获得结果中得到的urlPath，所以要么为空，要么为以/，#，?开头
     * @return
     */
    public static String removeEmailFromUrlPath(String urlPath) {

        if (StringAssist.isBlank(urlPath)) {
            return null;
        }

        int start = 0;
        int end = urlPath.length() - 1;

        char c;

        StringBuilder sb = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        if (start <= end && urlPath.charAt(start) == '/') {
            sb.append(urlPath.charAt(start));

            start++;
            while (start <= end) {
                c = urlPath.charAt(start);

                if (c == '?' || c == '#') {
                    break;
                }

                if (c == '/') {
                    if (buffer.length() > 0) {
                        if (notContainEmail(buffer)) {
                            sb.append(buffer);
                        }
                        buffer.setLength(0);
                    }

                    sb.append(c);
                } else {
                    buffer.append(c);
                }
                start++;
            }

            if (buffer.length() > 0) {
                if (notContainEmail(buffer)) {
                    sb.append(buffer);
                }
                buffer.setLength(0);
            }
        }

        if (start <= end && urlPath.charAt(start) == '?') {
            sb.append(urlPath.charAt(start));

            boolean findKey = false;
            start++;

            while (start <= end) {
                c = urlPath.charAt(start);
                if (c == '#') {
                    break;
                }

                if (c == '=') {
                    if (findKey) {
                        buffer.append(c);
                    } else {
                        sb.append(c);
                        findKey = true;
                    }
                } else if (c == '&') {
                    if (findKey) {
                        if (buffer.length() > 0) {
                            if (notContainEmail(buffer)) {
                                sb.append(buffer);
                            }

                            buffer.setLength(0);
                        }
                        sb.append(c);
                        findKey = false;
                    } else {
                        sb.append(c);
                    }
                } else {
                    if (findKey) {
                        buffer.append(c);
                    } else {
                        sb.append(c);
                    }
                }

                start++;
            }

            if (buffer.length() > 0) {
                if (notContainEmail(buffer)) {
                    sb.append(buffer);
                }

                buffer.setLength(0);
            }
        }

        if (start <= end && urlPath.charAt(start) == '#') {
            sb.append(urlPath.charAt(start));

            start++;
            while (start <= end) {
                buffer.append(urlPath.charAt(start));

                start++;
            }

            if (buffer.length() > 0) {
                if (notContainEmail(buffer)) {
                    sb.append(buffer);
                }

                buffer.setLength(0);
            }

        }

        // int len = sb.length();
        // if (len == 0) {
        // return sb.toString();
        // } else {
        // // 最后去掉结尾带的/.&,#\字符
        // int cnt = 0;
        // for (int index = len - 1; index >= 0; index--) {
        // c = sb.charAt(index);
        // if (c == '/' || c == '.' || c == '&' || c == ',' || c == '#' || c == '\\') {
        // cnt++;
        // } else {
        // break;
        // }
        // }
        //
        // sb.setLength(len - cnt);
        //
        // return sb.toString();
        // }

        // 上面去掉结尾带的/.&,#\字符，先注释掉不做。否则，会导致出现youtube.com/just@163.com -> youtube.com，易导致误判，youtube.com/相对来说风险小点
        return sb.toString();
    }

    /**
     * 
     * @param buffer 内容长度大于0
     * @return
     */
    private static boolean notContainEmail(StringBuilder buffer) {

        String content = buffer.toString();

        Matcher matcher = MAIL_PATTERN.matcher(content);

        if (content.contains("@") && matcher.find()) {
            return false;
        }

        try {
            String decodeContent = new String(Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8)));
            if (decodeContent.contains("@") && MAIL_PATTERN.matcher(decodeContent).matches()) {
                return false;
            }
        } catch (Exception e) {
            return true;
        }

        return true;
    }

    /**
     * 是否是静态资源路径
     * 
     * @param urlPath 从retainHostAndUrlPath方法获得结果中得到的urlPath，所以要么为空，要么为以/，#，?开头
     * @return
     */
    public static boolean isStaticResource(String urlPath) {
        if (StringAssist.isBlank(urlPath)) {
            return false;
        }

        if (urlPath.charAt(0) != '/') {
            return false;
        }

        int end = urlPath.length() - 1;

        int pos0 = urlPath.indexOf("?");
        int pos1 = urlPath.indexOf("#");
        if (pos0 != -1 && pos0 - 1 < end) {
            end = pos0 - 1;
        }
        if (pos1 != -1 && pos1 - 1 < end) {
            end = pos1 - 1;
        }
        int pos = urlPath.lastIndexOf(".", end);
        if (pos == -1) {
            return false;
        }

        return SUFFIX_SET.contains(urlPath.substring(pos + 1, end + 1).toLowerCase());
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