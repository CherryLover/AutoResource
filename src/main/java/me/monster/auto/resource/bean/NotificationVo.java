package me.monster.auto.resource.bean;

public class NotificationVo {
    /**
     * 通知需要展示的文本
     */
    private final String text;

    /**
     * 附件 URL
     */
    private String attachmentUrl;
    private String attachmentFileName;
    /**
     * 通知来源：Bing、Unsplash
     */
    private String source;

    public NotificationVo(String text) {
        this.text = text;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public String getAttachmentUrl() {
        return attachmentUrl == null ? "" : attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentFileName() {
        return attachmentFileName == null ? "" : attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public String getSource() {
        return source == null ? "" : source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "NotificationVo{" +
                "text='" + text + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", attachmentFileName='" + attachmentFileName + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
