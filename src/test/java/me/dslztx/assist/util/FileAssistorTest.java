package me.dslztx.assist.util;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAssistorTest {

  private static final Logger logger = LoggerFactory.getLogger(FileAssistorTest.class);

  @Test
  public void testObtainSuffix() throws Exception {
    try {
      Assert.assertTrue(FileAssistor.obtainSuffix("a.pig").equals("pig"));
      Assert.assertTrue(FileAssistor.obtainSuffix("a").equals(""));
      Assert.assertTrue(FileAssistor.obtainSuffix("a.").equals(""));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

  @Test
  public void testIsImage() throws Exception {
    try {
      Assert.assertTrue(FileAssistor.isImage("a.jpg"));
      Assert.assertTrue(!FileAssistor.isImage("a.pdf"));
      Assert.assertTrue(!FileAssistor.isImage(""));
      Assert.assertTrue(!FileAssistor.isImage("a."));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

  @Test
  public void testCopyClassPathFileToLocalDir() throws Exception {
    try {
      FileAssistor.copyClassPathFileToLocalDir("mysql.properties", true);
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