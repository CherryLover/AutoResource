package me.monster.auto.resource.bean;

import me.monster.auto.resource.tool.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MdInfo {
    private String fileTitle;
    private List<String> tableTitle;
    private String updateTime;
    private List<MdInfoItem> infoItemList;

    public void setFileTitle(String title) {
        fileTitle = title;
    }

    public void setTableTitle(String... titles) {
        if (tableTitle == null) {
            tableTitle = new ArrayList<>();
        }
        tableTitle.clear();
        tableTitle.addAll(Arrays.asList(titles));
    }

    public void appendItem(MdInfoItem infoItem) {
        if (infoItemList == null) {
            infoItemList = new ArrayList<>();
        }
        infoItemList.add(infoItem);
    }

    public List<String> getTableTitle() {
        return tableTitle == null ? tableTitle = new ArrayList<>() : tableTitle;
    }

    public String getFileTitle() {
        return fileTitle == null ? fileTitle = "" : fileTitle;
    }

    public List<MdInfoItem> getInfoItemList() {
        return infoItemList == null ? infoItemList = new ArrayList<>() : infoItemList;
    }

    public void refresh() {
        updateTime = TimeUtils.currentTime();
    }

    public String getUpdateTime() {
        return updateTime == null ? updateTime = TimeUtils.currentTime() : updateTime;
    }

    public static class MdInfoItem {
        private String date;
        private String content;
        // 暂无用
        private Map<String, String> extraMap;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content.replace("|", "-");
        }

        public Map<String, String> getExtraMap() {
            return extraMap;
        }

        public void setExtraMap(Map<String, String> extraMap) {
            this.extraMap = extraMap;
        }
    }
}
