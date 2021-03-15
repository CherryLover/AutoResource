package me.monster.auto.resource.bean;

import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/11 11:10 上午
 */
public class RunConfig {
    private String run;
    private List<String> with;
    private BingConfig bingConfig;
    private UnsplashConfig unsplashConfig;

    static class BingConfig {
        private String endPoint;
        private String ossKey;
        private String ossSecret;
        private String bucketName;
        private String dir;
    }

    static class UnsplashConfig {
        private String unsplashKey;
        private String endPoint;
        private String ossKey;
        private String ossSecret;
        private String bucketName;
        private String dir;
    }

}

