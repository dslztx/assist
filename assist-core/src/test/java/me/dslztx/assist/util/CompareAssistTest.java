package me.dslztx.assist.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CompareAssistTest {

  @Test
  public void less() {
    try {
      assertTrue(CompareAssist.less(new Integer(5), new Integer(10)));
      assertFalse(CompareAssist.less(new Integer(15), new Integer(10)));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void lessEqual() {
    try {
      assertTrue(CompareAssist.lessEqual(new Integer(5), new Integer(10)));
      assertTrue(CompareAssist.lessEqual(new Integer(10), new Integer(10)));
      assertFalse(CompareAssist.lessEqual(new Integer(11), new Integer(10)));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void greater() {
    try {
      assertTrue(CompareAssist.greater(new Integer(50), new Integer(10)));
      assertFalse(CompareAssist.greater(new Integer(1), new Integer(10)));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void greaterEqual() {
    try {
      assertTrue(CompareAssist.greaterEqual(new Integer(50), new Integer(10)));
      assertTrue(CompareAssist.greaterEqual(new Integer(10), new Integer(10)));
      assertFalse(CompareAssist.greaterEqual(new Integer(1), new Integer(10)));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void equal() {
    try {
      assertTrue(CompareAssist.equal(new Integer(10), new Integer(10)));
      assertFalse(CompareAssist.equal(new Integer(1), new Integer(10)));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}