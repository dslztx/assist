package me.dslztx.assist.util.filetype;

import static org.junit.Assert.fail;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ClassPathResourceAssist;

public class JPEGMatcherTest {

    private static final Logger logger = LoggerFactory.getLogger(JPEGMatcherTest.class);

    @Test
    public void match() {
        try {
            JPEGMatcher jpegMatcher = new JPEGMatcher();
            byte[] data = IOUtils.toByteArray(ClassPathResourceAssist.locateInputStream("images/1.jpg"));
            Assert.assertTrue(jpegMatcher.match(data));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}