package com.nork.threadpool;

import org.springframework.stereotype.Component;

/**
 * 线程池实例管理。
 * 
 */
public class ThreadPoolManager implements ILifeCycle {

    private ILifeCycle _threadPool = new ThreadPoolImpl(); 
    
    private static Object _lock = new Object();
    private boolean _initStatus = false;
    private boolean _destroyStatus = false;
    
    private static ThreadPoolManager _instance = new ThreadPoolManager();
    public static ThreadPoolManager getSingleton() {
        return _instance;
    }

    public ThreadPool getThreadPool() {
        return (ThreadPool) _threadPool;
    }
    
    
    @Override
    public void init() {
        synchronized (_lock) {
            if (_initStatus) {
                return;
            }
            _threadPool.init();
            _initStatus = true;
        }
    }

    @Override
    public void destroy() {
        synchronized (_lock) {
            if (_destroyStatus) {
                return;
            }
            _threadPool.destroy();
            _destroyStatus = true;
        }
    }

}
