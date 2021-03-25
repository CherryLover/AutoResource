package me.monster.auto.resource.tool;

import java.util.Objects;

public class StringUtils {
    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || "".equals(text);
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }
}
