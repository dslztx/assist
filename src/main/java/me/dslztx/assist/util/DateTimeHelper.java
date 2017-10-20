package me.dslztx.assist.util;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * 
 * @date 2015年5月8日
 * @author dslztx
 */
//todo
public class DateTimeHelper {
    /**
     * 获取当前时间
     * 
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取指定年，月，日的时间
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date obtainFromYearMonthDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        // 月份以0开始编号
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    /**
     * millis是到"midnight, January 1, 1970 UTC"的时间位移，单位是毫秒
     * 
     * @param millis
     * @return
     */
    public static Date obtainFromEpochOffset(long millis) {
        return new Date(millis);
    }

    /**
     * 获取距指定时间day天位移的时间
     * 
     * @param date
     * @param day
     * @return
     */
    public static Date obtainFromAssignedDateDayOffset(Date date, long day) {
        long timeInMillis = date.getTime();
        return new Date(timeInMillis + day * 24 * 3600 * 1000);
    }

    /**
     * 将输入时间转换为format指定形式的时间
     * 
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static String format(Date date, DatetimeFormat format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format.formatStr);
        return formatter.format(date);
    }

    /**
     * 以format指定形式解析输入时间字符串，获取时间
     * 
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateStr, DatetimeFormat format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format.formatStr);
        return formatter.parse(dateStr);
    }

    /**
     * 判断左侧时间是否先于右侧时间
     * 
     * @param left
     * @param right
     * @return
     */
    public static boolean isBefore(Date left, Date right) {
        return left.before(right);
    }

    /**
     * 计算两指定时间之间的距离天数
     * 
     * @param left
     * @param right
     * @return
     */
    public static int dayOffsetBetweenDates(Date left, Date right) {
        long leftTimeMillis = left.getTime();
        long rightTimeMillis = right.getTime();
        return (int) ((rightTimeMillis - leftTimeMillis) / 1000 / 3600 / 24);
    }

    /**
     * 将日期对象的时分秒毫秒都设为0值，即将日期转换成午夜时间的形式
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date convertToMidNight(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DatetimeFormat.yyyyMMdd.formatStr);
        return formatter.parse(formatter.format(date));
    }

    /**
     * 日期格式集合
     */
    public enum DatetimeFormat {
        yyyyMMdd("yyyy-MM-dd"), yyyyMMddHHmmss("yyyy-MM-dd HH:mm:ss"), SimpleChineseDate("yyyy年MM月dd号"),
        ComplexChineseDate("yyyy年MM月dd号HH点mm分ss秒");

        String formatStr;

        DatetimeFormat(String formatStr) {
            this.formatStr = formatStr;
        }
    }

    public static void main(String[] args) throws ParseException {
        // System.out.println(DateTimeHelper.now());
        // System.out.println(DateTimeHelper.obtainFromYearMonthDay(1989, 7, 3));
        // long time = System.currentTimeMillis();
        // System.out.println(DateTimeHelper.obtainFromEpochOffset(time));
        // System.out.println(DateTimeHelper.obtainFromAssignedDateDayOffset(new Date(), 10));
        // System.out.println(DateTimeHelper.format(new Date(), DatetimeFormat.SimpleChineseDate));
        // System.out.println(DateTimeHelper.parse("1989年8月7号18点59分59秒", DatetimeFormat.ComplexChineseDate));
        // System.out.println(DateTimeHelper.isBefore(new Date(),
        // DateTimeHelper.obtainFromAssignedDateDayOffset(new Date(), 10)));
        // System.out.println(DateTimeHelper.dayOffsetBetweenDates(new Date(),
        // DateTimeHelper.obtainFromAssignedDateDayOffset(new Date(), 10)));
        //
        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // Date date = new Date();
        // String str = format.format(date);
        // System.out.println(str);
        // Date newDate = format.parse(str);
        // System.out.println(newDate);
        // System.out.println(DateTimeHelper.obtainFromAssignedDateDayOffset(newDate, 90));
        // System.out.println(new Date(0));
        // Calendar calendar = Calendar.getInstance();
        // System.out.println(DateTimeHelper.obtainFromYearMonthDay(2015, 11, 20));
        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // String formatStr = format.format(new Date());
        // System.out.println(format.parse(formatStr));
        // System.out.println(DateTimeHelper.convertToMidNight(new Date(0)));
        // System.out.println(DateTimeHelper.convertToMidNight(new Date()));
        // long time1 = DateTimeHelper.convertToMidNight(new Date(0)).getTime();
        // long time = DateTimeHelper.convertToMidNight(new Date()).getTime();
        // System.out.println((time - time1) / 1000 / 3600 / 24);
        // SimpleDateFormat formatter = new SimpleDateFormat("HH");
        // System.out.println(formatter.format(new Date()));
        //
        // SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHH");
        // System.out.println(formatter2.format(new Date()));
        //
        // Integer a = 5;
        // Integer b = 10;
        // Integer c = 11;
        // System.out.println((a.compareTo(b) <= 0 && b.compareTo(c) <= 0));
        //
        // System.out.println(new Date(0).getTime());

        // Date date = new Date();
        // long timeInMillus = date.getTime();
        // System.out.println(new Date(timeInMillus));
        // System.out.println(new Date(timeInMillus - 12 * 3600 * 1000));
        // System.out.println(new Date(System.currentTimeMillis() + 2 * 60 * 1000L));
        // System.out.println(ConfigType.NUMERICAL.ordinal());
        // String as = "899.3";
        // Double a = Double.parseDouble(as);
        // DecimalFormat decimalFormat = new DecimalFormat("#.##");
        // System.out.println(String.format("%.0f", a));
        // Double b = Double.parseDouble(String.format("%.2f", a));
        // System.out.println(b);
        //
        // System.out.println(Math.pow(0.1, 2));
        // System.out.println(Math.pow(0.1, 0));
        // for (int i = 0; i < 10; i++) {
        // System.out.println(String.format("%.11f", getResult(i)));
        // }
        String hello = "好";
        byte[] aa = hello.getBytes(Charset.forName("utf-8"));
        System.out.println("hello");
        for (int i = 0; i < aa.length; i++) {
            String hex = Integer.toHexString(aa[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
             
            }   System.out.println(hex);
        }
        System.out.println(Charset.defaultCharset().name());
    }

    public enum ConfigType {
        NUMERICAL, STRING;
    }

    private static double getResult(int precision) {
        double result = 1.0;
        for (int i = 0; i < precision; i++) {
            result = result / 10.0;
        }
        return result;
    }
}
