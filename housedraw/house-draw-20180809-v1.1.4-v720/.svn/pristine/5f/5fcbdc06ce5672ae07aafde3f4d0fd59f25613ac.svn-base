package com.sandu.task.res;

import com.sandu.api.task.service.CleanDrawResFileTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@sanduspace.cn
 * @date 2018/07/24
 */

@Slf4j
@Component
public class CleanDrawResFileTask {

    @Autowired
    CleanDrawResFileTaskService cleanDrawResFileTaskService;

    /**
     * 每晚2-5点隔20分钟执行一次
     */
    @Scheduled(cron = "${clean.task.cron:0 0/20 2,3,4,5 * * ?}")
    public void doJob() {
        long startup = System.currentTimeMillis();
        log.info("######################## 开始执行清除资源定时任务 ########################");

        cleanDrawResFileTaskService.cleanDrawResFile();

        log.info("######################## 执行清除资源定时任务结束 ########################");
        log.info("########################## 处理清除资源耗时：{}(ms) #########################", System.currentTimeMillis() - startup);
    }
}
