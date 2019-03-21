package com.nork.threadpool.job;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * 收集所有线程池的状态信息，统计并输出汇总信息。
 * 
 * 
 */
public class ThreadPoolStateJob extends AbstractJob {

    private static Logger _logger = Logger.getLogger(ThreadPoolStateJob.class);
    private Map<String, ExecutorService> _multiThreadPool;
    
    public ThreadPoolStateJob(Map<String, ExecutorService> multiThreadPool, int interval) {
        this._multiThreadPool = multiThreadPool;
        super._interval = interval;
    }
    
    @Override
    protected void execute() {
        Set<Entry<String, ExecutorService>> poolSet = _multiThreadPool.entrySet();
        for (Entry<String, ExecutorService> entry : poolSet) {
            ThreadPoolExecutor pool = (ThreadPoolExecutor) entry.getValue();
            StringBuilder strB = new StringBuilder("ThreadPool:");
            strB.append(entry.getKey());
            strB.append("ActiveThread:");
            strB.append(pool.getActiveCount());
            strB.append("TotalTask:");
            strB.append(pool.getTaskCount());
            strB.append("CompletedTask:");
            strB.append(pool.getCompletedTaskCount());
        }
        
        super.sleep();
    }

}
