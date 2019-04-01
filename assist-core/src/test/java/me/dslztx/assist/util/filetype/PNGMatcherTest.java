package me.dslztx.assist.util.filetype;

import static org.junit.Assert.fail;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ClassPathResourceAssist;

public class PNGMatcherTest {

    private static final Logger logger = LoggerFactory.getLogger(PNGMatcherTest.class);

    @Test
    public void match() {
        try {
            PNGMatcher pngMatcher = new PNGMatcher();
            byte[] data = IOUtils.toByteArray(ClassPathResourceAssist.locateInputStream("images/1.png"));
            Assert.assertTrue(pngMatcher.match(data));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}