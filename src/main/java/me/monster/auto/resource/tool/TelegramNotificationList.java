package me.monster.auto.resource.tool;

import java.util.LinkedList;

public class TelegramNotificationList {
    private final LinkedList<String> notificationTextList;
    private OnStartListener mOnStartListener;

    private TelegramNotificationList() {
        notificationTextList = new LinkedList<>();
    }

    public static TelegramNotificationList getInstance() {
        return Holder.holder;
    }

    public void addNotification(String text) {
        System.out.println("add notification " + text);
        boolean invokeStar = isEmpty();
        notificationTextList.offer(text);
        if (invokeStar) {
            if (mOnStartListener != null) {
                mOnStartListener.onStart();
            }
        }
    }

    public String getNotification() {
        final String poll = notificationTextList.poll();
        System.out.println("get notification " + poll);
        return StringUtils.isEmpty(poll) ? "" : poll;
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
