package com.dyj.customize;

import com.dyj.common.util.PropertiesUitl;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.*;

public class CustomizeThreadPool {

    public static ExecutorService executorService;

    static {
        try {
            executorService = getExecutorService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ExecutorService getExecutorService() throws IOException {

        Properties prop = PropertiesUitl.getPropertiesByResPath("/conf/threadpool.properties");
        String coreNum = prop.getProperty("threadPool.coreThreadNum");
        String maxNum = prop.getProperty("threadPool.maxThreadNum");
        String blockQueueNum = prop.getProperty("threadPool.blockQueueCapacity");

        int corePoolSize = coreNum == null ? 5 : Integer.valueOf(coreNum);
        int maxPoolSize = maxNum == null ? 10 : Integer.valueOf(maxNum);
        int capacity = blockQueueNum == null ? 1024 : Integer.valueOf(blockQueueNum);

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("customize-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                0L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<Runnable>(capacity),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        return executorService;
    }
}
