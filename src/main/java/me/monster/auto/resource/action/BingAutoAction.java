package me.monster.auto.resource.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.monster.auto.resource.FileUtils;
import me.monster.auto.resource.HttpUtils;
import me.monster.auto.resource.OssHelper;
import me.monster.auto.resource.bean.BingImageElement;
import me.monster.auto.resource.bean.RspBingImgEle;
import me.monster.auto.resource.bean.RspBingList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 6:14 下午
 */
public class BingAutoAction implements AutoAction {

    private static final String BING_API = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=10&nc=1612409408851&pid=hp&FORM=BEHPTB&uhd=1&uhdwidth=3840&uhdheight=2160";
    private static final String BING_URL = "https://cn.bing.com";

    private OssHelper mOssHelper;
    private BingImageElement mNewElement;

    @Override
    public void setup(String endPoint, String ossKey, String ossSecret, String bucketName, String dir) {
        mOssHelper = new OssHelper(endPoint, ossKey, ossSecret, bucketName, dir, "/bing");
    }

    @Override
    public void fetchInfo() {
        try {
            String httpContent = HttpUtils.getHttpContent(BING_API);
            RspBingList rspList = new Gson().fromJson(httpContent, RspBingList.class);
            RspBingImgEle lastImg = rspList.getLast();

            // 图片地址
            String url = BING_URL + lastImg.getUrl();
            url = url.substring(0, url.indexOf("&"));
            // 图片时间
            String endDate = lastImg.getEnddate();
//            LocalDate localDate = LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
//            endDate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            // 图片版权
            String copyright = lastImg.getCopyright();
            String copyRightLink = BING_URL + lastImg.getCopyrightlink();

            mNewElement = new BingImageElement(url, endDate, copyright);
            mNewElement.copyRightLink = copyRightLink;

            if (lastImg.getTitle().isEmpty()) {
                mNewElement.fileName = mNewElement.endDate + ".jpg";
            } else {
                mNewElement.fileName = mNewElement.endDate + "_" + lastImg.getTitle() + ".jpg";
            }
            mNewElement.ossPath = mOssHelper.save(url, mNewElement.fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void storeMetaInfo() {
        if (mNewElement == null) {
            return;
        }
        try {
            String content = FileUtils.getContent(getStoreFilePath());

            Type typeOfImageElement = new TypeToken<List<BingImageElement>>() {
            }.getType();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();

            List<BingImageElement> imageList = gson.fromJson(content, typeOfImageElement);
            if (imageList == null) {
                imageList = new ArrayList<>();
            }
            imageList.add(mNewElement);
            String json = gson.toJson(imageList);
            Files.write(Paths.get(getStoreFilePath()), json.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getStoreFilePath() {
        return System.getProperty("user.dir") + "/BingImage.json";
    }
}
