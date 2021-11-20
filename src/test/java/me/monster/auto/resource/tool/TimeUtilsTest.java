package me.monster.auto.resource.tool;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TimeUtilsTest {

    @Test
    public void parseConfigDayTime() {
        String text = "2021.11.10 12:05";
        Date date = TimeUtils.parseConfigDayTime(text);
        assertNotNull(date);
    }

    @Test
    public void diffCount() {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.set(Calendar.HOUR_OF_DAY, 24);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        System.out.println("sdsd " + TimeUtils.formatConfigDayTime(instance.getTimeInMillis()));

        Date date1 = TimeUtils.parseConfigDayTime("2021.11.20 13:35");
        Date date2 = TimeUtils.parseConfigDayTime("2019.01.01 00:00");
        assertEquals(904, TimeUtils.diffCount(date1, date2));
    }
}