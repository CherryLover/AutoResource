package me.monster.auto.resource.bean;

import me.monster.auto.resource.tool.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Geek 阅读存储的阅读消息列表
 */
public class GeekStore {

    public static final int MAX_COUNT = 500;

    private List<GeekStoreBean> infoList;
    private long update;

    public void addInfo(GeekStoreBean info) {
        if (this.infoList == null) {
            this.infoList = new ArrayList<>();
        }
        this.infoList.add(info);
    }

    public List<GeekStoreBean> getInfoList() {
        return infoList == null ? infoList = new ArrayList<>() : infoList;
    }

    public void refreshTime() {
        this.update = System.currentTimeMillis();
    }

    public void addNewInfo(GeekStore newGeekInfo) {
        if (newGeekInfo != null) {
            for (GeekStoreBean geekStoreBean : newGeekInfo.getInfoList()) {
                getInfoList().add(geekStoreBean);
            }
        }
        getInfoList().sort(Comparator.comparingLong(o -> o.timestamp));
        if (getInfoList().size() > MAX_COUNT) {
            getInfoList().subList(0, getInfoList().size() - MAX_COUNT).clear();
        }
        Collections.reverse(getInfoList());
        refreshTime();
    }

    public static class GeekStoreBean {
        // 获取当前消息的时间戳
        private long timestamp;

        // 封面图
        private String cover;
        // 网站标题
        private String title;
        // 内容链接
        private String url;
        // 内容网站
        private String siteName;
        // 文章发布时间 "2021-03-26T15:01:00.000Z"
        private String publishedAt;
        private ArrayList<String> authors;
        // 话题
        private ArrayList<String> topics;

        private long viewCount;
        private long likeCount;
        private float score;

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public void addAuthor(String author) {
            if (authors == null) {
                authors = new ArrayList<>();
            }
            authors.add(author);
        }

        public void addTopic(String topic) {
            if (topics == null) {
                topics = new ArrayList<>();
            }
            topics.add(topic);
        }

        public void setViewCount(long viewCount) {
            this.viewCount = viewCount;
        }

        public void setLikeCount(long likeCount) {
            this.likeCount = likeCount;
        }

        public void setScore(float score) {
            this.score = score;
        }

        private String getSiteName() {
            return siteName == null ? siteName = "" : siteName;
        }

        private String getFormatAuthor() {
            if (authors == null) {
                return "";
            } else {
                return String.join(" ", authors);
            }
        }

        private String getTitle() {
            return title == null ? title = "" : title;
        }

        private String getUrl() {
            return url == null ? url = "" : url;
        }

        private String getFormatTopic() {
            return String.join(" ", topics);
        }

        private ArrayList<String> getTopics() {
            return topics == null ? topics = new ArrayList<>() : topics;
        }

        private long getViewCount() {
            return viewCount;
        }

        private long getLikeCount() {
            return likeCount;
        }

        public String getPreviewContent() {
            String content = "";
            if (StringUtils.isNotEmpty(cover)) {
                content = "封面图" + "<br />" + "![" + getTitle() + "]" + "(" + cover + ")";
            }
            content += "文章来自 " + getSiteName() + " 的 " + getFormatAuthor() + " [" + getTitle() + "](" + getUrl() + ")";
            if (getTopics().size() > 0) {
                String topic = "涉及话题：" + getFormatTopic();
                content = content + "<br />" + topic;
            }
            String analyze = "阅读数：" + getViewCount() + " 点赞数：" + getLikeCount();
            content = content + "<br />" + analyze;
            return content;
        }
   }
}
