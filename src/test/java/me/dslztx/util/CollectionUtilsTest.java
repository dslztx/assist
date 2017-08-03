package me.dslztx.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CollectionUtilsTest {

  @Test
  public void isEmpty() throws Exception {
    try {
      assertTrue(CollectionUtils.isEmpty(null));
      assertTrue(CollectionUtils.isEmpty(new ArrayList<String>()));
      List<String> strList = new ArrayList<String>();
      strList.add("hello");
      assertFalse(CollectionUtils.isEmpty(strList));
    } catch (Exception e) {
      fail();
    }
  }

}