package me.dslztx.assist.util;

import java.util.Collection;

/**
 * Collection Utility
 *
 * @author dslztx
 */
public class CollectionUtils {

  public static boolean isEmpty(Collection collection) {
    if (collection == null || collection.isEmpty()) {
      return true;
    }
    return false;
  }
}
