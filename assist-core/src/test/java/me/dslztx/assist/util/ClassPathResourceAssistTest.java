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
            List<InputStream> ins = ClassPathResourceAssist.locateInputStreams("1.xml");
            Assert.assertTrue(ins.size() == 1);

            List<InputStream> ins2 =
                ClassPathResourceAssist.locateInputStreams("org/apache/commons/configuration2/Configuration.class");
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
            Assert.assertTrue(ClassPathResourceAssist.locateFilesNotInJar("2.xml").size() == 1);
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