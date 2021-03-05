package me.monster.auto.resource.bean;

import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 3:56 下午
 */
public class RspBingList {
    private List<RspBingImgEle> images;

    public RspBingImgEle getLast() {
        if (images == null || images.isEmpty()) {
            return new RspBingImgEle();
        }
        return images.get(0);
    }
}
