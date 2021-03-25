package me.monster.auto.resource.bean;

import me.monster.auto.resource.tool.StringUtils;

import java.util.Objects;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/11 11:10 上午
 */
public class RunConfig {
    private BingConfig Bing;
    private UnsplashConfig Unsplash;
    private AliOssConfig AliOss;

    public BingConfig getBing() {
        return Bing;
    }

    public UnsplashConfig getUnsplash() {
        return Unsplash;
    }

    public AliOssConfig getAliOss() {
        return AliOss;
    }

    public static class BingConfig {
        private boolean saveOss;


        public boolean isSaveOss() {
            return saveOss;
        }
    }

    public static class UnsplashConfig {
        private boolean saveOss;
        private String clientId;

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public boolean isSaveOss() {
            return saveOss;
        }

        public boolean isAvailable() {
            return StringUtils.isNotEmpty(clientId);
        }

        public String getClientId() {
            return clientId;
        }
    }

    public static class AliOssConfig {
        private String endPoint = null;
        private String ossKey = null;
        private String ossSecret = null;
        private String bucketName = null;
        private String ossSaveFolder = null;

        public String getEndPoint() {
            return endPoint;
        }

        public String getOssKey() {
            return ossKey;
        }

        public void setOssKey(String ossKey) {
            this.ossKey = ossKey;
        }

        public String getOssSecret() {
            return ossSecret;
        }

        public void setOssSecret(String ossSecret) {
            this.ossSecret = ossSecret;
        }

        public String getBucketName() {
            return bucketName;
        }

        public String getOssSaveFolder() {
            return ossSaveFolder;
        }

        public boolean isAvailable() {
            return StringUtils.isNotEmpty(endPoint)
                    && StringUtils.isNotEmpty(ossKey)
                    && StringUtils.isNotEmpty(ossSecret)
                    && StringUtils.isNotEmpty(bucketName)
                    && StringUtils.isNotEmpty(ossSaveFolder);
        }
    }
}

