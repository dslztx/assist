package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(URLAssistTest.class);

    @Test
    public void removeUsernamePasswordTest() {
        try {
            String url0 = "http://www.baidu.com";
            Assert.assertTrue("http://www.baidu.com".equals(URLAssist.removeUserPassword(url0)));

            String url1 = "http://user:password@www.baidu.com";
            Assert.assertTrue("http://www.baidu.com".equals(URLAssist.removeUserPassword(url1)));

            String url2 = "user:password@www.baidu.com";
            Assert.assertTrue("www.baidu.com".equals(URLAssist.removeUserPassword(url2)));

            String url3 = "http://user:password@";
            Assert.assertTrue("http://".equals(URLAssist.removeUserPassword(url3)));

            String url4 = "https://8y5ny.csb.app/#test@baidu.com";
            Assert.assertTrue("https://8y5ny.csb.app/#test@baidu.com".equals(URLAssist.removeUserPassword(url4)));

            String url5 = "https://user:pass@8y5ny.csb.app/#test@baidu.com";
            Assert.assertTrue("https://8y5ny.csb.app/#test@baidu.com".equals(URLAssist.removeUserPassword(url5)));
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}