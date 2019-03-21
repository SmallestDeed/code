package com.nork.threadpool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 线程池。
 * 
 *  
 */
public interface ThreadPool {

    /**
     * 提交一个不需要返回值的异步任务给默认的线程池执行。
     * 
     */
    public Future<?> submit(Runnable task);
    
    /**
     * 提交一个不需要返回值的异步任务给指定的线程池执行。
     * 
     */
    public Future<?> submit(Runnable task, String threadpoolName);
    
    /**
     * 提交一个不需要返回值的异步任务给指定的线程池执行。
     * 
     */
    public Future<?> submit(Runnable task, String threadpoolName, 
            FailHandler<Runnable> failHandler);
    
    /**
     * 提交一个需要返回值的异步任务给默认的线程池执行。
     */
    public <T> Future<T> submit(Callable<T> task);
    
    /**
     * 提交一个需要返回值的异步任务给指定的线程池执行。
     */
    public <T> Future<T> submit(Callable<T> task, String threadpoolName);
    
    /**
     * 提交一个需要返回值的异步任务给指定的线程池执行。
     */
    public <T> Future<T> submit(Callable<T> task, String threadpoolName, 
            FailHandler<Callable<T>> failHandler);
    
    /**
     * 在线程池"default"中执行多个需要返回值的异步任务，并设置超时时间。
     */
    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, 
            long timeout, TimeUnit timeoutUnit);
    
    /**
     * 在指定的线程池中执行多个需要返回值的异步任务，并设置超时时间。
     */
    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks,  
            long timeout, TimeUnit timeoutUnit, String threadpoolName);

    /**
     * 查询指定名称的线程池是否存在。
     */
    public boolean isExists(String threadpoolName);
    
    /**
     * 获取线程池的信息。如：线程池容量，队列容量。
     */
    public ThreadPoolInfo getThreadPoolInfo(String threadpoolName);
    
    
    public ExecutorService getThreadPool(String threadpoolName);
    
    
    public ExecutorService getExistsThreadPool(String threadpoolName);

}
