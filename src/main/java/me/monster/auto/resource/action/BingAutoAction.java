package me.monster.auto.resource.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.monster.auto.resource.FileUtils;
import me.monster.auto.resource.HttpUtils;
import me.monster.auto.resource.OssHelper;
import me.monster.auto.resource.TimeUtils;
import me.monster.auto.resource.bean.BingImageVo;
import me.monster.auto.resource.bean.MdImage;
import me.monster.auto.resource.bean.RspBingVo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private RspBingVo mRspBingVo;

    @Override
    public void setup(String endPoint, String ossKey, String ossSecret, String bucketName, String dir) {
        mOssHelper = new OssHelper(endPoint, ossKey, ossSecret, bucketName, dir, "/bing");
    }

    @Override
    public void fetchInfo() {
        try {
            String httpContent = HttpUtils.getHttpContent(BING_API);
            mRspBingVo = new Gson().fromJson(httpContent, RspBingVo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void storeMetaInfo() {
        try {
            String json = FileUtils.getContent(getStoreFilePath().toString());
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();

            BingImageVo bingImageVo = gson.fromJson(json, BingImageVo.class);
            if (bingImageVo == null) {
                bingImageVo = new BingImageVo();
            }
            if (bingImageVo.getDayInfoList().isEmpty()) {
                storeAllPicture(gson, bingImageVo);
            } else {
                storePicture(gson, bingImageVo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 全量保存图片
    private void storeAllPicture(Gson gson, BingImageVo bingImageVo) throws IOException {
        for (RspBingVo.RspBingImgEle image : mRspBingVo.getImages()) {
            BingImageVo.BingImageElement element = xferBingImageVo(image, true);

            bingImageVo.appendList(element);
            bingImageVo.appendDay(image.getEnddate());
        }

        writeJsonStoreFile(gson, bingImageVo);
        writeMdPreviewFile(bingImageVo);
    }

    private BingImageVo.BingImageElement xferBingImageVo(RspBingVo.RspBingImgEle image, boolean ossSave) throws IOException {
        String url = BING_URL + image.getUrl();
        url = url.substring(0, url.indexOf("&"));
        BingImageVo.BingImageElement element = new BingImageVo.BingImageElement(url, TimeUtils.formatEndDate(image.getEnddate()), image.getCopyright());
        element.copyRightLink = BING_URL + image.getCopyrightlink();

        if (image.getTitle().isEmpty()) {
            element.fileName = element.endDate + ".jpg";
        } else {
            element.fileName = element.endDate + "_" + image.getTitle() + ".jpg";
        }
        if (ossSave) {
            element.ossPath = mOssHelper.save(element.url, element.fileName);
        }
        return element;
    }

    // 增量保存图片
    private void storePicture(Gson gson, BingImageVo bingImageVo) throws IOException {
        RspBingVo.RspBingImgEle lastImg = mRspBingVo.getLast();
        BingImageVo.BingImageElement newElement = xferBingImageVo(mRspBingVo.getLast(), false);
        if (bingImageVo.containDay(lastImg.getEnddate())) {
            System.out.println("contain " + lastImg.getEnddate() +" now finish current action run");
            writeJsonStoreFile(gson, bingImageVo);

            FileUtils.updateTime(getImagePreviewFilePath());
            return;
        }
        newElement.ossPath = mOssHelper.save(newElement.url, newElement.fileName);
        bingImageVo.appendList(newElement, 0);
        bingImageVo.appendDay(lastImg.getEnddate());

        writeJsonStoreFile(gson, bingImageVo);
        writeMdPreviewFile(bingImageVo);
    }

    private void writeJsonStoreFile(Gson gson, BingImageVo bingImageVo) throws IOException {
        bingImageVo.setUpdateTime(System.currentTimeMillis() + "");
        String json = gson.toJson(bingImageVo);
        FileUtils.rewriteFile(getStoreFilePath(), json);
    }

    private void writeMdPreviewFile(BingImageVo bingImageVo) throws IOException {
        List<MdImage> images = new ArrayList<>(bingImageVo.getDayInfoList().size());
        for (BingImageVo.BingImageElement bingImageElement : bingImageVo.getDayInfoList()) {
            images.add(new MdImage(MdImage.SOURCE_BING, bingImageElement.copyright, bingImageElement.endDate, bingImageElement.url));
        }
        FileUtils.writeImageMdFile(getImagePreviewFilePath(), images);
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
