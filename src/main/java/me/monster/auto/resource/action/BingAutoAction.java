package me.monster.auto.resource.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.monster.auto.resource.FileUtils;
import me.monster.auto.resource.HttpUtils;
import me.monster.auto.resource.OssHelper;
import me.monster.auto.resource.bean.BingImageVo;
import me.monster.auto.resource.bean.MdImage;
import me.monster.auto.resource.bean.RspBingVo;

import java.io.IOException;
import java.nio.file.Path;
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
    private BingImageVo.BingImageElement mNewElement;
    private RspBingVo.RspBingImgEle mLastImg;

    @Override
    public void setup(String endPoint, String ossKey, String ossSecret, String bucketName, String dir) {
        mOssHelper = new OssHelper(endPoint, ossKey, ossSecret, bucketName, dir, "/bing");
    }

    @Override
    public void fetchInfo() {
        try {
            String httpContent = HttpUtils.getHttpContent(BING_API);
            RspBingVo rspList = new Gson().fromJson(httpContent, RspBingVo.class);
            mLastImg = rspList.getLast();

            // 图片地址
            String url = BING_URL + mLastImg.getUrl();
            url = url.substring(0, url.indexOf("&"));
            // 图片时间
            String endDate = mLastImg.getEnddate();
            LocalDate localDate = LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
            endDate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            // 图片版权
            String copyright = mLastImg.getCopyright();
            String copyRightLink = BING_URL + mLastImg.getCopyrightlink();

            mNewElement = new BingImageVo.BingImageElement(url, endDate, copyright);
            mNewElement.copyRightLink = copyRightLink;

            if (mLastImg.getTitle().isEmpty()) {
                mNewElement.fileName = mNewElement.endDate + ".jpg";
            } else {
                mNewElement.fileName = mNewElement.endDate + "_" + mLastImg.getTitle() + ".jpg";
            }
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
            String content = FileUtils.getContent(getStoreFilePath().toString());
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();
            mNewElement.ossPath = mOssHelper.save(mNewElement.url, mNewElement.fileName);

            BingImageVo bingImageVo = gson.fromJson(content, BingImageVo.class);
            if (bingImageVo.containDay(mLastImg.getEnddate())) {
                System.out.println("contain " + mLastImg.getEnddate() +" now finish current action run");
                bingImageVo.setUpdateTime(System.currentTimeMillis() + "");

                String json = gson.toJson(bingImageVo);
                FileUtils.rewriteFile(getStoreFilePath(), json);

                FileUtils.updateTime(getImagePreviewFilePath());
                return;
            }
            bingImageVo.appendList(mNewElement);
            bingImageVo.appendDay(mLastImg.getEnddate());

            bingImageVo.setUpdateTime(System.currentTimeMillis() + "");
            String json = gson.toJson(bingImageVo);
            FileUtils.rewriteFile(getStoreFilePath(), json);

            List<MdImage> images = new ArrayList<>(bingImageVo.getDayInfoList().size());
            for (BingImageVo.BingImageElement bingImageElement : bingImageVo.getDayInfoList()) {
                images.add(new MdImage(MdImage.SOURCE_BING, bingImageElement.copyright, bingImageElement.endDate, bingImageElement.url));
            }
            FileUtils.writeImageMdFile(getImagePreviewFilePath(), images);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path getStoreFilePath() {
        return Paths.get(System.getProperty("user.dir") + "/BingImage.json");
    }

    @Override
    public Path getImagePreviewFilePath() {
        return Paths.get(System.getProperty("user.dir") + "/BingImage.md");
    }
}
