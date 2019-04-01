package me.dslztx.assist.util;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class FilenameAssistTest {

    @Test
    public void obtainExt() {
        try {
            Assert.assertTrue(FilenameAssist.obtainExt("1.txt").equals("txt"));
            Assert.assertTrue(FilenameAssist.obtainExt("1.png").equals("png"));
            Assert.assertTrue(FilenameAssist.obtainExt("1.ta").equals("ta"));
            Assert.assertTrue(FilenameAssist.obtainExt("1").equals(""));
        } catch (Exception e) {
            fail();
        }
    }
}