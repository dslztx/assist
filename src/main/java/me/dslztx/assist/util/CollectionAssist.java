package me.dslztx.assist.util;

import java.util.Collection;

/**
 * @author dslztx
 */
public class CollectionAssist {

  public static boolean isEmpty(Collection collection) {
    if (collection == null || collection.isEmpty()) {
      return true;
    }
    return false;
  }
}
