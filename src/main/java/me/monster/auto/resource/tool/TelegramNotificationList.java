package me.monster.auto.resource.tool;

import me.monster.auto.resource.bean.NotificationVo;

import java.util.LinkedList;

public class TelegramNotificationList {
    private final LinkedList<NotificationVo> notificationTextList;
    private OnStartListener mOnStartListener;

    private TelegramNotificationList() {
        notificationTextList = new LinkedList<>();
    }

    public static TelegramNotificationList getInstance() {
        return Holder.holder;
    }

    public void addNotification(NotificationVo notification) {
        System.out.println("add notification " + notification.toString());
        boolean invokeStar = isEmpty();
        notificationTextList.offer(notification);
        if (invokeStar) {
            if (mOnStartListener != null) {
                mOnStartListener.onStart();
            }
        }
    }

    public NotificationVo getNotification() {
        final NotificationVo poll = notificationTextList.poll();
        System.out.println("get notification " + poll);
        return poll;
    }

    public boolean isEmpty() {
        return notificationTextList.isEmpty();
    }

    public void setOnStartListener(OnStartListener mOnStartListener) {
        this.mOnStartListener = mOnStartListener;
    }

    private static class Holder {
        private static final TelegramNotificationList holder = new TelegramNotificationList();
    }

    public interface OnStartListener {
        void onStart();
    }
}
