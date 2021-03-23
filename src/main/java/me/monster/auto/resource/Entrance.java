package me.monster.auto.resource;

import com.google.gson.Gson;
import me.monster.auto.resource.action.AutoAction;
import me.monster.auto.resource.action.BingAutoAction;
import me.monster.auto.resource.bean.RunConfig;

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

    // args order: ossKey ossSecret unsplash_client_id
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
                }
            }
            DataHolder.getInstance().setRunConfig(runConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> actionList = new ArrayList<>();
        actionList.add(ACTION_BING);

        Map<String, AutoAction> actionMap = new HashMap<>();
        actionMap.put(ACTION_BING, new BingAutoAction());

        for (String action : actionList) {
            AutoAction autoAction = actionMap.get(action);

            if (autoAction == null) {
                throw new IllegalArgumentException("no " + action + " AutoAction implement");
            }
            if (autoAction.isSync()) {

            } else {
                autoAction.fetchInfo();
                autoAction.storeMetaInfo();
            }
        }

    }
}
