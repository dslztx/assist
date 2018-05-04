package me.dslztx.assist.util;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(FileAssistTest.class);

  @Test
  public void testObtainSuffix() throws Exception {
    try {
      Assert.assertTrue(FileAssist.obtainSuffix("a.pig").equals("pig"));
      Assert.assertTrue(FileAssist.obtainSuffix("a").equals(""));
      Assert.assertTrue(FileAssist.obtainSuffix("a.").equals(""));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

  @Test
  public void testIsImage() throws Exception {
    try {
      Assert.assertTrue(FileAssist.isImage("a.jpg"));
      Assert.assertTrue(!FileAssist.isImage("a.pdf"));
      Assert.assertTrue(!FileAssist.isImage(""));
      Assert.assertTrue(!FileAssist.isImage("a."));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

  @Test
  public void testCopyClassPathFileToLocalDir() throws Exception {
    try {
      FileAssist.copyClassPathFileToLocalDir("mysql.properties", true);
      Assert.assertTrue(new File("mysql.properties").exists());
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    } finally {
      File dst = new File("mysql.properties");
      if (dst.exists()) {
        dst.delete();
      }
    }
  }

}