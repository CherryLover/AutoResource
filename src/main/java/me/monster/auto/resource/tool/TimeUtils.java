package me.monster.auto.resource.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/8 11:31 上午
 */
public class TimeUtils {

    /**
     * 返回当前时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String currentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 返回当前时间
     * @return yyyy-MM-dd
     */
    public static String currentDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static String formatEndDate(String endDate) {
        LocalDate localDate = LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static final String PATTER_CONFIG = "yyyy.MM.dd HH:mm";
    public static final String PATTER_NORMAL_YEAR_DAY = "yyyy-MM-dd";

    public static String formatConfigDayTime(long date) {
        return format(PATTER_CONFIG, date);
    }

    public static String format(String pattern, long time) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(time));
    }

    public static Date parseConfigDayTime(String text) {
        return parse(PATTER_CONFIG, text);
    }

    public static Date parse(String pattern, String text) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            return format.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断时间是否为今天，逻辑为获取今天的 yyyy-MM-dd 24:00:00.00
     * 原因是：过了 00 点就是新的一天，没到第二天的 0 点时也应该算这这一天
     * @param date 目标时间
     * @return
     */
    public static boolean isToday(Date date) {
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(System.currentTimeMillis());
        current.set(Calendar.HOUR_OF_DAY, 24);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);
        String today = format(PATTER_NORMAL_YEAR_DAY, current.getTimeInMillis());
        String target = format(PATTER_NORMAL_YEAR_DAY, date.getTime());
        return today.equals(target);
    }

    /**
     *
     * @param date
     * @param amount
     * @param field 来自 Calendar 中的 field，例如 {@link Calendar#YEAR}
     * @return
     */
    public static Date addRange(Date date, int amount, int field) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(field, amount);
        return instance.getTime();
    }

    private static long DAY = 1000 * 3600 * 24;

    /**
     * 计算两天之间的时间差的天数，不满一天按一天计算
     * @return
     */
    public static int diffCount(Date date1, Date date2) {
        long max = Math.max(date1.getTime(), date2.getTime());
        long min = Math.min(date1.getTime(), date2.getTime());
        long day = (max - min) / DAY;
        final long more = (max - min) % DAY;
        if (more < DAY && more > 0) {
            day += 1;
        }
        return (int) day;
    }
}
