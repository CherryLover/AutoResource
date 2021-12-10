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
    private TelegramConfig Telegram;
    private LoveyConfig Lovely;

    public BingConfig getBing() {
        return Bing;
    }

    public UnsplashConfig getUnsplash() {
        return Unsplash;
    }

    public AliOssConfig getAliOss() {
        return AliOss;
    }

    public TelegramConfig getTelegram() {
        return Telegram;
    }

    public LoveyConfig getLovely() {
        return Lovely;
    }

    public static class BingConfig {
        private boolean saveOss;
        private boolean saveMarkdown;

        public boolean isSaveOss() {
            return saveOss;
        }

        public boolean isSaveMarkdown() {
            return saveMarkdown;
        }
    }

    public static class UnsplashConfig {
        private String clientId;
        private boolean saveMarkdown;

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public boolean isSaveMarkdown() {
            return saveMarkdown;
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

    public static class TelegramConfig {
        private String chatId;
        private String botToken;
        private boolean send = false;
        /**
         * http or https
         */
        private String callbackHttpProtocol = "http";
        /**
         * 通知响应的回调域名，不带 http 协议
         */
        private String callbackUrlHost;
        /**
         * 通知响应的回调 path，不带 host
         */
        private String callbackUrlPath;

        public String getChatId() {
            return chatId == null ? "" : chatId;
        }

        public void setChatId(String chatId) {
            this.chatId = chatId;
        }

        public String getBotToken() {
            return botToken == null ? "" : botToken;
        }

        public void setBotToken(String botToken) {
            this.botToken = botToken;
        }

        public boolean isSend() {
            return send;
        }

        public void setSend(boolean send) {
            this.send = send;
        }

        public String getCallbackHttpProtocol() {
            return callbackHttpProtocol == null ? "" : callbackHttpProtocol;
        }

        public void setCallbackHttpProtocol(String callbackHttpProtocol) {
            this.callbackHttpProtocol = callbackHttpProtocol;
        }

        public String getCallbackUrlHost() {
            return callbackUrlHost == null ? "" : callbackUrlHost;
        }

        public void setCallbackUrlHost(String callbackUrlHost) {
            this.callbackUrlHost = callbackUrlHost;
        }

        public String getCallbackUrlPath() {
            return callbackUrlPath == null ? "" : callbackUrlPath;
        }

        public void setCallbackUrlPath(String callbackUrlPath) {
            this.callbackUrlPath = callbackUrlPath;
        }

        public boolean isAvailable() {
            return send &&
                    StringUtils.isNotEmpty(chatId) &&
                    StringUtils.isNotEmpty(botToken) &&
                    StringUtils.isNotEmpty(callbackUrlHost) &&
                    StringUtils.isNotEmpty(callbackUrlPath);
        }
    }

    public static class LoveyConfig {
        private int randomChoice = 3;

        public int getRandomChoice() {
            return randomChoice;
        }

        public void setRandomChoice(int randomChoice) {
            this.randomChoice = randomChoice;
        }
    }
}

