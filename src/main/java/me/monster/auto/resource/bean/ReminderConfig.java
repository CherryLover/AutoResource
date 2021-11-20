package me.monster.auto.resource.bean;

import com.google.gson.annotations.SerializedName;
import me.monster.auto.resource.tool.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReminderConfig {
    private List<Days> days;
    @SerializedName("days_record")
    private List<DaysRecord> daysRecords;

    public List<Days> getDays() {
        return days == null ? days = new ArrayList<>() : days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }

    public List<DaysRecord> getDaysRecords() {
        return daysRecords == null ? daysRecords = new ArrayList<>() : daysRecords;
    }

    public void setDaysRecords(List<DaysRecord> daysRecords) {
        this.daysRecords = daysRecords;
    }

    public static class Notification {
        private String email;
        private String calendar;

        public String getEmail() {
            return email == null ? "" : email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCalendar() {
            return calendar == null ? "" : calendar;
        }

        public void setCalendar(String calendar) {
            this.calendar = calendar;
        }
    }

    public static class Sender {
        private String email;

        public String getEmail() {
            return email == null ? "" : email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class Days {
        /**
         * 纪念日
         */
        private String event;
        /**
         * 某天
         */
        private String time;
        /**
         * 需要做什么
         */
        private String action;

        public String getTime() {
            return time == null ? "" : time;
        }

        public Date getConvertDate() {
            return TimeUtils.parseConfigDayTime(getTime());
        }

        public void setTime(String time) {
            this.time = time;
        }

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

    public static class DaysRecord {
        private int amount;
        private String unit;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit == null ? "" : unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}