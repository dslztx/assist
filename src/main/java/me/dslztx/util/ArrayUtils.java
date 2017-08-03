package me.dslztx.util;

/**
 * Array Utility
 *
 * @author dslztx
 */
public class ArrayUtils {

  public static boolean isEmpty(Object[] array) {
    if (array == null || array.length == 0) {
      return true;
    }
    return false;
  }
}
