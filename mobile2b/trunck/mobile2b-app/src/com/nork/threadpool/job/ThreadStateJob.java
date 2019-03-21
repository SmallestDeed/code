package com.nork.threadpool.job;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.nork.threadpool.ThreadStateInfo;
import com.nork.threadpool.ThreadUtil;

/**
 * 收集所有线程组中所有线程的状态信息，统计并输出汇总信息。
 * 
 * 
 */
public class ThreadStateJob extends AbstractJob {

    private static Logger _logger = Logger.getLogger(ThreadStateJob.class);
    
    public ThreadStateJob(int interval) {
        super._interval = interval;
    }

    @Override
    protected void execute() {
        Map<String, ThreadStateInfo> statMap = ThreadUtil.statAllGroupThreadState();
        
        for (Entry<String, ThreadStateInfo> entry : statMap.entrySet()) {
            ThreadStateInfo stateInfo = entry.getValue();
            StringBuilder strB = new StringBuilder("ThreadGroup:");
            strB.append(entry.getKey());
            strB.append("New:");
            strB.append(stateInfo.getNewCount());
            strB.append("Runnable:");
            strB.append(stateInfo.getRunnableCount());
            strB.append("Blocked:");
            strB.append(stateInfo.getBlockedCount());
            strB.append("Waiting:");
            strB.append(stateInfo.getWaitingCount());
           
        }
        
        super.sleep();
    } // end of execute

}
