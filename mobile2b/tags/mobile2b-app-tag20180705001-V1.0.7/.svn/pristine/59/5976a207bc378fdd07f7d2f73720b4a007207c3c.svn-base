package com.nork.common.async;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestTaskExecutor {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 线程池初始化
        ExecutorService exec = Executors.newCachedThreadPool();
        // 异步任务列表
        ArrayList<Future<String>> results =new ArrayList<Future<String>>();
        for(int i=0;i<10;i++){
        	//添加任务到队列  并 提交异步任务到线程池
    		// 由于是异步并行任务，所以这里并不会阻塞
            results.add(exec.submit(new TestTask(i)));
        }
        for(Future<String> fs : results){
            //////System.out.println(fs.get());
        }
   }

}
