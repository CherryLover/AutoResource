package me.monster.auto.resource.bean;

public class NotificationVo {
    public static final String SOURCE_BING = "bing";
    public static final String SOURCE_UNSPLASH = "unsplash";
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

    public NotificationVo(String text, String source) {
        this.text = text;
        setSource(source);
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

    private void setSource(String source) {
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