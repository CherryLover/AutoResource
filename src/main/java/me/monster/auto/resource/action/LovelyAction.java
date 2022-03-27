package me.monster.auto.resource.action;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.monster.auto.resource.bean.NotificationVo;
import me.monster.auto.resource.tool.FileUtils;
import me.monster.auto.resource.tool.Lists;
import me.monster.auto.resource.tool.TelegramNotificationList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LovelyAction implements AutoAction {

    @Override
    public void fetchInfo() {
        final URL resource = this.getClass().getClassLoader().getResource("lovely.json");
        if (resource != null) {
            String fileName = resource.getFile();
            if (fileName != null) {
                try {
                    String content = FileUtils.getContent(fileName);
                    Type type = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    List<String> lovelyList = new Gson().fromJson(content, type);
                    final String randomLovely = Lists.getRandom(lovelyList).replace("|", "\n");
                    final NotificationVo notification = new NotificationVo("小情话：\n" + randomLovely, NotificationVo.SOURCE_LOVELY);
                    TelegramNotificationList.getInstance().addNotification(notification);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void storeMetaInfo() {

    }

    @Override
    public Path getStoreFilePath() {
        return null;
    }

    @Override
    public Path getPreviewFilePath() {
        return null;
    }

    @Override
    public boolean isSync() {
        return false;
    }
}
