package me.monster.auto.resource;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
}
