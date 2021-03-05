package me.monster.auto.resource.action;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 6:07 下午
 */
public interface AutoAction {
    void setup(String endPoint, String ossKey, String ossSecret, String bucketName, String dir);

    void fetchInfo();

    void storeMetaInfo();

    String getStoreFilePath();
}
