package com.nork.common.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/***
 * 异步线程管理类
 * @author qiu.jun
 * @date 2016.06.16
 */
public class TaskExecutor {
	private static TaskExecutor taskExecutor;
	private ExecutorService executorService;
//	private List<FutureTask<Result>> futureTasks;

	private TaskExecutor() {
		init();
	}

	public synchronized static TaskExecutor getInstance() {
		if (taskExecutor == null) {
			taskExecutor = new TaskExecutor();
		}
		return taskExecutor;
	}

	private void init() {
		// 异步任务列表
//		futureTasks = new ArrayList<FutureTask<Result>>();
		// 线程池初始化
		executorService = Executors.newFixedThreadPool(10);
	}

	/***
	 * 添加任务到线程池
	 * @param futureTask
	 */
	public void addTask(FutureTask<Result> futureTask) {
		//添加任务到队列
//		futureTasks.add(futureTask);
		// 提交异步任务到线程池
		// 由于是异步并行任务，所以这里并不会阻塞
		executorService.submit(futureTask);
	}

	public void release() {
		if (executorService != null) {
			// 清理线程池
			executorService.shutdown();
		}
	}
	
}
