package com.sandu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Sandu
 * @ClassName CommonConfig
 * @date 2018/11/6
 */
@Configuration
public class CommonConfig {

	@Bean("commonThreadPool")
	public ThreadPoolExecutor threadPoolExecutor() {
		/**
		 * corePoolSize： 线程池维护线程的最少数量
		 * maximumPoolSize：线程池维护线程的最大数量
		 * keepAliveTime： 线程池维护线程所允许的空闲时间
		 * unit： 线程池维护线程所允许的空闲时间的单位
		 * workQueue： 线程池所使用的缓冲队列
		 * threadFactory：创建执行线程的工厂
		 * handler： 线程池对拒绝任务的处理策略
		 */

		return new ThreadPoolExecutor(
				50,
				300,
				300,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(1000),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
}
