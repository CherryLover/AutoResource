package me.monster.auto.resource.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 3:56 下午
 */
public class RspBingVo {
    private List<RspBingImgEle> images;

    public RspBingImgEle getLast() {
        if (images == null || images.isEmpty()) {
            return new RspBingImgEle();
        }
        return images.get(0);
    }

    public List<RspBingImgEle> getImages() {
        return images == null ? images = new ArrayList<>() : images;
    }

    public static class RspBingImgEle {
        private String startdate;
        private String fullstartdate;
        private String enddate;
        private String url;
        private String urlbase;
        private String copyright;
        private String copyrightlink;
        private String title;

        public String getStartdate() {
            return startdate == null ? "" : startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate == null ? "" : fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate == null ? "" : enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase == null ? "" : urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright == null ? "" : copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink == null ? "" : copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
