package me.monster.auto.resource;

import com.google.gson.Gson;
import me.monster.auto.resource.action.*;
import me.monster.auto.resource.bean.RunConfig;
import me.monster.auto.resource.tool.DataHolder;
import me.monster.auto.resource.tool.FileUtils;
import me.monster.auto.resource.tool.NotificationHelper;
import me.monster.auto.resource.tool.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 5:54 下午
 */
public class Entrance {

    public static final String ACTION_BING = "Bing";
    public static final String ACTION_UNSPLASH = "Unsplash";
    public static final String ACTION_DAY_REMINDER = "DAY_REMINDER";
    public static final String ACTION_LOVELY = "LOVELY";
    public static final String ACTION_GEEK_NEWS = "GEEK_READ";

    private static final String[] ALL_ACTION = {ACTION_BING, ACTION_UNSPLASH, ACTION_DAY_REMINDER, ACTION_LOVELY, ACTION_GEEK_NEWS};

    // args order: ossKey ossSecret unsplash_client_id telegram_ChatId telegram_botToken 服务器地址
    public static void main(String[] args) {
        System.out.println("args size: " + args.length);
        for (String arg : args) {
            System.out.print(" " + arg + " ");
        }
        List<String> actionList = new ArrayList<>();
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
                    case 6:
                        actionList.addAll(applyRunAction(args[i]));
                        break;
                }
            }
            DataHolder.getInstance().setRunConfig(runConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (actionList.isEmpty()) {
            System.err.println("no action to run");
            System.exit(0);
        }

        final NotificationHelper notificationHelper = new NotificationHelper();
        System.out.println("init notification helper");

        Map<String, AutoAction> actionMap = new HashMap<>();
        actionMap.put(ACTION_BING, new BingAutoAction());
        actionMap.put(ACTION_UNSPLASH, new UnsplashImgAutoAction());
        actionMap.put(ACTION_DAY_REMINDER, new DayReminderAction());
        actionMap.put(ACTION_LOVELY, new LovelyAction());
        actionMap.put(ACTION_GEEK_NEWS, new GeekReadInfoAction());


        final List<AutoAction> runnerList = new ArrayList<>(actionList.size());
        for (String action : actionList) {
            AutoAction autoAction = actionMap.get(action);

            if (autoAction == null) {
                throw new IllegalArgumentException("no " + action + " AutoAction implement");
            }
            runnerList.add(autoAction);
        }
        Collections.sort(runnerList, (o1, o2) -> Integer.compare(o1.priority(), o2.priority()));
        for (AutoAction autoAction : runnerList) {
            System.out.println("now execute " + autoAction.name());
            autoAction.fetchInfo();
            if (!autoAction.isSync()) {
                autoAction.storeMetaInfo();
            }
        }
    }

    private static List<String> applyRunAction(String actionPosition) {
        List<String> actions = new ArrayList<>();
        final String[] split = actionPosition.split(",");
        for (String s : split) {
            final int position = StringUtils.parseInt(s, -1);
            if (position >= 0 && position < ALL_ACTION.length) {
                actions.add(ALL_ACTION[position]);
            }
        }
        return actions;
    }
}
