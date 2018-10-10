package com.wjs.network.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//https://blog.csdn.net/u011414643/article/details/56676139
public class ThreadPoolUtils {
    private static ExecutorService service;
    private ThreadPoolUtils(){}
    public static synchronized ExecutorService getService() {
        if(service==null){
            int availableProcessors=Runtime.getRuntime().availableProcessors();
            service=Executors.newFixedThreadPool(availableProcessors);
        }
        return service;
    }
}
