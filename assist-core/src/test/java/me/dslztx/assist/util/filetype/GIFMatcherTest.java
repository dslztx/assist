package me.dslztx.assist.util.filetype;

import static org.junit.Assert.fail;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ClassPathResourceAssist;

public class GIFMatcherTest {

    private static final Logger logger = LoggerFactory.getLogger(GIFMatcherTest.class);

    @Test
    public void match() {
        try {
            GIFMatcher giFMatcher = new GIFMatcher();
            byte[] data = IOUtils.toByteArray(ClassPathResourceAssist.locateInputStream("images/1.gif"));

            Assert.assertTrue(giFMatcher.match(data));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void match2() {
        try {
            GIFMatcher gifMatcher = Mockito.mock(GIFMatcher.class);

            Mockito.when(gifMatcher.match(Mockito.any())).thenReturn(true);
            Assert.assertTrue(gifMatcher.match(null));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}