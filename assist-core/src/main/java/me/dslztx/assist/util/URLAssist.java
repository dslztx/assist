package me.dslztx.assist.util;

import java.io.BufferedReader;
import java.net.IDN;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration2.Configuration;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.domain.ParsedURLTuple;
import me.dslztx.assist.util.domain.URLParseBean;
import me.dslztx.assist.util.metric.TimerAssist;

/**
 * URL RFC：https://datatracker.ietf.org/doc/html/rfc1738<br/>
 * <p>
 * <p>
 * URI RFC：https://datatracker.ietf.org/doc/html/rfc3986#section-1.1.3<br/>
 * <p>
 * <p>
 * https://data.iana.org/TLD/tlds-alpha-by-domain.txt：从此处可以获取最新完备的顶级域名列表<br/>
 * TLD（Top Level Domain）中包括两部分： <br/>
 * 1、常见的英文顶级域名，比如".com",".cn"<br/>
 * 2、国际顶级域名（IDN，Internationalized Domain Name），包括中文顶级域名，文档中以Punycode编码格式存在，比如"XN--FIQS8S -> 中国","XN--J6W193G -> 香港"
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
    private static Pattern urlChinesePattern = null;
    private static Pattern urlProtocols = null;

    private static Set<String> englishTLDSet;
    private static Set<String> chineseTLDSet;

    private static AhoCorasickDoubleArrayTrie<String> chineseTLDAC;
    private static AhoCorasickDoubleArrayTrie<String> englishTLDAC;

    static {
        String pname = "init";

        TimerAssist.timerStart(pname);

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

        TimerAssist.timerStop(pname);

        log.info("URLAssist static init success, time cost {}", TimerAssist.timerValue(pname));
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

    @Deprecated
    public static Pattern getIpPattern() {
        return ipPattern;
    }

    @Deprecated
    public static Pattern getDomainPattern() {
        return domainPattern;
    }

    @Deprecated
    public static Pattern getUrlPattern() {
        return urlPattern;
    }

    @Deprecated
    public static Pattern getUrlProtocols() {
        return urlProtocols;
    }

    private static void loadPatternRegexFromFile() {
        Configuration configuration = ConfigLoadAssist.propConfig("regex.list", "utf-8");

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

        List<String> englishTLDList = new ArrayList<>();
        List<String> chineseTLDList = new ArrayList<>();

        loadEnglishAndChineseTLDList(englishTLDList, chineseTLDList);

        englishTLDSet = new HashSet<>();
        for (String englishTLD : englishTLDList) {
            englishTLDSet.add("." + englishTLD);
        }

        chineseTLDSet = new HashSet<>();
        for (String chineseTLD : chineseTLDList) {
            chineseTLDSet.add("." + chineseTLD);
        }

        buildChineseTLDAC(chineseTLDList);
        buildEnglishTLDAC(englishTLDList);

        String urlChineseRegex = configuration.getString("url_chinese");
        if (StringAssist.isBlank(urlChineseRegex)) {
            throw new RuntimeException("no url_chinese regex");
        }
        StringBuilder sb = new StringBuilder();
        for (String chineseTLD : chineseTLDList) {
            sb.append(chineseTLD);
            sb.append("|");
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        urlChineseRegex = urlChineseRegex.replace("${1}", sb.toString());
        urlChinesePattern = Pattern.compile(urlChineseRegex, Pattern.CASE_INSENSITIVE);
    }

    private static void loadEnglishAndChineseTLDList(List<String> englishTLDList, List<String> chineseTLDList) {

        BufferedReader in = null;
        try {
            in = IOAssist.bufferedReader(ClassPathResourceAssist.locateInputStream("tlds.txt"),
                StandardCharsets.US_ASCII);

            String line;

            TextAssist.Language language;
            String tld;

            int englishTLDCnt = 0;
            int idnTLDCnt = 0;
            int chineseTLDCnt = 0;

            while ((line = in.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                tld = line;
                if (line.startsWith("XN--")) {
                    idnTLDCnt++;

                    tld = IDN.toUnicode(line);
                    language = TextAssist.guessLanguage(tld);

                    if (language == TextAssist.Language.CHINESE) {
                        chineseTLDCnt++;

                        chineseTLDList.add(tld);
                    }
                } else {
                    englishTLDCnt++;

                    englishTLDList.add(tld.toLowerCase());
                }
            }

            log.info("load english and chinese tld list success, tld number stat [english-{}, idn-{}, chinese-{}]",
                englishTLDCnt, idnTLDCnt, chineseTLDCnt);

        } catch (Exception e) {
            log.error("load english and chinese tld list fail", e);

            throw new RuntimeException("", e);
        } finally {
            CloseableAssist.closeQuietly(in);
        }
    }

    private static void buildEnglishTLDAC(List<String> englishTLDList) {
        Map<String, String> kwMap = new HashMap<>();

        String kw;
        for (String englishTLD : englishTLDList) {
            kw = "." + englishTLD;
            kwMap.put(kw, kw);
        }

        englishTLDAC = new AhoCorasickDoubleArrayTrie<String>();
        englishTLDAC.build(kwMap);
    }

    private static void buildChineseTLDAC(List<String> chineseTLDList) {
        Map<String, String> kwMap = new HashMap<>();

        String kw;
        for (String chineseTLD : chineseTLDList) {
            kw = "." + chineseTLD;
            kwMap.put(kw, kw);
        }

        chineseTLDAC = new AhoCorasickDoubleArrayTrie<String>();
        chineseTLDAC.build(kwMap);
    }

    /**
     * 完备情况说明：考虑了"英语.英语顶级域名"和"英语/中文.中文顶级域名"，暂时未考虑"中文.英语顶级域名"
     *
     * @param content
     * @return
     */
    public static List<ParsedURLTuple> parseUrlFromContent(String content) {

        List<ParsedURLTuple> result = new ArrayList<>();

        if (StringAssist.isBlank(content)) {
            return result;
        }

        List<ParsedURLTuple> tmp = null;

        tmp = parseEnglishUrl0(content);
        if (CollectionAssist.isNotEmpty(tmp)) {
            result.addAll(tmp);
        }

        tmp = parseChineseUrl0(content);
        if (CollectionAssist.isNotEmpty(tmp)) {
            result.addAll(tmp);
        }

        return result;
    }

    /**
     * 针对"英文/中文.中文顶级域名"情形：<br/>
     * 1、通过中文顶级域名的AC自动机进行剪枝<br/>
     * 2、对于正则表达式中的中文顶级域名匹配部分，"(\.[\u4e00-\u9fa5\w-]{2,})" vs
     * "(\.(佛山|慈善|集团|在线|点看|八卦|公益|公司|香格里拉|网站|移动|我爱你|联通|时尚|微博|淡马锡|商标|商店|商城|新闻|家電|中文网|中信|中国|中國|娱乐|谷歌|電訊盈科|购物|通販|网店|餐厅|网络|香港|亚马逊|食品|飞利浦|台湾|台灣|手机|澳門|政府|机构|组织机构|健康|招聘|大拿|世界|書籍|网址|天主教|游戏|企业|信息|嘉里大酒店|嘉里|广东|新加坡|政务))"：前者字符类范围过广，后者虽然每次需要匹配59次，但是相对前者性能优，选用后者<br/>
     * 3、中文顶级域名后置验证：1）步骤2中如果选用前者，那么需要后置验证；2）步骤2中如果选用后者，那么无需后置验证<br/>
     * <br/>
     * <br/>
     * 对于形如"(a|b|c|d|e)"正则表达式的匹配过程，详见Pattern内部类Branch的match方法<br/>
     * 
     * @param content
     * @return
     */
    protected static List<ParsedURLTuple> parseChineseUrl0(String content) {

        List<AhoCorasickDoubleArrayTrie.Hit<String>> hits = chineseTLDAC.parseText(content);

        if (CollectionAssist.isEmpty(hits)) {
            return null;
        }

        List<ParsedURLTuple> result = new ArrayList<>();

        Matcher matcher = urlChinesePattern.matcher(content);

        while (matcher.find()) {
            result.add(new ParsedURLTuple(matcher.group(0), matcher.group(1), matcher.group(5), matcher.start(),
                matcher.end()));
        }
        return result;
    }

    /**
     * 针对"英文.英文顶级域名"情形：<br/>
     * 1、通过英文顶级域名的AC自动机进行剪枝：需要注意AC自动机是区分大小写的<br/>
     * 2、对于正则表达式中的英文顶级域名匹配部分，"(\.[\w-]{2,})" vs
     * "(\.(com|cn|tw|ua|...总共1000多个))"：前者字符类范围相对不广，后者每次需要匹配1000多次，相对前者性能差，选用前者<br/>
     * 3、英文顶级域名后置验证：1）步骤2中如果选用前者，那么需要后置验证；2）步骤2中如果选用后者，那么无需后置验证<br/>
     *
     * @param content
     * @return
     */
    protected static List<ParsedURLTuple> parseEnglishUrl0(String content) {
        List<AhoCorasickDoubleArrayTrie.Hit<String>> hits = englishTLDAC.parseText(content.toLowerCase());

        if (CollectionAssist.isEmpty(hits)) {
            return null;
        }

        List<ParsedURLTuple> result = new ArrayList<>();

        Matcher matcher = urlPattern.matcher(content);

        while (matcher.find()) {
            if (englishTLDSet.contains(matcher.group(5).toLowerCase())) {
                result.add(new ParsedURLTuple(matcher.group(0), matcher.group(1), matcher.group(5), matcher.start(),
                    matcher.end()));
            }
        }
        return result;
    }

    public static boolean isLegalURL(String url) {
        if (StringAssist.isBlank(url)) {
            return false;
        }

        return isLegalEnglishUrl0(url) || isLegalChineseUrl0(url);
    }

    protected static boolean isLegalEnglishUrl0(String url) {

        if (CollectionAssist.isEmpty(englishTLDAC.parseText(url.toLowerCase()))) {
            return false;
        }

        Matcher matcher = urlPattern.matcher(url);

        if (!matcher.matches()) {
            return false;
        }

        if (!englishTLDSet.contains(matcher.group(5).toLowerCase())) {
            return false;
        }

        return true;
    }

    protected static boolean isLegalChineseUrl0(String url) {

        if (CollectionAssist.isEmpty(chineseTLDAC.parseText(url))) {
            return false;
        }

        Matcher matcher = urlChinesePattern.matcher(url);

        if (!matcher.matches()) {
            return false;
        }

        return true;
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