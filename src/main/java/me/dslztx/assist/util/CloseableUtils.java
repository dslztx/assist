package me.dslztx.assist.util;

import java.io.Closeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class CloseableUtils {

  private static final Logger logger = LoggerFactory.getLogger(CloseableUtils.class);

  public static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        logger.error("", e);
        throw new RuntimeException(e);
      }
    }
  }

  public static void closeQuietly(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        logger.error("", e);
      }
    }
  }
}
