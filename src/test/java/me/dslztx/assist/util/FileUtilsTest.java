package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(FileUtilsTest.class);

  @Test
  public void testObtainSuffix() throws Exception {
    try {
      Assert.assertTrue(FileUtils.obtainSuffix("a.pig").equals("pig"));
      Assert.assertTrue(FileUtils.obtainSuffix("a").equals(""));
      Assert.assertTrue(FileUtils.obtainSuffix("a.").equals(""));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

  @Test
  public void testIsImage() throws Exception {
    try {
      Assert.assertTrue(FileUtils.isImage("a.jpg"));
      Assert.assertTrue(!FileUtils.isImage("a.pdf"));
      Assert.assertTrue(!FileUtils.isImage(""));
      Assert.assertTrue(!FileUtils.isImage("a."));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

}