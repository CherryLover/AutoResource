package me.monster.auto.resource.bean;

import java.util.ArrayList;
import java.util.List;

public class RspGeekHotList {
    public static final String STATUS_SUCCESS = "SUCCESS";
    private int statusCode;
    private String message;
    private List<GeekInfoBean> data;

    public boolean isSuccess() {
        return statusCode == 200 && message.equals(STATUS_SUCCESS);
    }

    public List<GeekInfoBean> getData() {
        return data == null ? new ArrayList<>() : data;
    }

    public static class GeekInfoBean {

        private ArrayList<String> authors;
        private String language;
        private long likeCount;
        private float score;
        private long viewCount;
        private ArrayList<TopicBean> topics;
        private String _id;
        // 封面图
        private String cover;
        // 网站标题
        private String title;
        // 内容链接
        private String url;
        // 内容网站
        private String siteName;
        // 发布时间 "2021-03-26T15:01:00.000Z"
        private String publishedAt;

        public String getAuthorFormat() {
            if (authors == null || authors.isEmpty()) {
                return "";
            }
            if (authors.size() == 1) {
                return authors.get(0);
            }
            StringBuilder sb = new StringBuilder();
            for (String author : authors) {
                sb.append(author);
                if (authors.indexOf(author) != authors.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }

        public ArrayList<String> getAuthors() {
            return authors;
        }

        public long getLikeCount() {
            return likeCount;
        }

        public float getScore() {
            return score;
        }

        public ArrayList<TopicBean> getTopics() {
            return topics;
        }

        public void setTopics(ArrayList<TopicBean> topics) {
            this.topics = topics;
        }

        public long getViewCount() {
            return viewCount;
        }

        public void setViewCount(long viewCount) {
            this.viewCount = viewCount;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        @Override
        public String toString() {
            return "GeekInfoBean{" +
                    "authors=" + authors +
                    ", language='" + language + '\'' +
                    ", likeCount=" + likeCount +
                    ", score=" + score +
                    ", topics=" + topics +
                    ", viewCount=" + viewCount +
                    ", _id='" + _id + '\'' +
                    ", cover='" + cover + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", siteName='" + siteName + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    '}';
        }
    }



    public static class TopicBean {
        private String _id;
        private String name;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "TopicBean{" +
                    "_id='" + _id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
