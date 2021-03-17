package me.monster.auto.resource.bean;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/7 9:40 下午
 */
public class MdImage {
    public static final int SOURCE_BING = 743;
    public static final int SOURCE_UNSPLASH = 291;

    private final String desc;
    private final String date;
    private final String url;
    private final int imgSource;

    private String unsplashConvertImg;

    public MdImage(int imgSource, String desc, String date, String url) {
        this.imgSource = imgSource;
        this.desc = desc;
        this.date = date;
        this.url = url;
    }

    public String getLargeFormat() {
        if (imgSource == SOURCE_BING) {
            String smallUrl = url + "&w=1000";
            return String.format("![%s](%s)Today: [%s](%s)", desc, smallUrl, desc, url);
        } else if (imgSource == SOURCE_UNSPLASH) {
            return unsplashConvertImg;
        }
        return url;
    }

    public void setUnsplashConvertImg(String unsplashConvertImg) {
        this.unsplashConvertImg = unsplashConvertImg;
    }

    @Override
    public String toString() {
        String smallUrl = url;
        if (imgSource == SOURCE_BING) {
            smallUrl = url + "&pid=hp&w=384&h=216&rs=1&c=4";
        } else if (imgSource == SOURCE_UNSPLASH) {
            return url + desc;
        }
        return String.format("![%s](%s) <br />%s [Download](%s)", desc, smallUrl, date, url);
    }
}
