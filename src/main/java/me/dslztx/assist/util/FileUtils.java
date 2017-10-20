package me.dslztx.assist.util;

import java.io.File;

/**
 * @author dslztx
 */
public class FileUtils {

  public static boolean isDir(File file) {
    return file != null && file.isDirectory();
  }

  public static boolean isRegular(File file) {
    return file != null && file.isFile();
  }
}
