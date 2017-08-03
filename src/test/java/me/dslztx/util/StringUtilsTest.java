package me.dslztx.util;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 * @date 2015年08月18日
 */
public class StringUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(StringUtilsTest.class);

  @Test
  public void testIsHexStr() throws Exception {
    try {
      assertTrue(StringUtils.isHexStr("aaFf"));
      assertFalse(StringUtils.isHexStr("zu"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testIsOctStr() throws Exception {
    try {
      assertTrue(StringUtils.isOctStr("07770"));
      assertFalse(StringUtils.isOctStr("0768"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testIsDecimalStr() throws Exception {
    try {
      assertTrue(StringUtils.isDecimalStr("3128390217"));
      assertFalse(StringUtils.isDecimalStr("983219z"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void testIsBlank() {
    try {
      assertTrue(StringUtils.isBlank(""));
      assertTrue(StringUtils.isBlank(null));
      assertTrue(StringUtils.isBlank("   "));
      assertFalse(StringUtils.isBlank("  fdsfd "));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }
}
