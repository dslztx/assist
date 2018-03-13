package me.dslztx.assist.util;

import java.io.File;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipCompressUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(ZipCompressUtilsTest.class);

  private static final String PATH_PREFIX = "src/test/resources";

  @Test
  public void compressAndDecompress() throws Exception {
    try {
      File original = new File(PATH_PREFIX + File.separator + "a");
      File compressed = new File(PATH_PREFIX + File.separator + "test" + File.separator + "a.zip");
      compressed.getParentFile().mkdirs();

      ZipCompressUtils.compress(original, compressed);

      ZipCompressUtils.decompress(compressed, compressed.getParentFile());

      Assert.assertTrue(FileUtils.isDirSame(original,
          new File(compressed.getParentFile().getCanonicalPath() + File.separator + "a")));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    } finally {
      FileUtils.delFileRecursiveForce(new File(PATH_PREFIX + File.separator + "test"));
    }
  }

  @Test
  public void compressAndDecompress2() throws Exception {
    try {
      File original = new File(PATH_PREFIX + File.separator + "1.xml");
      File compressed = new File(
          PATH_PREFIX + File.separator + "test" + File.separator + "1.xml.zip");
      compressed.getParentFile().mkdirs();

      SevenZCompressUtils.compress(original, compressed);

      SevenZCompressUtils.decompress(compressed, compressed.getParentFile());

      Assert.assertTrue(FileUtils.isContentSame(original,
          new File(compressed.getParentFile().getCanonicalPath() + File.separator + "1.xml")));
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    } finally {
      FileUtils.delFileRecursiveForce(new File(PATH_PREFIX + File.separator + "test"));
    }
  }
}