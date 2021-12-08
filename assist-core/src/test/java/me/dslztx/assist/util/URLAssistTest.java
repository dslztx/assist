package me.dslztx.assist.util;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(URLAssistTest.class);

    @Test
    public void extractLegalURLTest() {
        try {

            String url0 = "https://www.baidu.com/,.@?=%";
            Assert.assertTrue("https://www.baidu.com/,.@?=%".equals(URLAssist.extractLegalURL(url0)));

            String url1 = "mmmmhttps://www.baidu.com/cc[]]";
            Assert.assertTrue("https://www.baidu.com/cc".equals(URLAssist.extractLegalURL(url1)));

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void extractLegalURLsWithProtocol() {
        try {
            String s =
                "http://eqeqeqeq.immutable.expert/ad?bb=c2FsZUBhdmljLXRlY2guY29tLmNu#https://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7D";

            Set<String> set = URLAssist.extractSuspectRedirectURLs(s);

            Assert.assertTrue(set.size() == 2);

            Assert.assertTrue(set.contains("http://eqeqeqeq.immutable.expert/ad?bb=c2FsZUBhdmljLXRlY2guY29tLmNu#"));

            Assert.assertTrue(set.contains(
                "https://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7D"));

            String s2 =
                "http://eqeqeqeq.immutable.expert/ad?bb=c2FsZUBhdmljLXRlY2guY29tLmNu#https://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7Dhttps://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7D";

            Set<String> set2 = URLAssist.extractSuspectRedirectURLs(s2);

            Assert.assertTrue(set2.size() == 2);

            Assert.assertTrue(set2.contains("http://eqeqeqeq.immutable.expert/ad?bb=c2FsZUBhdmljLXRlY2guY29tLmNu#"));

            Assert.assertTrue(set2.contains(
                "https://hwmail.qiye.163.com/js6/main.jsp?sid=10YAf6ECi8u4S1q2ZCstFCp4O3UMjEuD&hl=zh_CN#module=read.ReadModule%7C%7B%22area%22%3A%22normal%22%2C%22isThread%22%3Afalse%2C%22viewType%22%3A%22%22%2C%22id%22%3A%22AOAAwwBEIAd9blGM94AJPKok%22%2C%22fid%22%3A1%7D"));

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void removeUserPasswordTest() {
        try {
            String url0 = "http://www.baidu.com/";
            Assert.assertTrue("http://www.baidu.com/".equals(URLAssist.removeUserPassword(url0)));

            String url1 = "http://user:password@www.baidu.com?";
            Assert.assertTrue("http://www.baidu.com?".equals(URLAssist.removeUserPassword(url1)));

            String url2 = "user:password@www.baidu.com";
            Assert.assertTrue("www.baidu.com".equals(URLAssist.removeUserPassword(url2)));

            String url3 = "http://user:password@";
            Assert.assertTrue("http://".equals(URLAssist.removeUserPassword(url3)));

            String url4 = "httPS://8y5ny.csb.app/#test@baidu.com";
            Assert.assertTrue("httPS://8y5ny.csb.app/#test@baidu.com".equals(URLAssist.removeUserPassword(url4)));

            String url5 = "htTps://user:pass@8y5ny.csb.app/#test@baidu.com";
            Assert.assertTrue("htTps://8y5ny.csb.app/#test@baidu.com".equals(URLAssist.removeUserPassword(url5)));

            String url6 = "http://-----@@evgenia.ru/0001/00126";
            Assert.assertTrue("http://evgenia.ru/0001/00126".equals(URLAssist.removeUserPassword(url6)));

            String url7 = "https://goldmine.squirly.info?tom=Mandy9988@163.com";
            Assert.assertTrue(
                "https://goldmine.squirly.info?tom=Mandy9988@163.com".equals(URLAssist.removeUserPassword(url7)));

            String url8 = "https://@@buttercup-lily-degree.glitch.me#bohai66@126.com";
            Assert.assertTrue(
                "https://buttercup-lily-degree.glitch.me#bohai66@126.com".equals(URLAssist.removeUserPassword(url8)));

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void illegalCharacterTolerantTest() {
        try {
            String url0 = "http://www。baidu.com";

            Assert.assertTrue("http://www.baidu.com".equals(URLAssist.illegalCharacterTolerant(url0)));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}