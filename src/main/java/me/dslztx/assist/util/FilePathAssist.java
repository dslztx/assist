package me.dslztx.assist.util;

import java.io.File;

public class FilePathAssist {

  public static String obtainParentPath(String path) {
    if (StringAssist.isBlank(path)) {
      return "";
    }

    int index = path.length() - 1;
    while (index >= 0 && path.charAt(index) == File.separatorChar) {
      index--;
    }

    if (index < 0) {
      return "";
    }

    index = path.lastIndexOf(File.separatorChar, index);

    if (index == -1) {
      return "";
    }

    while (index >= 0 && path.charAt(index) == File.separatorChar) {
      index--;
    }

    if (index < 0) {
      return "" + File.separatorChar;
    } else {
      return path.substring(0, index + 1);
    }
  }

  public static String[] pathElements(String path) {
    if (StringAssist.isBlank(path)) {
      return new String[0];
    }

    return path.split(File.separator);
  }

  public static String concat(String rootPath, String... elements) {
    StringBuilder sb = new StringBuilder();
    sb.append(rootPath);

    for (String element : elements) {
      sb.append(File.separatorChar);
      sb.append(element);
    }

    return sb.toString();
  }
}
