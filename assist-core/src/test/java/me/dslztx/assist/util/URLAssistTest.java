package me.dslztx.assist.util;

import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.domain.ParsedURLTuple;
import me.dslztx.assist.util.domain.URLParseBean;

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
    public void isLegalTest() {
        try {
            String url =
                "https://nt.embluemail.com/p/cl?data=Zw8yiTPC%2BcebJKMtk%2F%2B6rku2tsJmDyxVoHOef6Z5QTA%2FpEYeNg7ExE4%3D!-!6b5bj!-!https://power.kellyvspace.com/d2VibWFzdGVyQHNpdGFiYy5jb20=#%3CFONT%20id=830523030%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1252113650%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1580351169%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1164394953%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=2068099828%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=340120265%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1213793263%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1866781615%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1258497138%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1366491710%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1919142205%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1772545589%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=78289168%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1590280532%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=407690411%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1621688155%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1708961486%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=904537611%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1435282489%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1717388577%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1418470316%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1854531168%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=42452455%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=2113043847%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=783996330%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E#";

            Assert.assertTrue(URLAssist.isLegalURL(url));

            String url2 = "http://你好啊";
            Assert.assertFalse(URLAssist.isLegalURL(url2));

            String url3 = "www.baidu.com";
            Assert.assertTrue(URLAssist.isLegalURL(url3));

            String url4 = "helloworld";
            Assert.assertFalse(URLAssist.isLegalURL(url4));

            String url5 = "163.com";
            Assert.assertTrue(URLAssist.isLegalURL(url5));

            Assert.assertTrue(URLAssist.isLegalURL("蜜雪冰城.网址"));
            Assert.assertTrue(URLAssist.isLegalURL("www.163.com"));
            Assert.assertFalse(URLAssist.isLegalURL("www.163.comm"));
            Assert.assertFalse(URLAssist.isLegalURL("蜜雪冰城.网址址"));
            Assert.assertTrue(URLAssist.isLegalURL("www.163.Com"));
            Assert.assertTrue(URLAssist.isLegalURL("www.163.CoM"));
            Assert.assertTrue(URLAssist.isLegalURL("www.163.COM"));

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

            String s3 =
                "https://nt.embluemail.com/p/cl?data=Zw8yiTPC%2BcebJKMtk%2F%2B6rku2tsJmDyxVoHOef6Z5QTA%2FpEYeNg7ExE4%3D!-!6b5bj!-!https://power.kellyvspace.com/d2VibWFzdGVyQHNpdGFiYy5jb20=#%3CFONT%20id=830523030%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1252113650%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1580351169%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1164394953%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=2068099828%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=340120265%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1213793263%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1866781615%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1258497138%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1366491710%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1919142205%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1772545589%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=78289168%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1590280532%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=407690411%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1621688155%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1708961486%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=904537611%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1435282489%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1717388577%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1418470316%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1854531168%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=42452455%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=2113043847%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=783996330%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E#";

            Set<String> set3 = URLAssist.extractSuspectRedirectURLs(s3);

            Assert.assertTrue(set3.size() == 2);

            Assert.assertTrue(set3.contains(
                "https://power.kellyvspace.com/d2VibWFzdGVyQHNpdGFiYy5jb20=#%3CFONT%20id=830523030%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1252113650%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1580351169%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1164394953%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=2068099828%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=340120265%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1213793263%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1866781615%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1258497138%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1366491710%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1919142205%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1772545589%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=78289168%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1590280532%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=407690411%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1621688155%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1708961486%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=904537611%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1435282489%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1717388577%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1418470316%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=1854531168%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=42452455%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=2113043847%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E%3CFONT%20id=783996330%3E%E2%80%8F%3CSTRONG%3Ean%3C/STRONG%3E%E2%80%8E%3C/FONT%3E#"));

            Assert.assertTrue(set3.contains(
                "https://nt.embluemail.com/p/cl?data=Zw8yiTPC%2BcebJKMtk%2F%2B6rku2tsJmDyxVoHOef6Z5QTA%2FpEYeNg7ExE4%3D!-!6b5bj!-!"));
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

    @Test
    public void removeProtocolTest() {
        try {
            Assert.assertTrue("www.baidu.com".equals(URLAssist.removeProtocol("http://www.baidu.com")));

            Assert.assertTrue("www.baidu.com".equals(URLAssist.removeProtocol("http://////www.baidu.com")));

            Assert.assertTrue("httpx://www.baidu.com".equals(URLAssist.removeProtocol("httpx://www.baidu.com")));

            Assert.assertTrue("://www.baidu.com".equals(URLAssist.removeProtocol("://www.baidu.com")));

            Assert.assertTrue("".equals(URLAssist.removeProtocol("http:////")));

            Assert.assertTrue("www.baidu.com".equals(URLAssist.removeProtocol("https://////www.baidu.com")));
            Assert.assertTrue("www.baidu.com".equals(URLAssist.removeProtocol("ftp://////www.baidu.com")));

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void bigContentExtractURLTimeCostTest() {
        try {

            String content =
                IOUtils.toString(ClassPathResourceAssist.locateInputStream("big_content_for_url.text"), "UTF-8");

            long start = System.currentTimeMillis();

            URLAssist.getUrlPattern().matcher(content).find();

            long end = System.currentTimeMillis();

            Assert.assertTrue((end - start) < 1000);

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void obtainURLPathPartTest() {
        try {
            Assert.assertTrue(URLAssist.obtainURLPathPart("http://www.baidu.com/").equals(""));
            Assert.assertTrue(URLAssist.obtainURLPathPart("http://////www.baidu.com/2").equals("2"));
            Assert.assertTrue(URLAssist.obtainURLPathPart("http:\\\\\\www.baidu.com/h").equals("h"));
            Assert.assertTrue(URLAssist.obtainURLPathPart("http:\\\\\\www.baidu.com/h\\world").equals("h\\world"));

            Assert.assertTrue(
                URLAssist.obtainURLPathPart("http://cpc4ksa.com/ctwsiayhm/?dremis=sarah@henfenpaper" + ".com ")
                    .equals("ctwsiayhm/?dremis=sarah@henfenpaper.com "));

            Assert.assertTrue(URLAssist.obtainURLPathPart("http://qxzmwuehsj.oxurxhydru.ink#sten@unitedubao.com")
                .equals("sten@unitedubao.com"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void retainHostAndUrlPath0() {
        try {
            Assert.assertTrue(URLAssist
                .retainHostAndUrlPath("http://testuser:testpass@www.aspxfans.com:8080/news/index"
                    + ".asp?boardID=5&ID=24618&page=1#refpart")
                .equals(new URLParseBean("aspxfans.com", "/news/index.asp?boardID=5&ID=24618&page=1#refpart")));

            Assert.assertTrue(URLAssist
                .retainHostAndUrlPath(
                    "testuser:testpass@www.aspxfans.com:8080/news/index" + ".asp?boardID=5&ID=24618&page=1#refpart")
                .equals(new URLParseBean("aspxfans.com", "/news/index.asp?boardID=5&ID=24618&page=1#refpart")));

            Assert.assertTrue(URLAssist
                .retainHostAndUrlPath("www.aspxfans.com:8080/news/index" + ".asp?boardid=5&id=24618&page=1#refpart")
                .equals(new URLParseBean("aspxfans.com", "/news/index.asp?boardid=5&id=24618&page=1#refpart")));

            Assert.assertTrue(
                URLAssist.retainHostAndUrlPath("www.aspxfans.com/news/index" + ".asp?boardid=5&id=24618&page=1#refpart")
                    .equals(new URLParseBean("aspxfans.com", "/news/index.asp?boardid=5&id=24618&page=1#refpart")));

            Assert.assertTrue(
                URLAssist.retainHostAndUrlPath("www.aspxfans.com").equals(new URLParseBean("aspxfans.com", "")));

            Assert.assertTrue(URLAssist.retainHostAndUrlPath("http:www.baidu.com/a?b#c")
                .equals(new URLParseBean("baidu.com", "/a?b#c")));
            Assert.assertTrue(URLAssist.retainHostAndUrlPath("http/www.baidu.com/a?b#c")
                .equals(new URLParseBean("baidu.com", "/a?b#c")));
            Assert.assertTrue(URLAssist.retainHostAndUrlPath("http//www.baidu.com/a?b#c")
                .equals(new URLParseBean("baidu.com", "/a?b#c")));
            Assert.assertTrue(URLAssist.retainHostAndUrlPath("http///www.baidu.com/a?b#c")
                .equals(new URLParseBean("baidu.com", "/a?b#c")));

            Assert.assertTrue(
                URLAssist.retainHostAndUrlPath("https://www.baidu.com/a").equals(new URLParseBean("baidu.com", "/a")));
            Assert.assertTrue(
                URLAssist.retainHostAndUrlPath("ftp://www.baidu.com?b").equals(new URLParseBean("baidu.com", "?b")));
            Assert.assertTrue(
                URLAssist.retainHostAndUrlPath("http://www.baidu.com#c").equals(new URLParseBean("baidu.com", "#c")));
            Assert.assertTrue(URLAssist.retainHostAndUrlPath("http://www.baidu.com/a?b#c")
                .equals(new URLParseBean("baidu.com", "/a?b#c")));

            Assert.assertTrue(URLAssist.retainHostAndUrlPath("http://www.baidu.com/a?b=just_test@163.com#c")
                .equals(new URLParseBean("baidu.com", "/a?b=just_test@163.com#c")));
            Assert.assertTrue(URLAssist.retainHostAndUrlPath("http://www.baidu.com/a?b=d:e#c")
                .equals(new URLParseBean("baidu.com", "/a?b=d:e#c")));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void retainHostAndUrlPath1() {
        try {
            Assert.assertNull(URLAssist.retainHostAndUrlPath("telnet://www.baidu.com"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void retainHostAndUrlPath2() {
        try {
            Assert.assertNull(URLAssist.retainHostAndUrlPath("http://www.baidu.com@refpart"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }

    }

    @Test
    public void retainHostAndUrlPath3() {
        try {
            Assert.assertNull(URLAssist.retainHostAndUrlPath("http://www:8080baidu.com/refpartillegal"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }

    }

    @Test
    public void retainHostAndUrlPath4() {
        try {
            Assert.assertNull(URLAssist.retainHostAndUrlPath("http:///..cn"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }

    }

    @Test
    public void retainHostAndUrlPath5() {
        try {
            String url = "http://tangy2010.c.wao360.com/%D4%AA%B5%A9%BA%D8%BF%A8%D6%D0%CE%C4%B0%E6.JPG";

            Assert.assertNull(URLAssist.retainHostAndUrlPath(url));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void removeEmailFromUrlPathTest() {
        try {
            String s = "/urlpath/test@163.com/dGVzdEAxNjMuY29t?a=dGVzdEAxNjMuY29t&b=test@163.com#dGVzdEAxNjMuY29t";
            Assert.assertTrue(URLAssist.removeEmailFromUrlPath(s).equals("/urlpath//?a=&b=#"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isStaticResourceTest() {
        try {
            Assert.assertTrue(URLAssist.isStaticResource("/a/a.png?a=b#c"));
            Assert.assertTrue(URLAssist.isStaticResource("/a.zip?a=b#c"));
            Assert.assertTrue(URLAssist.isStaticResource("/a.rar"));

            Assert.assertFalse(URLAssist.isStaticResource(""));
            Assert.assertFalse(URLAssist.isStaticResource(null));
            Assert.assertFalse(URLAssist.isStaticResource("/.?a=b#c"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void parseUrlFromContentTest() {
        try {
            String content = "网络办公.中国夜風凜凜 獨回望舊事前塵\n" + "是以往的我充滿怒憤http://www.baidu.COM\n" + "誣告與指責 積壓着滿肚氣不憤\n"
                + "對謠言反應甚為着緊\n" + "受了教訓 得了書經的指引\n" + "現已看得透不再自困\n" + "但覺有分數 不再像以往那般笨\n" + "抹淚痕輕快笑着行www.163.com\n"
                + "冥冥中都早註定你富或貧\n" + "是錯永不對真永是真\n" + "任你怎説安守我本份yeah.Cn\n" + "始終相信沉默是金\n" + "是非有公理 慎言莫冒犯別人\n"
                + "遇上冷風雨休太認真\n" + "自信滿心裏 休理會諷刺與質問\n" + "笑罵由人 灑脱地做人\n" + "受了教訓 得了書經的指引\n" + "現已看得透不再自困\n"
                + "但覺有分數 不再像以往那般笨\n" + "抹淚痕輕快笑着行\n" + "冥冥中都早註定你富或貧google.com:8080/china\n" + "是錯永不對真永是真：https://台湾"
                + ".電訊盈科RFC\n" + "任你怎説安守我本份\n" + "始終相信沉默是金。蜜雪冰城.网址。优衣库中国.网址。屈臣氏.网址";

            List<ParsedURLTuple> result = URLAssist.parseUrlFromContent(content);

            Assert.assertTrue("http://www.baidu.COM".equals(result.get(0).getUrl()));
            Assert.assertTrue("www.163.com".equals(result.get(1).getUrl()));
            Assert.assertTrue("yeah.Cn".equals(result.get(2).getUrl()));
            Assert.assertTrue("google.com:8080/china".equals(result.get(3).getUrl()));
            Assert.assertTrue("网络办公.中国".equals(result.get(4).getUrl()));
            Assert.assertTrue("https://台湾.電訊盈科".equals(result.get(5).getUrl()));
            Assert.assertTrue("蜜雪冰城.网址".equals(result.get(6).getUrl()));
            Assert.assertTrue("优衣库中国.网址".equals(result.get(7).getUrl()));
            Assert.assertTrue("屈臣氏.网址".equals(result.get(8).getUrl()));

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void parseUrlFromContentTest1() {
        try {
            Assert.assertTrue(URLAssist.parseUrlFromContent("http://www.baidu.comm").size() == 0);
            Assert.assertTrue(URLAssist.parseUrlFromContent("网络办公.中国国").get(0).getUrl().equals("网络办公.中国"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void parseUrlFromContentTest2() {
        try {
            Assert.assertTrue(URLAssist.parseUrlFromContent("http://www.baidu.COM，这是一段测试文本").get(0).getUrl()
                .equals("http://www.baidu.COM"));

            Assert.assertTrue(URLAssist.parseUrlFromContent("http://www.baidu.COMM，这是一段测试文本").size() == 0);

            // baidu竟然也是个顶级域名
            Assert.assertTrue(URLAssist.parseUrlFromContent("http://www.baidu.C，这是一段测试文本").get(0).getUrl()
                .equals("http://www.baidu"));

            Assert.assertTrue(URLAssist.parseUrlFromContent("http://www.baidu.com.cn，这是一段测试文本").get(0).getUrl()
                .equals("http://www.baidu.com.cn"));

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

}