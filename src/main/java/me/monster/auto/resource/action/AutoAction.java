package me.monster.auto.resource.action;

import java.nio.file.Path;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 6:07 下午
 */
public interface AutoAction {
    /**
     * 从第三方处拉取资源
     */
    void fetchInfo();

    /**
     * 存储信息
     */
    void storeMetaInfo();

    /**
     * JSON 文件路径，用于存储当前 Action 的操作结果
     * @return JSON 文件路径
     */
    Path getStoreFilePath();

    /**
     * Markdown 文件路径，用于展示当前 Action 的操作结果
     * @return Markdown 文件路径
     */
    Path getPreviewFilePath();

    /**
     * 是否为异步处理，如果是异步处理，Main 方法中不再调用其余方法，仅调用 {@link #setup(String, String, String, String, String)} 方法
     * @return true 异步 false 同步
     */
    boolean isSync();
}
