package me.monster.auto.resource.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/7 5:18 下午
 */
public class BingImageVo {
    public static final int DAY_STORE_MAP_EXISTS = 1;
    private Map<String, Integer> dayStoreMap;
    private List<BingImageElement> dayInfoList;

    public boolean containDay(String endDate) {
        Integer integer = getDayStoreMap().get(endDate);
        return integer == DAY_STORE_MAP_EXISTS;
    }

    public void appendDay(String endDate) {
        getDayStoreMap().put(endDate, DAY_STORE_MAP_EXISTS);
    }

    private Map<String, Integer> getDayStoreMap() {
        return dayStoreMap == null ? dayStoreMap = new HashMap<>() : dayStoreMap;
    }

    private List<BingImageElement> getDayInfoList() {
        return dayInfoList == null ? dayInfoList = new ArrayList<>() : dayInfoList;
    }

    public void appendList(BingImageElement element) {
        getDayInfoList().add(element);
    }

    public static class BingImageElement {
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
}
