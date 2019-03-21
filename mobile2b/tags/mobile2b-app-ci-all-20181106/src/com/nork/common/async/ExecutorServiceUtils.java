package com.nork.common.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * 
 * @author huangsongbo
 *
 */
public class ExecutorServiceUtils {

	private volatile static ExecutorService threadPool = null;
	
	public static ExecutorService getInstance() {
		if(threadPool == null) {
			synchronized (ExecutorServiceUtils.class) {
				if(threadPool == null) {
					threadPool = Executors.newFixedThreadPool(20);
				}
			}
		}
		return threadPool;
	}
	
}
