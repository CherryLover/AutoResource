package me.monster.auto.resource.action;

import com.google.gson.Gson;
import me.monster.auto.resource.Entrance;
import me.monster.auto.resource.bean.NotificationVo;
import me.monster.auto.resource.bean.Reminder;
import me.monster.auto.resource.bean.ReminderConfig;
import me.monster.auto.resource.tool.FileUtils;
import me.monster.auto.resource.tool.Lists;
import me.monster.auto.resource.tool.TelegramNotificationList;
import me.monster.auto.resource.tool.TimeUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 纪念日检查
 */
public class DayReminderAction implements AutoAction {

    @Override
    public void fetchInfo() {
        try {
            String config = FileUtils.getContent("ReminderConfig.json");
            final ReminderConfig reminderConfig = new Gson().fromJson(config, ReminderConfig.class);
            filterToNotification(reminderConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void filterToNotification(ReminderConfig reminderConfig) {
        final String message = generateNotification(reminderConfig);
        final NotificationVo notification = new NotificationVo(message, NotificationVo.SOURCE_DAY_REMINDER);
        TelegramNotificationList.getInstance().addNotification(notification);
    }

    private String generateNotification(ReminderConfig config) {
        final List<ReminderConfig.Days> days = applyTimeSchedule(config);
        System.out.println("config size " + days.size());
        final List<Reminder> reminderList = match(config, days);
        StringBuilder sb = new StringBuilder();
        if (reminderList.isEmpty()) {
            System.out.println("reminder is empty");
            final ReminderConfig.Days random = Lists.getRandom(config.getDays());
            sb.append("今天是")
                    .append(random.getEvent())
                    .append('(').append(random.getTime()).append(')').append("的")
                    .append("第 ").append(TimeUtils.diffCount(new Date(), random.getConvertDate())).append(" 天");
        } else {
            int length = reminderList.size();
            for (int i = 0; i < length; i++) {
                final Reminder reminder = reminderList.get(i);
                sb.append("今天是").append(reminder.getEvent())
                        .append("，")
                        .append(reminder.getAction());
                if (i != length - 1) {
                    sb.append("\n\n");
                }
            }
        }
        return sb.toString();
    }

    private List<Reminder> match(ReminderConfig config, List<ReminderConfig.Days> days) {
        List<Reminder> reminderList = new ArrayList<>(config.getDays().size());
        for (ReminderConfig.Days dayInfo : days) {
            final Date configDate = TimeUtils.parseConfigDayTime(dayInfo.getTime());
            if (TimeUtils.isToday(configDate)) {
                final Reminder reminder = new Reminder();
                reminder.setEvent(dayInfo.getEvent());
                reminder.setAction(dayInfo.getAction());
                reminderList.add(reminder);
            }
        }
        return reminderList;
    }

    /**
     * 闰年的 2 月 29 日加一年是 28 日
     * @return 所有纪念日时间
     * @param config
     */
    private List<ReminderConfig.Days> applyTimeSchedule(ReminderConfig config) {
        List<ReminderConfig.Days> tempDayConfig = new ArrayList<>();
        for (ReminderConfig.Days dayInfo : config.getDays()) {
            final Date date = TimeUtils.parseConfigDayTime(dayInfo.getTime());

            for (ReminderConfig.DaysRecord daysRecord : config.getDaysRecords()) {
                Date tempDate = date;
                String event = dayInfo.getEvent();
                switch (daysRecord.getUnit()) {
                    case "day":
                        tempDate = TimeUtils.addRange(date, daysRecord.getAmount(), Calendar.DAY_OF_YEAR);
                        event = dayInfo.getEvent() + "第 " + daysRecord.getAmount() + " 天";
                        break;
                    case "month":
                        tempDate = TimeUtils.addRange(date, daysRecord.getAmount(), Calendar.MONTH);
                        event = dayInfo.getEvent() + "第 " + daysRecord.getAmount() + " 月";
                        break;
                    case "year":
                        tempDate = TimeUtils.addRange(date, daysRecord.getAmount(), Calendar.YEAR);
                        event = dayInfo.getEvent() + "第 " + daysRecord.getAmount() + " 年";
                        break;
                }
                final ReminderConfig.Days element = new ReminderConfig.Days();
                element.setEvent(event);
                element.setAction(dayInfo.getAction());
                element.setTime(TimeUtils.formatConfigDayTime(tempDate.getTime()));
                tempDayConfig.add(element);
            }
        }
        return tempDayConfig;
    }

    @Override
    public void storeMetaInfo() {

    }

    @Override
    public Path getStoreFilePath() {
        return null;
    }

    @Override
    public Path getPreviewFilePath() {
        return null;
    }

    @Override
    public boolean isSync() {
        return false;
    }

    @Override
    public String name() {
        return Entrance.ACTION_DAY_REMINDER;
    }

    @Override
    public int priority() {
        return 10;
    }
}
