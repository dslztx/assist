package me.dslztx.assist.util;

/**
 * @author dslztx
 */
public class CompareAssist {

  public static <T extends Comparable<T>> boolean less(T a, T b) {
    return a.compareTo(b) < 0;
  }

  public static <T extends Comparable<T>> boolean lessEqual(T a, T b) {
    return a.compareTo(b) <= 0;
  }

  public static <T extends Comparable<T>> boolean greater(T a, T b) {
    return a.compareTo(b) > 0;
  }

  public static <T extends Comparable<T>> boolean greaterEqual(T a, T b) {
    return a.compareTo(b) >= 0;
  }

  public static <T extends Comparable<T>> boolean equal(T a, T b) {
    return a.compareTo(b) == 0;
  }

}
