package me.monster.auto.resource;

import me.monster.auto.resource.action.AutoAction;
import me.monster.auto.resource.action.BingAutoAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 5:54 下午
 */
public class Entrance {

    public static final String ACTION_BING = "Bing";

    // args order: endPoint ossKey ossSecret bucketName dir actions{bing, unsplash}
    public static void main(String[] args) {
        if (args == null || args.length != 6) {
            throw new IllegalArgumentException("no args");
        }

        String endPoint = args[0];
        String ossKey = args[1];
        String ossSecret = args[2];
        String bucketName = args[3];
        String dir = args[4];
        String action = args[5];

        System.out.println("endPoint " + endPoint + " action " + action);

        Map<String, AutoAction> actionMap = new HashMap<>();
        actionMap.put(ACTION_BING, new BingAutoAction());
        AutoAction autoAction = actionMap.get(action);

        if (autoAction == null) {
            throw new IllegalArgumentException("no " + action + " AutoAction implement");
        }
        autoAction.setup(endPoint, ossKey, ossSecret, bucketName, dir);
        autoAction.fetchInfo();
        autoAction.storeMetaInfo();
    }
}
