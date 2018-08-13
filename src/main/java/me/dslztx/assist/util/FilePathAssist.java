package me.dslztx.assist.util;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilePathAssist {

  private static final Logger logger = LoggerFactory.getLogger(FilePathAssist.class);

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
}
