package me.monster.auto.resource.tool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorTool {

    private ExecutorTool() {

    }

    public static ExecutorTool getInstance() {
        return ExecutorHolder.instance;
    }

    private static class ExecutorHolder {
        private static final ExecutorTool instance = new ExecutorTool();
    }

    private ThreadPoolExecutor unsplashExecutor;

    /**
     * 核心线程数为 3 的含义是实例化时就创建 3 个线程
     * 如果非核心线程数为 3 则需要添加任务时创建线程
     * @return 线程池
     */
    public ThreadPoolExecutor getUnsplashExecutor() {
        if (unsplashExecutor == null) {
            unsplashExecutor = new ThreadPoolExecutor(3, 3,
                    60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        }
        return unsplashExecutor;
    }


}
