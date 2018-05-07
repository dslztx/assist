package me.dslztx.assist.util;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 * @date 2015年08月18日
 */
public class StringAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(StringAssistTest.class);

  @Test
  public void testIsHexStr() throws Exception {
    try {
      assertTrue(StringAssist.isHexStr("aaFf"));
      assertFalse(StringAssist.isHexStr("zu"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testIsOctStr() throws Exception {
    try {
      assertTrue(StringAssist.isOctStr("07770"));
      assertFalse(StringAssist.isOctStr("0768"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testIsDecimalStr() throws Exception {
    try {
      assertTrue(StringAssist.isDecimalStr("3128390217"));
      assertFalse(StringAssist.isDecimalStr("983219z"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testIsBlank() {
    try {
      assertTrue(StringAssist.isBlank(""));
      assertTrue(StringAssist.isBlank(null));
      assertTrue(StringAssist.isBlank("   "));
      assertFalse(StringAssist.isBlank("  fdsfd "));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testToLowerCase() {
    try {
      assertNull(StringAssist.toLowerCase(null));
      assertTrue(StringAssist.toLowerCase("hEllo").equals("hello"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testToUpperCase() {
    try {
      assertNull(StringAssist.toUpperCase(null));
      assertTrue(StringAssist.toUpperCase("hEllo").equals("HELLO"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }
}
