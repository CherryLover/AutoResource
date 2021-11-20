package me.monster.auto.resource;

import me.monster.auto.resource.action.DayReminderAction;
import me.monster.auto.resource.tool.NotificationHelper;

public class TestEntrance {
    public static void main(String[] args) {
        final NotificationHelper notificationHelper = new NotificationHelper();
        System.out.println("init notification helper");
        final DayReminderAction dayReminderAction = new DayReminderAction();
        dayReminderAction.fetchInfo();
    }
}
