package me.monster.auto.resource.bean;

import java.io.Serializable;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 3:19 下午
 */
public class BingImageElement implements Serializable {
    public String url = "";
    public String endDate = "";
    public String copyright = "";
    public String copyRightLink;

    public String fileName = "";
    public String ossPath = "";

    public BingImageElement(String url, String endDate, String copyright) {
        this.url = url;
        this.endDate = endDate;
        this.copyright = copyright;
    }



}
