package me.dslztx.assist.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dslztx
 */
public class FileUtils {

  private static final Set<String> imgSuffixes = new HashSet<String>();

  static {
    imgSuffixes.add("gif");
    imgSuffixes.add("jpg");
    imgSuffixes.add("png");
    imgSuffixes.add("jpeg");
    imgSuffixes.add("bmp");
    imgSuffixes.add("pic");
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
}
