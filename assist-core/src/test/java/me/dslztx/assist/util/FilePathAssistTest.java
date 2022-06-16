package me.dslztx.assist.util;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class FilePathAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(FilePathAssistTest.class);

    @Test
    public void obtainParentPath() {
        try {
            Assert.assertTrue(FilePathAssist.obtainParentPath("/home/dsl/Desktop").equals("/home/dsl"));
            Assert.assertTrue(FilePathAssist.obtainParentPath("/////").equals(""));
            Assert.assertTrue(FilePathAssist.obtainParentPath("Desktop////").equals(""));
            Assert.assertTrue(FilePathAssist.obtainParentPath("///Desktop////").equals("/"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void pathElements() {
        try {
            Assert.assertTrue(Arrays.equals(FilePathAssist.pathElements("/home/dsl/Desktop"),
                new String[] {"", "home", "dsl", "Desktop"}));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void concat() {
        try {
            Assert
                .assertTrue(FilePathAssist.concat("/root", "home", "dsl", "Desktop").equals("/root/home/dsl/Desktop"));
        } catch (Exception e) {
            logger.error("");
            Assert.fail();
        }
    }
}