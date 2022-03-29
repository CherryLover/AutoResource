package me.monster.auto.resource;


import me.monster.auto.resource.action.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestEntrance {
    public static void main(String[] args) {
//        GeekReadInfoAction geekReadInfoAction = new GeekReadInfoAction();
//        geekReadInfoAction.fetchInfo();
        List<AutoAction> list = new ArrayList<>();
        list.add(new BingAutoAction());
        list.add(new DayReminderAction());
        list.add(new GeekReadInfoAction());
        list.add(new LovelyAction());
        list.add(new UnsplashImgAutoAction());
        for (AutoAction autoAction : list) {
            System.out.print(autoAction.name() + " " + autoAction.priority() + "\n");
        }
        Collections.sort(list, (o1, o2) -> Integer.compare(o1.priority(), o2.priority()));
        System.out.println("排序后");
        for (AutoAction autoAction : list) {
            System.out.print(autoAction.name() + " " + autoAction.priority() + "\n");
        }
    }


}
