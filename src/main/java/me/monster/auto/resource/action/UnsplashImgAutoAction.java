package me.monster.auto.resource.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.monster.auto.resource.FileUtils;
import me.monster.auto.resource.HttpUtils;
import me.monster.auto.resource.TimeUtils;
import me.monster.auto.resource.bean.MdImage;
import me.monster.auto.resource.bean.RspUnsplash;
import me.monster.auto.resource.bean.UnsplashImageVo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @description 每日自动存储 Unsplash 图片信息
 * @author: Created jiangjiwei in 2021/3/15 2:48 下午
 */
public class UnsplashImgAutoAction implements AutoAction {

    public static final String URL = "https://api.unsplash.com/photos/random";
    public static final String URL_ARGS_COUNT = "count";
    public static final String URL_ARGS_ORIENTATION = "orientation";
    public static final String URL_ARGS_QUERY = "query";
    public static final String URL_ARGS_CLIENT_ID = "client_id";
    public static final String[] ORIENTATION_ITEMS = {"landscape", "portrait", "squarish"};
    public static final String[] QUERY_ITEMS = {"wallpapers", "nature", "people", "architecture", "current-events", "fashion", "film", "travel", "textures-patterns", "animals", "food-drink"};
    public static final String UNSPLASH_TITLE = "Unsplash Images";
    public static final String[] MD_PREVIEW_TABLE_TITLE = {"Landscape", "Portrait", "Squarish"};
    private final Map<String, String> queryMap;
    private RspUnsplash mRspUnsplash;

    public UnsplashImgAutoAction() {
        this.queryMap = new HashMap<>();
        queryMap.put(URL_ARGS_COUNT, "1");
        queryMap.put(URL_ARGS_ORIENTATION, getOrientation());
        queryMap.put(URL_ARGS_QUERY, getQueryInfo());
        // TODO-JiangJiweiFeat: 2021/3/15 添加 Client_id

    }

    private String getQueryInfo() {
        int i = Math.abs(new Random(System.nanoTime()).nextInt());
        return QUERY_ITEMS[i % QUERY_ITEMS.length];
    }

    private String getOrientation() {
        int i = Math.abs(new Random(System.nanoTime()).nextInt());
        return ORIENTATION_ITEMS[i % ORIENTATION_ITEMS.length];
    }

    @Override
    public void setup(String endPoint, String ossKey, String ossSecret, String bucketName, String dir) {

    }

    // TODO-JiangJiweiFeat: 2021/3/17 使用线程池同步发起 3 个网络请求，并设置开启独立线程进行计时 60s，超过时间自动停止
    // TODO-JiangJiweiFeat: 2021/3/17 3 个网络请求完成后手动停止计时线程
    // TODO-JiangJiweiFeat: 2021/3/17 请求内容：横向、竖向、方形，如果为 empty 或 null 设置默认的图片并标注
    // TODO-JiangJiweiFeat: 2021/3/17 UNsplash readme 表头更新，json 记录当前类型

    @Override
    public void fetchInfo() {
        try {
            String httpContent = HttpUtils.getHttpContent(formatUrl());
            Type type = new TypeToken<ArrayList<RspUnsplash>>() {}.getType();
            List<RspUnsplash> unsplashList = new Gson().fromJson(httpContent, type);
            if (unsplashList == null || unsplashList.isEmpty()) {
                // TODO-JiangJiweiFeat: 2021/3/15 空数据
                mRspUnsplash = null;
            } else {
                mRspUnsplash = unsplashList.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(URL).append("?");
        Object[] objects = queryMap.keySet().toArray();
        for (int i = 0; i < objects.length; i++) {
            String key = objects[i].toString();
            sb.append(key).append("=").append(queryMap.get(key));
            if (i != objects.length - 1) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    @Override
    public void storeMetaInfo() {
        if (mRspUnsplash == null) {
            return;
        }
        UnsplashImageVo.ImgVo unsplashImageVo = new UnsplashImageVo.ImgVo();
        unsplashImageVo.setColor(mRspUnsplash.getColor());
        unsplashImageVo.setDesc(mRspUnsplash.getDescription());

        unsplashImageVo.setView(mRspUnsplash.getViews());
        unsplashImageVo.setDownload(mRspUnsplash.getDownloads());
        unsplashImageVo.setLike(mRspUnsplash.getLikes());

        unsplashImageVo.setWidth(mRspUnsplash.getWidth());
        unsplashImageVo.setHeight(mRspUnsplash.getHeight());

        unsplashImageVo.setImgPage(mRspUnsplash.getLinks().getHtml());
        unsplashImageVo.setUserPage(mRspUnsplash.getUser().getLinks().getHtml());
        unsplashImageVo.setUserName(mRspUnsplash.getUser().getUsername());

        unsplashImageVo.setImgs(mRspUnsplash.getUrls());

        try {
            String json = FileUtils.getContent(getStoreFilePath().toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

            UnsplashImageVo storeImgVo = gson.fromJson(json, UnsplashImageVo.class);
            if (storeImgVo == null) {
                storeImgVo = new UnsplashImageVo();
            }
            storeImgVo.insertImg(unsplashImageVo);
            storeImgVo.update();

            String writeJson = gson.toJson(storeImgVo);
            FileUtils.rewriteFile(getStoreFilePath(), writeJson);

            int storeSize = storeImgVo.getImgs().size();
            ArrayList<MdImage> mdImages = new ArrayList<>(storeSize);
            for (int i = 0; i < storeSize; i++) {
                UnsplashImageVo.ImgVo imgVo = storeImgVo.getImgs().get(i);
                MdImage mdImage = new MdImage(MdImage.SOURCE_UNSPLASH, imgVo.getShareInfoMdString(), TimeUtils.currentDay(), imgVo.getPreviewImageMdString());
                mdImage.setUnsplashConvertImg(imgVo.getCoverImgMdString());
                mdImages.add(mdImage);
            }
            FileUtils.writeImageMdFile(getPreviewFilePath(), UNSPLASH_TITLE, MD_PREVIEW_TABLE_TITLE, mdImages);
            FileUtils.writeImageMdFile(getPreviewFilePath(), UNSPLASH_TITLE, mdImages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Path getStoreFilePath() {
        return Paths.get(System.getProperty("user.dir") + "/UnsplashImage.json");
    }

    @Override
    public Path getPreviewFilePath() {
        return Paths.get(System.getProperty("user.dir") + "/UnsplashImage.md");
    }

    @Override
    public boolean isSync() {
        return true;
    }
}
