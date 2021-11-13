package me.monster.auto.resource.tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NotificationHelper {
    public static final String base_url = "https://api.telegram.org/";
    public static final String path_send_message = "/sendMessage";

    private final TelegramNotificationList telegramNotificationListInstance;
    private final TelegramNotificationList.OnStartListener onStartListener = new TelegramNotificationList.OnStartListener() {
        @Override
        public void onStart() {
            trySendNotification();
        }
    };

    private void trySendNotification() {
        if (telegramNotificationListInstance.isEmpty()) {
            return;
        }
        sendNotification(telegramNotificationListInstance.getNotification());
    }

    public NotificationHelper() {
        telegramNotificationListInstance = TelegramNotificationList.getInstance();
        telegramNotificationListInstance.setOnStartListener(onStartListener);
    }

    private void sendNotification(String text) {
        Map<String, String> params = new HashMap<>();
        params.put("chat_id", "xxx");
        params.put("text", text);
        try {
            String httpContent = HttpUtils.getPostResponse(generateSendMessageUrl(), params);
            System.out.println("post notification response " + httpContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        trySendNotification();
    }

    private String generateSendMessageUrl() {
        return base_url + "xxx" + path_send_message;
    }
}
