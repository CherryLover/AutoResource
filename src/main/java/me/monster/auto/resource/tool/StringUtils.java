package me.monster.auto.resource.tool;

import java.util.Objects;

public class StringUtils {
    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || "".equals(text);
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static int parseInt(String text, int defaultValue) {
        int a = defaultValue;
        try {
            a = Integer.parseInt(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}
