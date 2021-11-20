package me.monster.auto.resource.tool;

import java.util.List;
import java.util.Random;

public class Lists {
    public static <E> E getRandom(List<E> list) {
        int min = 0;
        int max = list.size() - 1;
        int position = new Random().nextInt(max - min + 1) + min;
        return list.get(position);
    }
}
