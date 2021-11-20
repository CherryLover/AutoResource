package me.monster.auto.resource;

import com.google.gson.Gson;
import me.monster.auto.resource.action.AutoAction;
import me.monster.auto.resource.action.BingAutoAction;
import me.monster.auto.resource.action.DayReminderAction;
import me.monster.auto.resource.action.UnsplashImgAutoAction;
import me.monster.auto.resource.bean.RunConfig;
import me.monster.auto.resource.tool.DataHolder;
import me.monster.auto.resource.tool.FileUtils;
import me.monster.auto.resource.tool.NotificationHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 5:54 下午
 */
public class Entrance {

    public static final String ACTION_BING = "Bing";
    public static final String ACTION_UNSPLASH = "Unsplash";
    public static final String ACTION_DAY_REMINDER = "DAY_REMINDER";

    // args order: ossKey ossSecret unsplash_client_id telegram_ChatId telegram_botToken 服务器地址
    public static void main(String[] args) {
        try {
            String config = FileUtils.getContent("RunConfig.json");
            RunConfig runConfig = new Gson().fromJson(config, RunConfig.class);
            for (int i = 0; i < args.length; i++) {
                switch (i) {
                    case 0:
                        runConfig.getAliOss().setOssKey(args[i]);
                        break;
                    case 1:
                        runConfig.getAliOss().setOssSecret(args[i]);
                        break;
                    case 2:
                        runConfig.getUnsplash().setClientId(args[i]);
                        break;
                    case 3:
                        runConfig.getTelegram().setChatId(args[i]);
                        break;
                    case 4:
                        runConfig.getTelegram().setBotToken(args[i]);
                        break;
                    case 5:
                        runConfig.getTelegram().setCallbackUrlHost(args[i]);
                        break;
                }
            }
            DataHolder.getInstance().setRunConfig(runConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final NotificationHelper notificationHelper = new NotificationHelper();
        System.out.println("init notification helper");

        List<String> actionList = new ArrayList<>();
//        actionList.add(ACTION_BING);
//        actionList.add(ACTION_UNSPLASH);
        actionList.add(ACTION_DAY_REMINDER);

        Map<String, AutoAction> actionMap = new HashMap<>();
        actionMap.put(ACTION_BING, new BingAutoAction());
        actionMap.put(ACTION_UNSPLASH, new UnsplashImgAutoAction());
        actionMap.put(ACTION_DAY_REMINDER, new DayReminderAction());


        for (String action : actionList) {
            AutoAction autoAction = actionMap.get(action);

            if (autoAction == null) {
                throw new IllegalArgumentException("no " + action + " AutoAction implement");
            }
            autoAction.fetchInfo();
            if (!autoAction.isSync()) {
                autoAction.storeMetaInfo();
            }
        }

    }
}
