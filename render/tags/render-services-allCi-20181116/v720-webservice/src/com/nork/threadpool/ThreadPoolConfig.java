package com.nork.threadpool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 线程配置类
 * 
 */
public class ThreadPoolConfig implements ILifeCycle {

    
	public final static String THREAD_POOL_NAME = "UploadPicFilePool"; //线程池名称
	
	protected final static int COREPOOL_SIZE = 5; //核心线程数
	
	protected final static int MAXPOOL_SIZE = 20; // 最大线程数
	
	protected final static long KEEPALIVETIME = 15L;//线程成活时间
	
	protected final static int WORKQUEUE_SIZE= 30;//工作队列大小
	
	
    /**
     * key为线程池名称，value为ThreadPoolInfo实例。
     */
    protected Map<String, ThreadPoolInfo> _multiThreadPoolInfo = new HashMap<String, ThreadPoolInfo>();
    
    /** 线程池状态收集开关 */
    protected boolean _threadPoolStateSwitch = false;
    protected int _threadPoolStateInterval = 60;   // 单位：秒
    
    /** 线程状态收集开关 */
    protected boolean _threadStateSwitch = false;
    protected int _threadStateInterval = 60;   // 单位：秒
    
    /** 线程堆栈收集开关 */
    protected boolean _threadStackSwitch = false;
    protected int _threadStackInterval = 60;   // 单位：秒
    
    @Override
    public void init() {
        initConfig();
    }
    
    private void initConfig() {
    	ThreadPoolInfo info = new ThreadPoolInfo();
        info.setName(ThreadPoolConfig.THREAD_POOL_NAME);
        info.setCoreSize(ThreadPoolConfig.COREPOOL_SIZE);
        info.setMaxSize(ThreadPoolConfig.MAXPOOL_SIZE);
        info.setThreadKeepAliveTime(ThreadPoolConfig.KEEPALIVETIME);
        info.setQueueSize(ThreadPoolConfig.WORKQUEUE_SIZE);
        
        _multiThreadPoolInfo.put(info.getName(), info);
    }
    

    /**
     * 指定名称的线程池的配置是否存在。
     * 
     * @return 如果指定名称的线程池的配置存在返回true，如果不存在返回false；如果传入的线程池名称为null也返回false。
     */
    public boolean containsPool(String poolName) {
        if (null == poolName || null == _multiThreadPoolInfo || _multiThreadPoolInfo.isEmpty()) {
            return false;
        }
        
        return _multiThreadPoolInfo.containsKey(poolName);
    }
    
    /**
     * 获取指定线程池的配置信息。
     * 
     * @param threadpoolName 线程池名称
     * @return 线程池配置信息
     */
    public ThreadPoolInfo getThreadPoolConfig(String threadpoolName) {
        return _multiThreadPoolInfo.get(threadpoolName);
    }
    
    /**
     * 获取所有线程池的配置信息。
     * 
     * @return 线程池配置信息集合
     */
    public Collection<ThreadPoolInfo> getThreadPoolConfig() {
        return _multiThreadPoolInfo.values();
    }
    
    /**
     * @return 输出各个线程池状态信息的开关，true表示开，false表示关
     */
    public boolean getThreadPoolStateSwitch() {
        return _threadPoolStateSwitch;
    }
    
    /**
     * @return 线程池状态信息输出的间隔时间（单位：秒）
     */
    public int getThreadPoolStateInterval() {
        return _threadPoolStateInterval;
    }
    
    /**
     * @return 输出各个线程组中线程状态信息的开关，true表示开，false表示关
     */
    public boolean getThreadStateSwitch() {
        return _threadStateSwitch;
    }
    
    /**
     * @return 线程状态信息输出的间隔时间（单位：秒）
     */
    public int getThreadStateInterval() {
        return _threadStateInterval;
    }
    
    /**
     * @return 输出所有线程堆栈的开关，true表示开，false表示关
     */
    public boolean getThreadStackSwitch() {
        return _threadStackSwitch;
    }
    
    /**
     * @return 线程堆栈信息输出的间隔时间（单位：秒）
     */
    public int getThreadStackInterval() {
        return _threadStackInterval;
    }
    
    @Override
    public void destroy() {
        _threadPoolStateSwitch = false;
        _threadStateSwitch = false;
        _multiThreadPoolInfo.clear();
    }

}
