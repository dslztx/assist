package me.dslztx.assist.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * yyyy：年<br/>
 *
 * MM：月<br/>
 *
 * dd：日<br/>
 *
 * HH：小时<br/>
 *
 * mm：分钟<br/>
 *
 * ss：秒<br/>
 *
 * 毫秒<br/>
 */
public class DateAssist {

  private static final Logger logger = LoggerFactory.getLogger(DateAssist.class);

  public static Date generate(int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, day);

    return calendar.getTime();
  }

  public static Date generate(int year, int month, int day, int hour, int minute, int second) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.HOUR_OF_DAY, hour);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, second);
    return calendar.getTime();
  }


  public static Date generate(int year, int month, int day, int hour, int minute, int second,
      int millisecond) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.HOUR_OF_DAY, hour);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, second);
    calendar.set(Calendar.MILLISECOND, millisecond);
    return calendar.getTime();
  }

  public static int gapInYear(Date a, Date b) {
    Calendar calendarB = Calendar.getInstance();
    calendarB.setTime(b);

    int yearB = calendarB.get(Calendar.YEAR);

    Calendar calendarA = Calendar.getInstance();
    calendarA.setTime(a);
    int yearA = calendarA.get(Calendar.YEAR);

    if (CompareAssist.less(a, b)) {
      return yearB - yearA;
    } else {
      return yearA - yearB;
    }
  }

  public static int gapInMonth(Date a, Date b) {
    Calendar calendarB = Calendar.getInstance();
    calendarB.setTime(b);

    int yearB = calendarB.get(Calendar.YEAR);
    int monthB = calendarB.get(Calendar.MONTH);

    Calendar calendarA = Calendar.getInstance();
    calendarA.setTime(a);
    int yearA = calendarA.get(Calendar.YEAR);
    int monthA = calendarA.get(Calendar.MONTH);

    if (CompareAssist.less(a, b)) {
      return (yearB - yearA) * 12 + (monthB - monthA);
    } else {
      return (yearA - yearB) * 12 + (monthA - monthB);
    }
  }

  public static long gapInDay(Date a, Date b) {
    long offsetA = a.getTime();
    long offsetB = b.getTime();

    if (offsetA < offsetB) {
      return (offsetB - offsetA) / 1000 / 3600 / 24;
    } else {
      return (offsetA - offsetB) / 1000 / 3600 / 24;
    }
  }

  public static long gapInHour(Date a, Date b) {
    long offsetA = a.getTime();
    long offsetB = b.getTime();

    if (offsetA < offsetB) {
      return (offsetB - offsetA) / 1000 / 3600;
    } else {
      return (offsetA - offsetB) / 1000 / 3600;
    }
  }

  public static long gapInMinute(Date a, Date b) {
    long offsetA = a.getTime();
    long offsetB = b.getTime();

    if (offsetA < offsetB) {
      return (offsetB - offsetA) / 1000 / 60;
    } else {
      return (offsetA - offsetB) / 1000 / 60;
    }
  }

  public static long gapInSecond(Date a, Date b) {
    long offsetA = a.getTime();
    long offsetB = b.getTime();

    if (offsetA < offsetB) {
      return (offsetB - offsetA) / 1000;
    } else {
      return (offsetA - offsetB) / 1000;
    }
  }

  public static long gapInMilliSecond(Date a, Date b) {
    long offsetA = a.getTime();
    long offsetB = b.getTime();

    if (offsetA < offsetB) {
      return (offsetB - offsetA);
    } else {
      return (offsetA - offsetB);
    }
  }

  public static String format(Date a, String formatStr) {
    SimpleDateFormat formatter = new SimpleDateFormat(formatStr);

    return formatter.format(a);
  }

  public static Date parse(String date, String formatStr) {
    SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
    try {
      return formatter.parse(date);
    } catch (Exception e) {
      logger.error("", e);
      return null;
    }
  }
}
