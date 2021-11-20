package me.monster.auto.resource.bean;

public class Reminder {
    /**
     * 遇见
     * 会自动拼接 第 x 天、月、年
     */
    private String event;
    /**
     * 睡前祝福
     */
    private String action;

    public String getEvent() {
        return event == null ? "" : event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getAction() {
        return action == null ? "" : action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
