package me.dslztx.assist.util;

/**
 * @author dslztx
 */
public class ArrayUtils {

  public static boolean isEmpty(Object[] array) {
    if (array == null || array.length == 0) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(byte[] array) {
    if (array == null || array.length == 0) {
      return true;
    }
    return false;
  }
}
