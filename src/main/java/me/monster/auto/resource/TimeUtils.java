package me.monster.auto.resource;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/8 11:31 上午
 */
public class TimeUtils {

    public static String currentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(System.currentTimeMillis()));
    }
}
