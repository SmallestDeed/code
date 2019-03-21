//package com.nork.common.pano;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.log4j.Logger;
//
//import com.nork.common.util.Utils;
//
///**
// * Created by Administrator on 2017/4/12.
// */
//public class GeneratePanoramaExecutor {
//
//    Logger logger = Logger.getLogger(GeneratePanoramaExecutor.class);
//
//    private final int corePoolSize = 3;// 线程池大小
//    private ExecutorService executor;
//    private static GeneratePanoramaExecutor panoramaExecutor;
//
//    public GeneratePanoramaExecutor(){
//        init();
//    }
//
//    public static synchronized GeneratePanoramaExecutor getInstance(){
//        if( panoramaExecutor == null ){
//            panoramaExecutor = new GeneratePanoramaExecutor();
//        }
//        return panoramaExecutor;
//    }
//
//    public void init(){
//        executor = Executors.newFixedThreadPool(corePoolSize);
//    }
//
//    public void addTask(GeneratePanoramaTask task){
//        if( task == null ){
//            logger.error(Utils.getLineNumber()+"task is null......");
//            return;
//        }
//        logger.info("加入720页面生成任务列表!taskId="+task.getTaskId()+" ,businessCode="+task.getBusinessCode()+" ,picPath="+task.getPicPath());
//        executor.execute(task);
//    }
//}
