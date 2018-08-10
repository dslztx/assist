package me.dslztx.assist.util;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(DateAssistTest.class);

  @Test
  public void generate() {
    try {
      Date date = DateAssist.generate(2018, 7, 20);
      assertTrue("2018-07-20".equals(DateAssist.format(date, DateAssist.YMD)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generateWithTimeZone() {
    try {
      Date date1 = DateAssist.generate(2018, 7, 20);

      Date date2 = DateAssist.generate(2018, 7, 20, TimeZone.getTimeZone("GMT+0700"));

      assertTrue(DateAssist.gapInHour(date1, date2) == 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate1() {
    try {
      Date date = DateAssist.generate(2018, 7, 20, 23, 59, 59);
      assertTrue("2018-07-20 23:59:59".equals(DateAssist.format(date, DateAssist.YMD_HMS)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate1WithTimeZone() {
    try {
      Date date1 = DateAssist.generate(2018, 7, 20, 23, 59, 59);
      Date date2 = DateAssist.generate(2018, 7, 20, 23, 59, 59, TimeZone.getTimeZone("GMT+0730"));

      assertTrue(DateAssist.gapInMinute(date1, date2) == 30);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate2() {
    try {
      Date date = DateAssist.generate(2018, 7, 20, 23, 59, 59, 999);
      assertTrue(
          "2018-07-20 23:59:59.999".equals(DateAssist.format(date, DateAssist.YMD_HMS_MS)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate2WithTimeZone() {
    try {
      Date date1 = DateAssist.generate(2018, 7, 20, 23, 59, 59, 999);
      Date date2 = DateAssist
          .generate(2018, 7, 20, 23, 59, 59, 999, TimeZone.getTimeZone("GMT+0720"));

      assertTrue(DateAssist.gapInMinute(date2, date1) == 40L);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInYear() {
    try {
      Date a = DateAssist.generate(2018, 7, 20);
      Date b = DateAssist.generate(2017, 7, 20);
      assertTrue(DateAssist.gapInYear(a, b) == 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInMonth() {
    try {
      Date a = DateAssist.generate(2018, 7, 20);
      Date b = DateAssist.generate(2017, 5, 20);
      assertTrue(DateAssist.gapInMonth(a, b) == 14);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void minusInDay() {
    try {
      Date a = DateAssist.generate(2018, 3, 1);
      Date b = DateAssist.minusInDay(a, 2);
      assertTrue("2018-02-27".equals(DateAssist.format(b, DateAssist.YMD)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void addInDay() {
    try {
      Date a = DateAssist.generate(2018, 3, 1);
      Date b = DateAssist.addInDay(a, 2);
      assertTrue("2018-03-03".equals(DateAssist.format(b, DateAssist.YMD)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInDay() {
    try {
      Date a = DateAssist.generate(2018, 3, 1, 0, 0, 0);
      Date b = DateAssist.generate(2018, 2, 26, 23, 59, 59);

      assertTrue(DateAssist.gapInDay(a, b) == 2);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInHour() {
    try {
      Date a = DateAssist.generate(2018, 3, 1, 0, 0, 0);
      Date b = DateAssist.generate(2018, 2, 26, 23, 59, 59);

      assertTrue(DateAssist.gapInHour(a, b) == 48);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInMinute() {
    try {
      Date a = DateAssist.generate(2018, 3, 1, 0, 0, 0, 0);
      Date b = DateAssist.generate(2018, 2, 26, 23, 59, 0, 0);

      assertTrue(DateAssist.gapInMinute(a, b) == 48 * 60 + 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInSecond() {
    try {
      Date a = DateAssist.generate(2018, 3, 1, 0, 0, 0, 0);
      Date b = DateAssist.generate(2018, 2, 26, 23, 59, 59, 0);

      assertTrue(DateAssist.gapInSecond(a, b) == 48 * 60 * 60 + 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInMilliSecond() {
    try {
      Date a = DateAssist.generate(2018, 3, 1, 0, 0, 0, 0);
      Date b = DateAssist.generate(2018, 2, 26, 23, 59, 59, 100);

      assertTrue(DateAssist.gapInMilliSecond(a, b) == 48 * 60 * 60 * 1000 + 900);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void format() {
    try {
      Date date = DateAssist.generate(2018, 7, 24, 10, 13, 56, 110);
      assertTrue(DateAssist.format(date, DateAssist.YMD_HMS_MS).equals("2018-07-24 10:13:56.110"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void formatWithTimeZone() {
    try {
      Date date = DateAssist.generate(2018, 7, 24, 10, 13, 56, 110);
      System.out.println(DateAssist.format(date, "yyyy-MM-dd HH:mm:ss.SSS 'GMT'Z"));
      assertTrue(DateAssist.format(date, DateAssist.YMD_HMS_MS).equals("2018-07-24 10:13:56.110"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void parse() {
    try {
      Date a = DateAssist.parse("2018-07-24 10:13:56.110", DateAssist.YMD_HMS_MS);
      Date b = DateAssist.generate(2018, 7, 24, 10, 13, 56, 110);

      assertTrue(a.equals(b));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void parseWithTimeZone() {
    try {

      Date a = DateAssist.parse("2018-07-24 10:13:56.110 GMT-08:00", "yyyy-MM-dd HH:mm:ss.SSS Z");
//      Date b = DateAssist.generate(2018, 7, 24, 10, 13, 56, 110);

//      assertTrue(a.equals(b));
      System.out.println(a);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }
}