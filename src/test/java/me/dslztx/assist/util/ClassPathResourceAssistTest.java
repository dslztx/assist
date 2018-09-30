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

            List<InputStream> ins2 = ClassPathResourceAssist.locateInputStreams("com/alibaba/druid/Constants.class");
            Assert.assertTrue(ins2.size() == 1);
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

    @Test
    public void locateFilesNotInJar() {
        try {
            Assert.assertTrue(ClassPathResourceAssist.locateFilesNotInJar("logback.xml").size() == 2);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void locateFileNotInJar() {
        try {
            Assert.assertNotNull(ClassPathResourceAssist.locateFileNotInJar("holiday_arrangement"));

            Assert.assertNull(ClassPathResourceAssist.locateFileNotInJar("com/alibaba/druid/Constants.class"));
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}