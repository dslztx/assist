package me.dslztx.assist.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CollectionAssistTest {

  @Test
  public void isEmpty() throws Exception {
    try {
      assertTrue(CollectionAssist.isEmpty(null));
      assertTrue(CollectionAssist.isEmpty(new ArrayList<String>()));
      List<String> strList = new ArrayList<String>();
      strList.add("hello");
      assertFalse(CollectionAssist.isEmpty(strList));
    } catch (Exception e) {
      fail();
    }
  }

}