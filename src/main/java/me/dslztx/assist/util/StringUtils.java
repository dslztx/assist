package me.dslztx.assist.util;

/**
 * @author dslztx
 */
public class StringUtils {

  /**
   * judge whether legal hexadecimal number string
   */
  public static boolean isHexStr(String s) {
    if (isBlank(s)) {
      return false;
    }

    for (int index = 0; index < s.length(); index++) {
      if (!CharUtils.isHexChar(s.charAt(index))) {
        return false;
      }
    }
    return true;
  }

  /**
   * judge whether legal octal number string
   */
  public static boolean isOctStr(String s) {
    if (isBlank(s)) {
      return false;
    }

    for (int index = 0; index < s.length(); index++) {
      if (!CharUtils.isOctChar(s.charAt(index))) {
        return false;
      }
    }

    return true;
  }

  /**
   * judge whether legal decimal number string
   */
  public static boolean isDecimalStr(String s) {
    if (isBlank(s)) {
      return false;
    }

    for (int index = 0; index < s.length(); index++) {
      if (!CharUtils.isDecimalChar(s.charAt(index))) {
        return false;
      }
    }
    return true;
  }

  public static boolean isBlank(String s) {
    if (s == null || s.length() == 0) {
      return true;
    }

    char c;
    for (int index = 0; index < s.length(); index++) {
      c = s.charAt(index);
      if (c != ' ') {
        return false;
      }
    }
    return true;
  }

  public static String toLowerCase(String s) {
    if (s == null) {
      return null;
    }
    return s.toLowerCase();
  }

  public static String toUpperCase(String s) {
    if (s == null) {
      return null;
    }
    return s.toUpperCase();
  }
}
