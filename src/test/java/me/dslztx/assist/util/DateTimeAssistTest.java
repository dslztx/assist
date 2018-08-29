package me.dslztx.assist.util;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;
import me.dslztx.assist.bean.DayOfWeek;

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
            assertTrue("2018-07-20 23:59:59.999".equals(DateTimeAssist.format(date, DateTimeAssist.YMD_HMS_MS)));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void generate2WithTimeZone() {
        try {
            Date date1 = DateTimeAssist.generate(2018, 7, 20, 23, 59, 59, 999);
            Date date2 = DateTimeAssist.generate(2018, 7, 20, 23, 59, 59, 999, TimeZone.getTimeZone("GMT+0720"));

            assertTrue(DateTimeAssist.gapInMinute(date2, date1) == 40L);
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
            assertTrue(DateTimeAssist.format(date, DateTimeAssist.YMD_HMS_MS_Z, TimeZone.getTimeZone("GMT+07:00"))
                .equals("2018-07-24 09:13:56.110 +0700"));
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
            Date b = DateTimeAssist.generate(2018, 7, 25, 2, 13, 56, 110, TimeZone.getTimeZone("GMT+08:00"));
            Assert.assertTrue(a.equals(b));

            Date c = DateTimeAssist.parse("2018-07-24 10:13:56.110", "yyyy-MM-dd HH:mm:ss.SSS",
                TimeZone.getTimeZone("GMT+07:00"));
            Date d = DateTimeAssist.generate(2018, 7, 24, 11, 13, 56, 110, TimeZone.getTimeZone("GMT+08:00"));
            Assert.assertTrue(c.equals(d));

            Date e = DateTimeAssist.parse("2018-07-24 10:13:56.110 GMT-08:00", "yyyy-MM-dd HH:mm:ss.SSS Z",
                TimeZone.getTimeZone("GMT+07:00"));
            Date f = DateTimeAssist.generate(2018, 7, 25, 2, 13, 56, 110, TimeZone.getTimeZone("GMT+08:00"));
            Assert.assertTrue(e.equals(f));
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
    public void other0() {
        try {
            Date date0 = DateTimeAssist.generate(1970, 1, 1, 0, 0, 0, 0, TimeZone.getTimeZone("GMT+00:00"));
            Date date1 = DateTimeAssist.generate(2018, 12, 10, 7, 30, 20, 500, TimeZone.getTimeZone("GMT+05:00"));
            Date date2 = DateTimeAssist.generate(2018, 12, 10, 8, 30, 20, 500, TimeZone.getTimeZone("GMT+06:00"));

            Assert.assertTrue(DateTimeAssist.gapInMilliSecond(date1, date0) == 1544409020500L);
            Assert.assertTrue(DateTimeAssist.gapInMilliSecond(date2, date0) == 1544409020500L);

            Assert.assertTrue(date1.getTime() == 1544409020500L);
            Assert.assertTrue(date2.getTime() == 1544409020500L);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void other1() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2018);
            calendar.set(Calendar.MONTH, 10);
            calendar.set(Calendar.DAY_OF_MONTH, 21);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 20);
            calendar.set(Calendar.MILLISECOND, 500);

            // 设置的时区参与日期时间的计算
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));

            // 调用getTime()方法，触发日期时间的计算
            Assert.assertTrue(calendar.getTime().getTime() == 1542771020500L);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void other2() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2018);
            calendar.set(Calendar.MONTH, 10);
            calendar.set(Calendar.DAY_OF_MONTH, 21);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 20);
            calendar.set(Calendar.MILLISECOND, 500);

            // 调用getTime()方法，触发日期时间的计算
            System.out.println(calendar.getTime());

            // 设置的时区不参与日期时间的计算，只表示转换时区
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));

            // 调用getTime()方法，先前已经完成日期时间的计算，不重复触发计算
            Assert.assertTrue(calendar.getTime().getTime() == 1542767420500L);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void other3() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2018);
            calendar.set(Calendar.MONTH, 10);
            calendar.set(Calendar.DAY_OF_MONTH, 21);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 20);
            calendar.set(Calendar.MILLISECOND, 500);

            // 调用getTime()方法，触发日期时间的计算
            System.out.println(calendar.getTime());

            // 调用set(int field, int value)方法，令先前完成的日期时间的计算失效
            calendar.set(Calendar.YEAR, 2018);

            // 设置的时区参与日期时间的计算
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));

            // 调用getTime()方法，触发日期时间的计算
            Assert.assertTrue(calendar.getTime().getTime() == 1542771020500L);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void other4() {
        try {
            // 2013-1-31 22:17:14
            Date date = new Date(1359641834000L);

            String dateStr = "2013-1-31 22:17:14";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            // parse时表示源时区为GMT+00:00，因此加上8个小时
            Date a = dateFormat.parse(dateStr);

            Date b = DateTimeAssist.generate(2013, 2, 1, 6, 17, 14, 0);
            Assert.assertTrue(a.equals(b));

            // format时表示目标时区为GMT+00:00，因此减去8个小时
            String dateStrTmp = dateFormat.format(date);

            Assert.assertTrue(dateStrTmp.equals("2013-01-31 14:17:14"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void obtainDayOfWeek() {
        try {
            Date date = DateTimeAssist.generate(2018, 8, 29);
            Assert.assertTrue(DateTimeAssist.obtainDayOfWeek(date) == DayOfWeek.WEDNESDAY);

            date = DateTimeAssist.generate(2018, 5, 20);
            Assert.assertTrue(DateTimeAssist.obtainDayOfWeek(date) == DayOfWeek.SUNDAY);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsHoliday() {
        try {
            Assert.assertTrue(DateTimeAssist.isHoliday(DateTimeAssist.generate(2018, 10, 1)));
            Assert.assertFalse(DateTimeAssist.isHoliday(DateTimeAssist.generate(2018, 9, 30)));
            Assert.assertFalse(DateTimeAssist.isHoliday(DateTimeAssist.generate(2018, 9, 30)));
            Assert.assertFalse(DateTimeAssist.isHoliday(DateTimeAssist.generate(2018, 8, 30)));
            Assert.assertTrue(DateTimeAssist.isHoliday(DateTimeAssist.generate(2018, 9, 1)));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}