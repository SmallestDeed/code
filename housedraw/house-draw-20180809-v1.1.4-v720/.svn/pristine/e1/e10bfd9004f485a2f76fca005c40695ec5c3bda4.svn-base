package com.sandu.task.bake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sandu.api.house.service.DrawBakeTaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * </p>
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 * </p>
 * 2018年3月29日
 */

@Slf4j
@Component
public class BakeTask {

    @Autowired
    private DrawBakeTaskService drawBakeTaskService;

    @Scheduled(initialDelay = 5 * 60 * 1000, fixedDelay = 30 * 60 * 1000)
    public void doJob() {
        log.info("######################## 开始执行重置烘焙失败任务 ########################");
        // TODO 本地不开启

        // 修复烘焙任务的相关的状态异常bug
        // 主任务烘焙中，子任务全部烘焙完成bug
        drawBakeTaskService.fix2_3Task();

        // 主任务
        drawBakeTaskService.resetTask();

        // 子任务
        drawBakeTaskService.resetTaskDetail();

        log.info("######################## 执行重置烘焙失败任务完成 ########################");
    }
}
