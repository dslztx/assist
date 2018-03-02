package me.dslztx.assist.util;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class FileUtils {

  private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

  private static final Set<String> imgSuffixes = new HashSet<String>();

  static {
    imgSuffixes.add("gif");
    imgSuffixes.add("jpg");
    imgSuffixes.add("png");
    imgSuffixes.add("jpeg");
    imgSuffixes.add("bmp");
    imgSuffixes.add("pic");
  }

  public static boolean isContentSame(File a, File b) {
    if (a == null || b == null) {
      return false;
    }

    BufferedInputStream ain = null;
    BufferedInputStream bin = null;
    try {
      ain = IOUtils.bufferedInputStream(a);
      bin = IOUtils.bufferedInputStream(b);

      int av = 0;
      int bv = 0;
      while (true) {
        av = ain.read();
        bv = bin.read();
        if (av == -1 || bv == -1) {
          break;
        }

        if (av != bv) {
          return false;
        }
      }

      if (av == -1 && bv == -1) {
        return true;
      } else {
        return false;
      }
    } catch (IOException e) {
      logger.error("", e);
      return false;
    } finally {
      CloseableUtils.close(ain);
      CloseableUtils.close(bin);
    }
  }

  public static boolean isDir(File file) {
    return file != null && file.isDirectory();
  }

  public static boolean isRegular(File file) {
    return file != null && file.isFile();
  }

  public static boolean isImage(File file) {
    if (file == null) {
      return false;
    }

    return isImage(file.getName());
  }

  public static boolean isImage(String name) {
    if (StringUtils.isBlank(name)) {
      return false;
    }

    String suffix = obtainSuffix(name);
    if (StringUtils.isBlank(suffix)) {
      return false;
    }

    return imgSuffixes.contains(suffix.toLowerCase());
  }

  public static String obtainSuffix(String name) {
    if (StringUtils.isBlank(name)) {
      return "";
    }
    int pos = name.lastIndexOf(".");
    if (pos == -1 || pos == name.length() - 1) {
      return "";
    }

    return name.substring(pos + 1);
  }

  public static void createUniqueFile(File dir, String suffix) {
    if (!isDir(dir)) {
      throw new RuntimeException("dir is not a legal directory");
    }

    long t = System.currentTimeMillis();

    File file = new File(
        (dir.getAbsolutePath() + File.separator + (t++) + (StringUtils.isBlank(suffix) ? ""
            : suffix)));
    while (file.exists()) {
      file = new File(
          dir.getAbsolutePath() + File.separator + (t++) + (StringUtils.isBlank(suffix) ? ""
              : suffix));
    }

    try {
      file.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException("create file [" + file.getAbsolutePath() + "] fail");
    }
  }

}
