package me.monster.auto.resource.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/15 4:05 下午
 */
public class UnsplashImageVo {

    private List<ImgVo> imgs;
    private String update;

    public List<ImgVo> getImgs() {
        return imgs == null ? imgs = new ArrayList<>() : imgs;
    }

    public void setImgs(List<ImgVo> imgs) {
        this.imgs = imgs;
    }

    public void insertImg(ImgVo imgVo) {
        getImgs().add(0, imgVo);
    }

    public void insertAllImg(List<ImgVo> imgs) {
        for (int i = imgs.size() - 1; i >= 0; i--) {
            ImgVo imgVo = imgs.get(i);
            insertImg(imgVo);
        }
    }

    public void addImage(ImgVo vo) {
        getImgs().add(vo);
    }

    public void update() {
        this.update = String.valueOf(System.currentTimeMillis());
    }

    public static class ImgVo {
        private String orientation;
        private String date;

        private int view;
        private int download;
        private int like;

        private int width;
        private int height;
        private String color;
        private String desc;

        private String userName;
        private String userPage;
        private String imgPage;

        private RspUnsplash.UrlsBean imgs;

        public String getOrientation() {
            return orientation;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getDownload() {
            return download;
        }

        public void setDownload(int download) {
            this.download = download;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getColor() {
            return color == null ? "" : color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUserName() {
            return userName == null ? "" : userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPage() {
            return userPage == null ? "" : userPage;
        }

        public void setUserPage(String userPage) {
            this.userPage = userPage;
        }

        public String getImgPage() {
            return imgPage == null ? "" : imgPage;
        }

        public void setImgPage(String imgPage) {
            this.imgPage = imgPage;
        }

        public RspUnsplash.UrlsBean getImgs() {
            return imgs;
        }

        public void setImgs(RspUnsplash.UrlsBean imgs) {
            this.imgs = imgs;
        }

        public String getShareInfoMdString() {
            return "<br />" + date + "<br />" + "下载：" +
                    download +
                    " 喜欢：" +
                    like +
                    "<br />" +
                    shareInfo();
        }

        private String shareInfo() {
            return "图片来自 " +
                    "[Unsplash]" + "(" + getImgPage() + ")" +
                    " 的 " + "[" + getUserName() + "]" + "(" + getUserPage() + ")";
        }

        public String getPreviewImageMdString() {
            return "![" + getDesc() + "]" +
                    "(" +
                    getImgs().getThumb() +
                    ")";
        }

        public String getCoverImgMdString() {
            return "![" + getDesc() + "]" +
                    "(" +
                    getImgs().getRegular() +
                    ")" + shareInfo();
        }


        public String getMdInfo() {
            return getPreviewImageMdString() + "<br />" + getShareInfoMdString();
        }
    }
}
