package me.monster.auto.resource.tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.monster.auto.resource.bean.RspUnsplash;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

public class UnsplashExecutor {
    public static final long DELAY_TIME = 30 * 1000;

    private final Map<String, RspUnsplash> allStoreMap;
    private OnResourceStoreListener mAllListener;

    public UnsplashExecutor() {
        allStoreMap = new HashMap<>();
    }

    public static UnsplashExecutor getInstance() {
        return InnerHolder.instance;
    }

    public void getContent(String... urls) {
        final ThreadPoolExecutor unsplashExecutor = ExecutorTool.getInstance().getUnsplashExecutor();
        Timer resourceGetTimeDown = new Timer("ResourceGetTimeDown");
        for (String url : urls) {
            UnsplashRequestProcessorRunnable runnable = new UnsplashRequestProcessorRunnable(url, allStoreMap);
            runnable.setListener(new OnResourceStoreListener() {
                @Override
                public void onStore(Map<String, RspUnsplash> store) {
                    allStoreMap.putAll(store);
                    if (allStoreMap.size() == urls.length) {
                        System.out.println("finished " + store.keySet().toString());
                        resourceGetTimeDown.cancel();
                        finishGetData(unsplashExecutor);
                    }
                }
            });
            unsplashExecutor.execute(runnable);
        }
        resourceGetTimeDown.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("--- Timer Down ---");
                finishGetData(unsplashExecutor);
            }
        }, DELAY_TIME);
    }

    private void finishGetData(ThreadPoolExecutor unsplashExecutor) {
        unsplashExecutor.shutdown();
        if (mAllListener != null) {
            mAllListener.onStore(allStoreMap);
        }
    }

    public UnsplashExecutor setStoreListener(OnResourceStoreListener mListener) {
        this.mAllListener = mListener;
        return this;
    }

    public interface OnResourceStoreListener {
        void onStore(Map<String, RspUnsplash> store);
    }

    private static class InnerHolder {
        private static final UnsplashExecutor instance = new UnsplashExecutor();
    }

    private static class UnsplashRequestProcessorRunnable implements Runnable {
        private final String url;
        private final Map<String, RspUnsplash> storeMap;
        private final String keyType;
        private OnResourceStoreListener mListener;

        public UnsplashRequestProcessorRunnable(String url, Map<String, RspUnsplash> storeMap) {
            this.url = url;
            this.keyType = parseUrl();
            this.storeMap = storeMap;
        }

        public void setListener(OnResourceStoreListener mListener) {
            this.mListener = mListener;
        }

        private String parseUrl() {
            String[] split = url.split("[?]");
            String args = split[1];
            String[] querys = args.split("&");
            String orientation = "";
            for (String query : querys) {
                if (query.startsWith("orientation") || query.startsWith("?orientation")) {
                    orientation = query.split("=")[1];
                    break;
                }
            }
            return orientation;
        }

        @Override
        public void run() {
            try {
                System.out.println("prepare request " + url + " for " + keyType);
                String httpContent = HttpUtils.getHttpContent(url);
                Type type = new TypeToken<ArrayList<RspUnsplash>>() {
                }.getType();
                List<RspUnsplash> unsplashList = new Gson().fromJson(httpContent, type);
                if (unsplashList == null || unsplashList.isEmpty()) {
                    System.err.println(url + " response has no data for " + keyType);
                } else {
                    System.out.println("request " + url + " has response save for " + keyType);
                    storeMap.put(keyType, unsplashList.get(0));
                    if (mListener != null) {
                        mListener.onStore(storeMap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
