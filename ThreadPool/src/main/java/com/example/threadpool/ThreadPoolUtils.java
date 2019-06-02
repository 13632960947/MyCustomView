package com.example.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {
    public static ThreadPoolExecutor threadPool;
    public static void execute(Runnable runnable){
            getThreadPool().execute(runnable);
    }

    public static void cancelThread(Runnable runnable){
        if(threadPool!=null)
        threadPool.getQueue().remove(runnable);
    }


    public static ThreadPoolExecutor getThreadPool(){
        if(threadPool!=null){
            return threadPool;
        }else{
            synchronized (ThreadPoolUtils.class){
                if(threadPool==null) {
                    threadPool = new ThreadPoolExecutor(8, 16,60,TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(32)
                            ,new ThreadPoolExecutor.CallerRunsPolicy());
                }
                return threadPool;
                }
            }
        }

}
