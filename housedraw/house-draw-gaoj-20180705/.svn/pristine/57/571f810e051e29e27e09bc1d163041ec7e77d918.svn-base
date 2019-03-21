package com.sandu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年3月29日
 */

@Lazy
@Configuration
@EnableScheduling
public class TaskSchedulingConfig {

	private static final int DEFAULT_POOL_SIZE = 1;

	@Bean
	public TaskScheduler taskScheduler() {

		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(DEFAULT_POOL_SIZE);
		taskScheduler.setThreadNamePrefix("draw-task-");

		return taskScheduler;
	}
}
