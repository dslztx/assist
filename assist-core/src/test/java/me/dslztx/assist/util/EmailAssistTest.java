package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(EmailAssistTest.class);

    @Test
    public void obtainDomain() {
        try {
            String account = "hah@gmail.com";
            Assert.assertTrue("gmail.com".equals(EmailAssist.obtainDomain(account)));
            Assert.assertNull(EmailAssist.obtainDomain(""));
            Assert.assertNull(EmailAssist.obtainDomain("hah"));
            Assert.assertNull(EmailAssist.obtainDomain("hah@"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}