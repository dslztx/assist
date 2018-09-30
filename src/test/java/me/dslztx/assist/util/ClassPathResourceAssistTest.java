package me.dslztx.assist.util;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathResourceAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassPathResourceAssist.class);

    @Test
    public void locateInputStreams() {
        try {
            List<InputStream> ins = ClassPathResourceAssist.locateInputStreams("logback.xml");
            Assert.assertTrue(ins.size() == 2);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void locateInputStream() {
        try {
            Assert.assertNotNull(
                ClassPathResourceAssist.locateInputStream("me/dslztx/assist/util/ClassPathResourceAssist.class"));
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}