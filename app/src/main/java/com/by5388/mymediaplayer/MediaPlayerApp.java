package com.by5388.mymediaplayer;

import android.app.Application;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/5/28.
 */
public class MediaPlayerApp extends Application {
    private static ThreadPoolExecutor sThreadPoolExecutor;

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    public static ThreadPoolExecutor getsThreadPoolExecutor() {
        return sThreadPoolExecutor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10);
        sThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue);
    }
}
