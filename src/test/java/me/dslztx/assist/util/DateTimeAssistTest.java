package me.dslztx.assist.util;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(DateTimeAssistTest.class);

  @Test
  public void generate() {
    try {
      Date date = DateTimeAssist.generate(2018, 7, 20);
      assertTrue("2018-07-20".equals(DateTimeAssist.format(date, DateTimeAssist.YMD)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generateWithTimeZone() {
    try {
      Date date1 = DateTimeAssist.generate(2018, 7, 20);

      Date date2 = DateTimeAssist.generate(2018, 7, 20, TimeZone.getTimeZone("GMT+0700"));

      assertTrue(DateTimeAssist.gapInHour(date1, date2) == 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate1() {
    try {
      Date date = DateTimeAssist.generate(2018, 7, 20, 23, 59, 59);
      assertTrue("2018-07-20 23:59:59".equals(DateTimeAssist.format(date, DateTimeAssist.YMD_HMS)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate1WithTimeZone() {
    try {
      Date date1 = DateTimeAssist.generate(2018, 7, 20, 23, 59, 59);
      Date date2 = DateTimeAssist.generate(2018, 7, 20, 23, 59, 59, TimeZone.getTimeZone("GMT+0730"));

      assertTrue(DateTimeAssist.gapInMinute(date1, date2) == 30);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate2() {
    try {
      Date date = DateTimeAssist.generate(2018, 7, 20, 23, 59, 59, 999);
      assertTrue(
          "2018-07-20 23:59:59.999".equals(DateTimeAssist.format(date, DateTimeAssist.YMD_HMS_MS)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void generate2WithTimeZone() {
    try {
      Date date1 = DateTimeAssist.generate(2018, 7, 20, 23, 59, 59, 999);
      Date date2 = DateTimeAssist
          .generate(2018, 7, 20, 23, 59, 59, 999, TimeZone.getTimeZone("GMT+0720"));

      assertTrue(DateTimeAssist.gapInMinute(date2, date1) == 40L);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInYear() {
    try {
      Date a = DateTimeAssist.generate(2018, 7, 20);
      Date b = DateTimeAssist.generate(2017, 7, 20);
      assertTrue(DateTimeAssist.gapInYear(a, b) == 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInMonth() {
    try {
      Date a = DateTimeAssist.generate(2018, 7, 20);
      Date b = DateTimeAssist.generate(2017, 5, 20);
      assertTrue(DateTimeAssist.gapInMonth(a, b) == 14);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void minusInDay() {
    try {
      Date a = DateTimeAssist.generate(2018, 3, 1);
      Date b = DateTimeAssist.minusInDay(a, 2);
      assertTrue("2018-02-27".equals(DateTimeAssist.format(b, DateTimeAssist.YMD)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void addInDay() {
    try {
      Date a = DateTimeAssist.generate(2018, 3, 1);
      Date b = DateTimeAssist.addInDay(a, 2);
      assertTrue("2018-03-03".equals(DateTimeAssist.format(b, DateTimeAssist.YMD)));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInDay() {
    try {
      Date a = DateTimeAssist.generate(2018, 3, 1, 0, 0, 0);
      Date b = DateTimeAssist.generate(2018, 2, 26, 23, 59, 59);

      assertTrue(DateTimeAssist.gapInDay(a, b) == 2);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInHour() {
    try {
      Date a = DateTimeAssist.generate(2018, 3, 1, 0, 0, 0);
      Date b = DateTimeAssist.generate(2018, 2, 26, 23, 59, 59);

      assertTrue(DateTimeAssist.gapInHour(a, b) == 48);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInMinute() {
    try {
      Date a = DateTimeAssist.generate(2018, 3, 1, 0, 0, 0, 0);
      Date b = DateTimeAssist.generate(2018, 2, 26, 23, 59, 0, 0);

      assertTrue(DateTimeAssist.gapInMinute(a, b) == 48 * 60 + 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInSecond() {
    try {
      Date a = DateTimeAssist.generate(2018, 3, 1, 0, 0, 0, 0);
      Date b = DateTimeAssist.generate(2018, 2, 26, 23, 59, 59, 0);

      assertTrue(DateTimeAssist.gapInSecond(a, b) == 48 * 60 * 60 + 1);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void gapInMilliSecond() {
    try {
      Date a = DateTimeAssist.generate(2018, 3, 1, 0, 0, 0, 0);
      Date b = DateTimeAssist.generate(2018, 2, 26, 23, 59, 59, 100);

      assertTrue(DateTimeAssist.gapInMilliSecond(a, b) == 48 * 60 * 60 * 1000 + 900);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void format() {
    try {
      Date date = DateTimeAssist.generate(2018, 7, 24, 10, 13, 56, 110);
      assertTrue(DateTimeAssist.format(date, DateTimeAssist.YMD_HMS_MS).equals("2018-07-24 10:13:56.110"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void formatWithTimeZone() {
    try {
      Date date = DateTimeAssist.generate(2018, 7, 24, 10, 13, 56, 110);
      System.out.println(DateTimeAssist.format(date, "yyyy-MM-dd HH:mm:ss.SSS 'GMT'Z"));
      assertTrue(DateTimeAssist.format(date, DateTimeAssist.YMD_HMS_MS).equals("2018-07-24 10:13:56.110"));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void parse() {
    try {
      Date a = DateTimeAssist.parse("2018-07-24 10:13:56.110", DateTimeAssist.YMD_HMS_MS);
      Date b = DateTimeAssist.generate(2018, 7, 24, 10, 13, 56, 110);

      assertTrue(a.equals(b));
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }

  @Test
  public void parseWithTimeZone() {
    try {

      Date a = DateTimeAssist.parse("2018-07-24 10:13:56.110 GMT-08:00", "yyyy-MM-dd HH:mm:ss.SSS Z");
//      Date b = DateTimeAssist.generate(2018, 7, 24, 10, 13, 56, 110);

//      assertTrue(a.equals(b));
      System.out.println(a);
    } catch (Exception e) {
      logger.error("", e);
      fail();
    }
  }
}