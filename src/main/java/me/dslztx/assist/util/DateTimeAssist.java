package me.dslztx.assist.util;

import java.io.BufferedReader;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.bean.DayOfWeek;

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

    private static Map<String, Integer> holidayArrangement = new HashMap<String, Integer>();

    private static volatile boolean initHolidayArrangement = false;

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

    public static DayOfWeek obtainDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        // 根据Calendar类的使用最佳实践，一般不允许使用“setTime(Date date)”方法
        calendar.setTime(date);

        int value = calendar.get(Calendar.DAY_OF_WEEK);
        if (value == 1) {
            return DayOfWeek.SUNDAY;
        } else if (value == 2) {
            return DayOfWeek.MONDAY;
        } else if (value == 3) {
            return DayOfWeek.TUESDAY;
        } else if (value == 4) {
            return DayOfWeek.WEDNESDAY;
        } else if (value == 5) {
            return DayOfWeek.THURSDAY;
        } else if (value == 6) {
            return DayOfWeek.FRIDAY;
        } else {
            return DayOfWeek.SATURDAY;
        }
    }

    /**
     * 判断某天是否为休息日，根据国务院放假安排进行修正
     */
    public static boolean isHoliday(Date date) {
        if (!initHolidayArrangement) {
            initHolidayArrangement();
        }

        String dateTimeStr = DateTimeAssist.format(date, YMD);
        if (holidayArrangement.get(dateTimeStr) == null) {
            DayOfWeek dayOfWeek = obtainDayOfWeek(date);
            return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
        } else {
            return holidayArrangement.get(dateTimeStr).equals(1);
        }
    }

    private static void initHolidayArrangement() {
        if (!initHolidayArrangement) {
            synchronized (DateTimeAssist.class) {
                if (!initHolidayArrangement) {
                    File[] dirs = FileAssist.locateClassPathFiles("holiday_arrangement");
                    if (ArrayAssist.isNotEmpty(dirs)) {
                        for (File dir : dirs) {
                            if (dir == null || !dir.isDirectory()) {
                                continue;
                            }

                            if (ArrayAssist.isNotEmpty(dir.listFiles())) {
                                for (File file : dir.listFiles()) {
                                    load(file);
                                }
                            }
                        }
                    }

                    initHolidayArrangement = true;
                }
            }
        }
    }

    private static void load(File file) {
        BufferedReader in = null;
        try {
            in = IOAssist.bufferedReader(file);
            String line = null;
            while ((line = in.readLine()) != null) {
                try {
                    if (StringAssist.isBlank(line)) {
                        continue;
                    }

                    String[] ss = StringAssist.split(line, ' ', true);
                    holidayArrangement.put(ss[0], Integer.valueOf(ss[1]));
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.closeQuietly(in);
        }
    }

    public static String obtainTimeZoneFromDateTimeStr(String dateTime, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
            format.parse(dateTime);

            GregorianCalendar calendar = (GregorianCalendar)format.getCalendar();

            int[] zoneOffsets = (int[])ReflectAssist.obtainInstanceFieldValue(calendar, "zoneOffsets");

            return obtainTimeZoneFromZoneOffsets(zoneOffsets);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    private static String obtainTimeZoneFromZoneOffsets(int[] zoneOffsets) {
        int sum = zoneOffsets[0] + zoneOffsets[1];
        if (sum == 0) {
            return "GMT";
        } else {
            if (sum > 0) {
                return "GMT+" + convertToHourMinute(sum);
            } else {
                return "GMT-" + convertToHourMinute(sum * (-1));
            }
        }
    }

    private static String convertToHourMinute(int offsetSum) {
        int hour = offsetSum / 1000 / 3600;

        int minute = offsetSum / 1000 / 60 - hour * 60;

        StringBuilder sb = new StringBuilder();
        if (hour >= 10) {
            sb.append(hour);
        } else {
            sb.append("0");
            sb.append(hour);
        }
        sb.append(":");

        if (minute >= 10) {
            sb.append(minute);
        } else {
            sb.append("0");
            sb.append(minute);
        }
        return sb.toString();
    }
}
