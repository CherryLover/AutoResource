package me.monster.auto.resource.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.monster.auto.resource.Entrance;
import me.monster.auto.resource.bean.*;
import me.monster.auto.resource.tool.*;

import java.io.IOException;
import java.nio.file.Files;
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
    public static final String[] ORIENTATION_ITEMS = {"landscape", "portrait", "squarish"};
    public static final String[] QUERY_ITEMS = {"wallpapers", "nature", "people", "architecture", "current-events", "fashion", "film", "travel", "textures-patterns", "animals", "food-drink"};
    public static final String UNSPLASH_TITLE = "Unsplash Images";
    public static final String CLIENT_ID = "client_id";
    public static final String[] MD_PREVIEW_TABLE_TITLE = {"Landscape", "Portrait", "Squarish"};
    private final Map<String, String> queryMap;

    public UnsplashImgAutoAction() {
        this.queryMap = new HashMap<>();
        queryMap.put(URL_ARGS_COUNT, "1");
        queryMap.put(URL_ARGS_ORIENTATION, getOrientation());
        queryMap.put(URL_ARGS_QUERY, getQueryInfo());
        // TODO-JiangJiweiFeat: 2021/3/17 开发过程中使用 镜像地址
    }

    public void setKeyValue4Test(String key, String value) {
        queryMap.put(key, value);
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
    public void fetchInfo() {
        RunConfig runConfig = DataHolder.getInstance().getRunConfig();
        if (!runConfig.getUnsplash().isAvailable()) {
            System.out.println("no Unsplash Client id");
            return;
        }
        queryMap.put(CLIENT_ID, runConfig.getUnsplash().getClientId());
        String[] urlArray = new String[3];
        for (int i = 0; i < ORIENTATION_ITEMS.length; i++) {
            queryMap.put(URL_ARGS_ORIENTATION, ORIENTATION_ITEMS[i]);
            String url = formatUrl(queryMap);
            urlArray[i] = url;
        }
        UnsplashExecutor.getInstance()
                .setStoreListener(this::storeInJsonAndMd)
                .getContent(urlArray);
    }

    private void storeInJsonAndMd(Map<String, RspUnsplash> store) {
        try {
            UnsplashImageVo newVo = coverVoObj(store);

            for (UnsplashImageVo.ImgVo img : newVo.getImgs()) {
                img.setDate(TimeUtils.currentDay());
                addNotification(img);
            }
            if (!DataHolder.getInstance().getRunConfig().getUnsplash().isSaveMarkdown()) {
                System.err.println("Unsplash 跳过保存 Markdown");
                return;
            }
            String json = FileUtils.getContent(getStoreFilePath().toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            UnsplashImageVo storeImgVo = gson.fromJson(json, UnsplashImageVo.class);
            if (storeImgVo == null) {
                storeImgVo = new UnsplashImageVo();
            }
            storeImgVo.insertAllImg(newVo.getImgs());
            storeImgVo.update();

            String writeJson = gson.toJson(storeImgVo);
            FileUtils.rewriteFile(getStoreFilePath(), writeJson);

            int storeSize = storeImgVo.getImgs().size();
            ArrayList<MdImage> mdImages = new ArrayList<>(storeSize);
            for (int i = 0; i < storeSize; i++) {
                UnsplashImageVo.ImgVo imgVo = storeImgVo.getImgs().get(i);
                MdImage mdImage = new MdImage(MdImage.SOURCE_UNSPLASH, imgVo.getShareInfoMdString(), imgVo.getDate(), imgVo.getPreviewImageMdString());
                mdImage.setUnsplashConvertImg(imgVo.getCoverImgMdString());
                mdImages.add(mdImage);
            }
            if (Files.exists(getPreviewFilePath())) {
                Files.delete(getPreviewFilePath());
            }
            FileUtils.writeImageMdFile(getPreviewFilePath(), UNSPLASH_TITLE, MD_PREVIEW_TABLE_TITLE, mdImages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNotification(UnsplashImageVo.ImgVo img) {
        StringBuilder sb = new StringBuilder();
        sb.append(img.getDesc()).append('\n')
                .append("摄影师：").append(img.getUserName()).append('\n')
                .append("下载量：").append(img.getDownload()).append('\n')
                .append("喜欢：").append(img.getLike()).append('\n')
                .append(img.getImgPage());
        final NotificationVo notificationVo = new NotificationVo(sb.toString(), NotificationVo.SOURCE_UNSPLASH);
        notificationVo.setAttachmentFileName(TimeUtils.currentTime() + ".jpg");
        notificationVo.setAttachmentUrl(img.getImgs().getRaw());
        TelegramNotificationList.getInstance().addNotification(notificationVo);
    }

    private UnsplashImageVo coverVoObj(Map<String, RspUnsplash> store) throws IOException {
        RspUnsplash landscape = store.get(ORIENTATION_ITEMS[0]);
        RspUnsplash portrait = store.get(ORIENTATION_ITEMS[1]);
        RspUnsplash squarish = store.get(ORIENTATION_ITEMS[2]);

        UnsplashImageVo.ImgVo lanVo, portraitVo, squarishVo;
        if (landscape == null) {
            lanVo = getDefaultVo(ORIENTATION_ITEMS[0]);
        } else {
            lanVo = covert2Vo(ORIENTATION_ITEMS[0], landscape);
        }
        if (portrait == null) {
            portraitVo = getDefaultVo(ORIENTATION_ITEMS[1]);
        } else {
            portraitVo = covert2Vo(ORIENTATION_ITEMS[1], portrait);
        }
        if (squarish == null) {
            squarishVo = getDefaultVo(ORIENTATION_ITEMS[2]);
        } else {
            squarishVo = covert2Vo(ORIENTATION_ITEMS[2], squarish);
        }
        UnsplashImageVo vo = new UnsplashImageVo();
        vo.addImage(lanVo);
        vo.addImage(portraitVo);
        vo.addImage(squarishVo);
        return vo;
    }

    private UnsplashImageVo.ImgVo getDefaultVo(String orientation) throws IOException {
        String content = FileUtils.getContent("unsplash_default_img.json");
        RspUnsplash rspUnsplash = new Gson().fromJson(content, RspUnsplash.class);
        return covert2Vo(orientation, rspUnsplash);
    }

    private static String formatUrl(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(URL).append("?");
        Object[] objects = map.keySet().toArray();
        for (int i = 0; i < objects.length; i++) {
            String key = objects[i].toString();
            sb.append(key).append("=").append(map.get(key));
            if (i != objects.length - 1) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    private UnsplashImageVo.ImgVo covert2Vo(String orientation,RspUnsplash rspUnsplash) {
        UnsplashImageVo.ImgVo unsplashImageVo = new UnsplashImageVo.ImgVo();
        unsplashImageVo.setOrientation(orientation);
        unsplashImageVo.setColor(rspUnsplash.getColor());
        unsplashImageVo.setDesc(rspUnsplash.getDescription());

        unsplashImageVo.setView(rspUnsplash.getViews());
        unsplashImageVo.setDownload(rspUnsplash.getDownloads());
        unsplashImageVo.setLike(rspUnsplash.getLikes());

        unsplashImageVo.setWidth(rspUnsplash.getWidth());
        unsplashImageVo.setHeight(rspUnsplash.getHeight());

        unsplashImageVo.setImgPage(rspUnsplash.getLinks().getHtml());
        unsplashImageVo.setUserPage(rspUnsplash.getUser().getLinks().getHtml());
        unsplashImageVo.setUserName(rspUnsplash.getUser().getUsername());

        unsplashImageVo.setImgs(rspUnsplash.getUrls());
        return unsplashImageVo;
    }

    @Override
    public void storeMetaInfo() {

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

    @Override
    public String name() {
        return Entrance.ACTION_UNSPLASH;
    }

    @Override
    public int priority() {
        return 40;
    }
}
