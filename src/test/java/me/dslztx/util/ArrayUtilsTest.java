package me.dslztx.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ArrayUtilsTest {

  @Test
  public void isEmpty() throws Exception {
    try {
      assertTrue(ArrayUtils.isEmpty(null));
      assertTrue(ArrayUtils.isEmpty(new String[0]));
      assertFalse(ArrayUtils.isEmpty(new String[1]));
    } catch (Exception e) {
      fail();
    }
  }
}