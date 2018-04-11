package me.dslztx.assist.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class FileUtils {

  private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

  private static final Set<String> imgSuffixes = new HashSet<String>();

  private static final AtomicLong uniqueID = new AtomicLong(System.currentTimeMillis());

  static {
    imgSuffixes.add("gif");
    imgSuffixes.add("jpg");
    imgSuffixes.add("png");
    imgSuffixes.add("jpeg");
    imgSuffixes.add("bmp");
    imgSuffixes.add("pic");
  }

  public static void copyClassPathFileToLocalDir(String name, boolean overwrite) {
    File dst = new File(name);

    if (!overwrite) {
      if (dst.exists()) {
        return;
      }
    }

    InputStream in = null;
    try {
      in = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
      org.apache.commons.io.FileUtils.copyInputStreamToFile(in, dst);
    } catch (Throwable e) {
      logger.error("", e);
    }
  }

  public static void delFileRecursiveForce(File a) {
    if (a == null || !a.exists()) {
      return;
    }

    if (a.isFile()) {
      a.delete();
    } else {
      if (ArrayUtils.isEmpty(a.listFiles())) {
        a.delete();
      } else {
        for (File file : a.listFiles()) {
          if (file.isFile()) {
            file.delete();
          } else {
            delFileRecursiveForce(file);
          }
        }
        a.delete();
      }
    }
  }

  public static boolean isDirSame(File a, File b) {
    if (!FileUtils.isDir(a) || !FileUtils.isDir(b)) {
      throw new RuntimeException("存在非法目录");
    }

    try {
      List<String> aFileNames = new ArrayList<String>();
      List<String> bFileNames = new ArrayList<String>();
      if (a.listFiles() != null) {
        for (File file : a.listFiles()) {
          aFileNames.add(file.getName());
        }
      }

      if (b.listFiles() != null) {
        for (File file : b.listFiles()) {
          bFileNames.add(file.getName());
        }
      }

      if (!aFileNames.equals(bFileNames)) {
        return false;
      }

      File aSubFile = null;
      File bSubFile = null;
      for (String fileName : aFileNames) {
        aSubFile = new File(a.getCanonicalPath() + File.separator + fileName);
        bSubFile = new File(b.getCanonicalPath() + File.separator + fileName);
        if (aSubFile.isFile() && bSubFile.isFile()) {
          if (!isContentSame(aSubFile, bSubFile)) {
            return false;
          }
        } else if (aSubFile.isDirectory() && bSubFile.isDirectory()) {
          if (!isDirSame(aSubFile, bSubFile)) {
            return false;
          }
        } else {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      logger.error("", e);
      return false;
    }
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

  public static void createUniqueFile(File dir, String suffix) throws IOException {
    if (!isDir(dir)) {
      throw new RuntimeException("dir is not a legal directory");
    }

    long t = System.currentTimeMillis();

    File file = new File(
        (dir.getCanonicalPath() + File.separator + (t++) + (StringUtils.isBlank(suffix) ? ""
            : suffix)));
    while (file.exists()) {
      file = new File(
          dir.getCanonicalPath() + File.separator + (t++) + (StringUtils.isBlank(suffix) ? ""
              : suffix));
    }

    try {
      file.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException("create file [" + file.getCanonicalPath() + "] fail");
    }
  }

  public static long obtainApplicationUniqueID() {
    return uniqueID.getAndIncrement();
  }
}
