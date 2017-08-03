package me.dslztx.util;

/**
 * String Utility
 *
 * @author dslztx
 */
public class StringUtils {

  /**
   * judge whether legal hexadecimal number string
   */
  public static boolean isHexStr(String s) {
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
}
