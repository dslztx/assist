package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(URLAssistTest.class);

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