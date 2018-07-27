package me.dslztx.assist.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.util.Arrays;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 * @date 2015年08月11日
 */
public class CodingAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(CodingAssistTest.class);

  @Test
  public void encodeTest() {
    try {
      byte[] a = CodingAssist.encode("好", Charset.forName("UTF-8"));
      byte[] b = new byte[3];
      b[0] = (byte) 0xe5;
      b[1] = (byte) 0xa5;
      b[2] = (byte) 0xbd;
      assertTrue(Arrays.equals(a, b));

      byte[] c = CodingAssist.encode("好", Charset.forName("GBK"));
      byte[] d = new byte[2];
      d[0] = (byte) 0xba;
      d[1] = (byte) 0xc3;
      assertTrue(Arrays.equals(c, d));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void decodeTest() {
    try {
      byte[] b = new byte[3];
      b[0] = (byte) 0xe5;
      b[1] = (byte) 0xa5;
      b[2] = (byte) 0xbd;
      String str = CodingAssist.decode(b, Charset.forName("UTF-8"));
      assertTrue(str.equals("好"));

      byte[] d = new byte[2];
      d[0] = (byte) 0xba;
      d[1] = (byte) 0xc3;
      String str1 = CodingAssist.decode(d, Charset.forName("GBK"));
      assertTrue(str1.equals("好"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }
}
