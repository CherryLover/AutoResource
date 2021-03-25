package me.monster.auto.resource;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import me.monster.auto.resource.bean.RunConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 4:14 下午
 */
public class OssHelper {
    public static final String FOLDER_BING = "bing";

    public static String save(String dir, String link, String fileName) throws IOException {
        RunConfig.AliOssConfig aliOss = DataHolder.getInstance().getRunConfig().getAliOss();
        if (!aliOss.isAvailable()) {
            return "";
        }
        String ossPath;
        if (RuntimeUtils.isDebug()) {
            ossPath = aliOss.getOssSaveFolder() + File.separator + dir + File.separator + "Debug" + File.separator + fileName;
        } else {
            ossPath = aliOss.getOssSaveFolder() + File.separator + dir + File.separator + fileName;
        }
        OSS ossClient = new OSSClientBuilder().build(formatEndPoint(aliOss.getEndPoint(), null), aliOss.getOssKey(), aliOss.getOssSecret());
        if (ossClient == null) {
            throw new IllegalArgumentException("Oss init has wrong " + aliOss.getOssKey());
        }
        InputStream inputStream = new URL(link).openStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(aliOss.getBucketName(), ossPath, inputStream);
        ossClient.putObject(putObjectRequest);
        return formatEndPoint(aliOss.getEndPoint(), aliOss.getBucketName()) + File.separator + ossPath;
    }

    private static String formatEndPoint(String endPoint, String bucketName) {
        String url = "https://";
        if (bucketName != null) {
            url = url + bucketName + ".";
        }
        url = url + endPoint;
        return url;
    }
}
