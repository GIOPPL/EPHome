package com.gioppl.ephome.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModelThread {
    private static ModelThread modelThread;
    private static ExecutorService executorService;
    public ModelThread(){}
    public static ModelThread getInstance(){
        if (modelThread==null)
            modelThread=new ModelThread();
        return modelThread;
    }
    public ExecutorService getGlobalThreadPool(){
        if (executorService==null)
            executorService= Executors.newCachedThreadPool();
        return executorService;
    }
}
