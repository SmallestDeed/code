package com.nork.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadFactory implements ThreadFactory {

	  private AtomicLong _count = new AtomicLong(1L);
	  private static final String DEFAULT_THREAD_NAME_PRIFIX = "sandu-thread";
	  private ThreadGroup _group;
	  private String _threadNamePrefix = "sandu-thread";

	  public DefaultThreadFactory() {
	    this("sandu-thread");
	  }

	  public DefaultThreadFactory(String threadNamePrefix) {
	    this._threadNamePrefix = threadNamePrefix;
	    ThreadGroup root = getRootThreadGroup();
	    this._group = new ThreadGroup(root, this._threadNamePrefix);
	  }

	  public Thread newThread(Runnable r)
	  {
	    Thread thread = new Thread(this._group, r);
	    thread.setName(this._threadNamePrefix + "-" + this._count.getAndIncrement());
	    if (thread.isDaemon()) {
	      thread.setDaemon(false);
	    }
	    if (5 != thread.getPriority()) {
	      thread.setPriority(5);
	    }

	    return thread;
	  }

	  private ThreadGroup getRootThreadGroup()
	  {
	    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
	    while (null != threadGroup.getParent()) {
	      threadGroup = threadGroup.getParent();
	    }

	    return threadGroup;
	  }
}
