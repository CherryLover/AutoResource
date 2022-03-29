package me.monster.auto.resource.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.monster.auto.resource.Entrance;
import me.monster.auto.resource.bean.GeekStore;
import me.monster.auto.resource.bean.MdInfo;
import me.monster.auto.resource.bean.NotificationVo;
import me.monster.auto.resource.bean.RspGeekHotList;
import me.monster.auto.resource.tool.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class GeekReadInfoAction implements AutoAction {

    public static final String URL = "https://geeker.devhub.top/v1/api/articles/hot";
    public static final String TITLE_PREVIEW_FILE = "极客阅读";
    private GeekStore mGeekStoreNew;

    @Override
    public void fetchInfo() {
        try {
            String response = HttpUtils.getPostResponse(URL, new HashMap<>(), new HashMap<>());
            RspGeekHotList rspGeekHotList = new Gson().fromJson(response, RspGeekHotList.class);
            StringBuilder sb = new StringBuilder();
            if (rspGeekHotList.isSuccess()) {
                mGeekStoreNew = new GeekStore();
                int idx = 1;
                for (RspGeekHotList.GeekInfoBean geekInfo : rspGeekHotList.getData()) {
                    sb.append("序号：").append(idx).append('\n')
                            .append(geekInfo.getTitle()).append('\n')
                            .append(geekInfo.getUrl()).append('\n')
                            .append('\n');
                    idx += 1;
                }
                TelegramNotificationList.getInstance().addNotification(new NotificationVo(sb.toString(), NotificationVo.SOURCE_GEEK));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeMetaInfo() {
//        try {
//            String oldInfo = FileUtils.getContent(getStoreFilePath().toString());
//            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//            GeekStore oldStore = gson.fromJson(oldInfo, GeekStore.class);
//            if (oldStore == null) {
//                oldStore = new GeekStore();
//            }
//            oldStore.addNewInfo(mGeekStoreNew);
//
//            String newGeekInfo = gson.toJson(oldStore);
//            FileUtils.rewriteFile(getStoreFilePath(), newGeekInfo);
//
//            MdInfo mdInfo = new MdInfo();
//            mdInfo.setFileTitle(TITLE_PREVIEW_FILE);
//            mdInfo.setTableTitle("日期", "内容", "备注");
//            for (GeekStore.GeekStoreBean geekStoreBean : oldStore.getInfoList()) {
//                MdInfo.MdInfoItem item = new MdInfo.MdInfoItem();
//                item.setDate(TimeUtils.formatDay(geekStoreBean.getTimestamp()));
//                item.setContent(geekStoreBean.getPreviewContent());
//                mdInfo.appendItem(item);
//            }
//            mdInfo.refresh();
//            FileUtils.writeInfoMdFile(getPreviewFilePath(), mdInfo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Path getStoreFilePath() {
        return Paths.get(System.getProperty("user.dir") + "/GeekerRead.json");
    }

    @Override
    public Path getPreviewFilePath() {
        return Paths.get(System.getProperty("user.dir") + "/GeekerRead.md");
    }

    @Override
    public boolean isSync() {
        return false;
    }

    @Override
    public String name() {
        return Entrance.ACTION_GEEK_NEWS;
    }

    @Override
    public int priority() {
        return 20;
    }
}
