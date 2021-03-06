package me.monster.auto.resource;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 4:14 下午
 */
public class OssHelper {
    private String endPoint = "";

    private String keyId = "";
    private String secret = "";

    private String bucket = "";

    private String basePath = "";

    private String storePath = "";

    public OssHelper(String endPoint, String keyId, String secret, String bucket, String basePath, String storePath) {
        this.endPoint = endPoint;
        this.keyId = keyId;
        this.secret = secret;
        this.bucket = bucket;
        this.basePath = basePath;
        this.storePath = storePath;
    }

    public String save(String link, String fileName) throws IOException {
        if (isEmpty(endPoint) || isEmpty(keyId) || isEmpty(secret)
                || isEmpty(bucket) || isEmpty(basePath) || isEmpty(storePath)
                || isEmpty(link)) {
            return "";
        }
        String ossPath = basePath + storePath + File.separator + fileName;
        OSS ossClient = new OSSClientBuilder().build(formatEndPoint(false), keyId, secret);
        if (ossClient == null) {
            throw new IllegalArgumentException("Oss init has wrong " + keyId);
        }
        InputStream inputStream = new URL(link).openStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, ossPath, inputStream);
        ossClient.putObject(putObjectRequest);
        return formatEndPoint(true) + File.separator + ossPath;
    }

    private boolean isEmpty(String text) {
        return text == null || text.isEmpty() || "null".equals(text);
    }

    private String formatEndPoint(boolean withBucket) {
        String url = "https://";
        if (withBucket) {
            url = url + bucket +".";
        }
        url = url + endPoint;
        return url;
    }
}
