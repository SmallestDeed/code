package com.nork.threadpool;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.nork.threadpool.job.ThreadPoolStateJob;
import com.nork.threadpool.job.ThreadStackJob;
import com.nork.threadpool.job.ThreadStateJob;


/**
 * 多线程池。
 * 
 * 
 */
public class ThreadPoolImpl implements ILifeCycle, ThreadPool {

    /** 默认的线程池名称 */
    private static final String DEFAULT_THREAD_POOL = "UploadPicFilePool";

    private static Logger _logger = Logger.getLogger(ThreadPoolImpl.class);
    protected ThreadPoolConfig _threadPoolConfig = new ThreadPoolConfig();
    protected int _status = ThreadPoolStatus.UNINITIALIZED;
    
    Map<String, ExecutorService> _multiThreadPool = new HashMap<String, ExecutorService>();
    ThreadPoolStateJob _threadPoolStateJob; //线程池状态Job
    ThreadStateJob _threadStateJob;//线程状态Job
    ThreadStackJob _threadStackJob;//线程堆栈状态Job
    
    public ThreadPoolImpl() {
        // nothing
    }
    
    @Override
    public void init() {
        if (ThreadPoolStatus.UNINITIALIZED != _status) {
            _logger.warn("initialization thread pool failed!");
            return;
        }
        
        try {
            initThreadPool();
            startThreadPoolStateJob();
            startThreadStateJob();
            startThreadStackJob();
            _status = ThreadPoolStatus.INITIALITION_SUCCESSFUL;
        } catch (RuntimeException e) {
            _status = ThreadPoolStatus.INITIALITION_FAILED;
            throw e;
        }
    }
    
    /**
     * 为了方便以后扩展，所以做成了一个线程池列表，我们到时可以通过配置创建不同的多个线程池
     * 初始化所有线程池。
     */
    private void initThreadPool() {
        _threadPoolConfig.init();
//        if (! _threadPoolConfig.containsPool(DEFAULT_THREAD_POOL)) {
//            throw new IllegalStateException("The UploadPicFilePool not exists!!");
//        }
        Collection<ThreadPoolInfo> threadPoolInfoList = _threadPoolConfig.getThreadPoolConfig();
        for (ThreadPoolInfo threadPoolInfo : threadPoolInfoList) {
//            BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(threadPoolInfo.getQueueSize());
            BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadPoolInfo.getCoreSize(), threadPoolInfo.getMaxSize(), 
                    threadPoolInfo.getThreadKeepAliveTime(), TimeUnit.MINUTES, workQueue,
                    new DefaultThreadFactory(threadPoolInfo.getName()));
            _multiThreadPool.put(threadPoolInfo.getName(), threadPool);
            _logger.info("initialization thread pool success:"+ threadPoolInfo.getName());
        }
    }
    
    /**
     * 初始化并启动线程池状态统计Job。
     */
    private void startThreadPoolStateJob() {
        if (! _threadPoolConfig.getThreadPoolStateSwitch()) {
            return;
        }
        
        _threadPoolStateJob = new ThreadPoolStateJob(
                _multiThreadPool,
                _threadPoolConfig.getThreadPoolStateInterval() );
        _threadPoolStateJob.init();
        Thread jobThread = new Thread(_threadPoolStateJob);
        jobThread.setName("UploadPicFilePool-threadpoolstate");
        jobThread.start();
        
    }
    
    /**
     * 初始化并启动线程状态统计Job。
     */
    private void startThreadStateJob() {
        if (! _threadPoolConfig.getThreadStateSwitch()) {
            return;
        }
        
        _threadStateJob = new ThreadStateJob(_threadPoolConfig.getThreadStateInterval());
        _threadStateJob.init();
        Thread jobThread = new Thread(_threadStateJob);
        jobThread.setName("UploadPicFilePool-threadstate");
        jobThread.start();
        
        _logger.info("start job 'UploadPicFilePool-threadstate' success");
    }
    
    private void startThreadStackJob() {
        if (! _threadPoolConfig.getThreadStackSwitch()) {
            return;
        }
        
        _threadStackJob = new ThreadStackJob(_threadPoolConfig.getThreadStackInterval());
        _threadStackJob.init();
        Thread jobThread = new Thread(_threadStackJob);
        jobThread.setName("UploadPicFilePool-threadstack");
        jobThread.start();
        
        _logger.info("start job 'UploadPicFilePool-threadstack' success");
    }
    
    public Future<?> submit(Runnable task) {
        return submit(task, DEFAULT_THREAD_POOL);
    }
    
    public Future<?> submit(Runnable task, String threadpoolName) {
        if (null == task) {
            throw new IllegalArgumentException("task is null");
        }
        
        ExecutorService threadPool = getExistsThreadPool(threadpoolName);
        _logger.info("submit a task to thread pool :" + threadpoolName);
        
        return threadPool.submit(task);
    }
    
    @Override
    public Future<?> submit(Runnable task, String threadpoolName, 
            FailHandler<Runnable> failHandler) {
        try {
            return submit(task, threadpoolName);
        } catch (RejectedExecutionException e) {
            if (null != failHandler) {
                failHandler.execute(task);
            }
        }
        
        return null;
    }
    
    public ExecutorService getThreadPool(String threadpoolName) {
        if (StringUtils.isBlank(threadpoolName)) {
            throw new IllegalArgumentException("thread pool name is empty");
        }
        
        ExecutorService threadPool = _multiThreadPool.get(threadpoolName);
        
        return threadPool;
    }
    
    public ExecutorService getExistsThreadPool(String threadpoolName) {
        ExecutorService threadPool = getThreadPool(threadpoolName);
        if (null == threadPool) {
            throw new IllegalArgumentException( String.format("thread pool %s not exists", threadpoolName) );
        }
        
        return threadPool;
    }
    
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return submit(task, DEFAULT_THREAD_POOL);
    }
    
    @Override
    public <T> Future<T> submit(Callable<T> task, String threadpoolName) {
        if (null == task) {
            throw new IllegalArgumentException("task is null");
        }
        
        ExecutorService threadPool = getExistsThreadPool(threadpoolName);
        _logger.info("submit a task to thread pool :" + threadpoolName);
        
        return threadPool.submit(task);
    }
    
    @Override
    public <T> Future<T> submit(Callable<T> task, String threadpoolName, 
            FailHandler<Callable<T>> failHandler) {
        try {
            return submit(task, threadpoolName);
        } catch (RejectedExecutionException e) {
            if (null != failHandler) {
                failHandler.execute(task);
            }
        }
        
        return null;
    }
    
    @Override
    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, 
            long timeout, TimeUnit timeoutUnit) {
        return invokeAll(tasks, timeout, timeoutUnit, DEFAULT_THREAD_POOL);
    }
    
    @Override
    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks,
            long timeout, TimeUnit timeoutUnit, String threadpoolName) {
        if (null == tasks || tasks.isEmpty()) {
            throw new IllegalArgumentException("task list is null or empty");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout less than or equals zero");
        }
        
        ExecutorService threadPool = getExistsThreadPool(threadpoolName);
        
        try {
            return threadPool.invokeAll(tasks, timeout, timeoutUnit);
        } catch (InterruptedException e) {
            _logger.error("invoke task list occurs error", e);
        }
        
        return null;
    }
    
    @Override
    public boolean isExists(String threadpoolName) {
        ExecutorService threadPool = getThreadPool(threadpoolName);
        
        return (null == threadPool ? false : true);
    }
    
    @Override
    public ThreadPoolInfo getThreadPoolInfo(String threadpoolName) {
        ThreadPoolInfo info = _threadPoolConfig.getThreadPoolConfig(threadpoolName);
        
        return info.clone();
    }
    
    @Override
    public void destroy() {
        if (ThreadPoolStatus.DESTROYED == _status) {
            return;
        }
        
        for (Entry<String, ExecutorService> entry : _multiThreadPool.entrySet()) {
            _logger.info("shutdown the thread pool :" + entry.getKey());
            entry.getValue().shutdown();
        }
        
        if (null != _threadPoolStateJob) {
            _threadPoolStateJob.destroy();
            _logger.info("stop job 'UploadPicFilePool-threadpoolstate' success");
            _threadPoolStateJob = null;
        }
        
        if (null != _threadStateJob) {
            _threadStateJob.destroy();
            _logger.info("stop job 'UploadPicFilePool-threadstate' success");
            _threadStateJob = null;
        }
        
        if (null != _threadStackJob) {
            _threadStackJob.destroy();
            _logger.info("stop job 'UploadPicFilePool-threadstack' success");
            _threadStackJob = null;
        }
        
        _threadPoolConfig.destroy();
        _status = ThreadPoolStatus.DESTROYED;
    }

}
