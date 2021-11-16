package me.monster.auto.resource.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.monster.auto.resource.bean.NotificationVo;
import me.monster.auto.resource.bean.RunConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationHelper {
    public static final String base_url = "https://api.telegram.org/";
    public static final String path_send_message = "/sendMessage";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final RunConfig.TelegramConfig telegramConfig = DataHolder.getInstance().getRunConfig().getTelegram();

    private final TelegramNotificationList telegramNotificationListInstance;

    private void trySendNotification() {
        if (!telegramConfig.isAvailable()) {
            return;
        }
        if (telegramNotificationListInstance.isEmpty()) {
            return;
        }
        final NotificationVo notification = telegramNotificationListInstance.getNotification();
        if (notification == null) {
            return;
        }
        sendNotification(notification);
    }

    public NotificationHelper() {
        telegramNotificationListInstance = TelegramNotificationList.getInstance();
        TelegramNotificationList.OnStartListener onStartListener = this::trySendNotification;
        telegramNotificationListInstance.setOnStartListener(onStartListener);
    }

    private void sendNotification(NotificationVo notificationVo) {
        Map<String, String> params = new HashMap<>();
        params.put("chat_id", telegramConfig.getChatId());
        params.put("text", notificationVo.getText());
        params.put("reply_markup", generateReplyMarkUp(notificationVo.getSource(), notificationVo.getAttachmentUrl(), notificationVo.getAttachmentFileName()));
        try {
            String httpContent = HttpUtils.getPostResponse(generateSendMessageUrl(), params);
            System.out.println("post notification response " + httpContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        trySendNotification();
    }

    private String generateSendMessageUrl() {
        return base_url + telegramConfig.getBotToken() + path_send_message;
    }

    private String generateReplyMarkUp(String type, String saveImgUrl, String fileName) {
        Map<String, Object> markup = new HashMap<>();
        Map<String, Object> button = new HashMap<>();
        button.put("text", "保存");
        button.put("url", generateNotificationCallbackUrl(saveImgUrl, type, fileName));
        List<Map<String, Object>> buttons = new ArrayList<>();
        buttons.add(button);
        List<List<Map<String, Object>>> wrapperList = new ArrayList<>();
        wrapperList.add(buttons);
        markup.put("inline_keyboard", wrapperList);
        return gson.toJson(markup);
    }

    private String generateNotificationCallbackUrl(String imgUrl, String type, String fileName) {
        return telegramConfig.getCallbackHttpProtocol() +
                "://" +
                telegramConfig.getCallbackUrlHost() +
                telegramConfig.getCallbackUrlPath() +
                "?" +
                "imgUrl=" +
                imgUrl +
                "&" +
                "type=" +
                type +
                "&" +
                "fileName=" +
                fileName;
    }
}
