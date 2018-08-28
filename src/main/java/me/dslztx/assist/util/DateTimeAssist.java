package me.dslztx.assist.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在Java中，日期时间主要涉及到4个类：Date，Calendar，SimpleDateFormat，TimeZone
 *
 * @author dslztx
 */
public class DateTimeAssist {

    public static final String YMD = "yyyy-MM-dd";
    public static final String YMD_Z = "yyyy-MM-dd Z";

    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_HMS_Z = "yyyy-MM-dd HH:mm:ss Z";

    public static final String YMD_HMS_MS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YMD_HMS_MS_Z = "yyyy-MM-dd HH:mm:ss.SSS Z";

    private static final Logger logger = LoggerFactory.getLogger(DateTimeAssist.class);

    public static Date generate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }

    public static Date generate(int year, int month, int day, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        calendar.setTimeZone(timeZone);

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

    public static Date generate(int year, int month, int day, int hour, int minute, int second, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        calendar.setTimeZone(timeZone);

        return calendar.getTime();
    }

    public static Date generate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
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

    public static Date generate(int year, int month, int day, int hour, int minute, int second, int millisecond,
        TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);

        calendar.setTimeZone(timeZone);

        return calendar.getTime();
    }

    /**
     * 常见的格式化字符串：YMD，YMD_HMS，YMD_HMS_MS
     *
     * @see java.text.SimpleDateFormat
     */
    public static String format(Date a, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);

        return formatter.format(a);
    }

    /**
     * 常见的格式化字符串：YMD，YMD_HMS，YMD_HMS_MS
     * 
     * @param targetTimeZone 格式化时的目标时区
     * @see java.text.SimpleDateFormat
     */
    public static String format(Date a, String pattern, TimeZone targetTimeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        formatter.setTimeZone(targetTimeZone);

        return formatter.format(a);
    }

    /**
     * 常见的格式化字符串：YMD，YMD_HMS，YMD_HMS_MS
     *
     * @see java.text.SimpleDateFormat
     */
    public static Date parse(String dateTime, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);

        try {
            return formatter.parse(dateTime);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 常见的格式化字符串：YMD，YMD_HMS，YMD_HMS_MS
     * 
     * @param sourceTimeZone 解析时的源时区，如果在dateTime日期时间字符串中已经包含“时区”信息，该参数则无意义
     * @see java.text.SimpleDateFormat
     */
    public static Date parse(String dateTime, String pattern, TimeZone sourceTimeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        formatter.setTimeZone(sourceTimeZone);

        try {
            return formatter.parse(dateTime);
        } catch (Exception e) {
            logger.error("", e);
            return null;
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

    public static Date minusInDay(Date a, int minusDay) {
        if (minusDay < 0) {
            throw new RuntimeException("minusDay is negative");
        }
        return new Date(a.getTime() - ((long)minusDay) * 24 * 3600 * 1000);
    }

    public static Date addInDay(Date a, int addDay) {
        if (addDay < 0) {
            throw new RuntimeException("addDay is negative");
        }
        return new Date(a.getTime() + ((long)addDay) * 24 * 3600 * 1000);
    }
}
