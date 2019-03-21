package com.sandu.search.datasync.task;

import com.sandu.search.datasync.handler.GroupProductMessageHandler;
import com.sandu.search.initialize.GroupProductIndex;
import com.sandu.search.storage.StorageComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步组合产品数据任务
 *
 * @date 20180918
 * @auth gaoj
 */
@Slf4j
@Component
public class GroupProductTask {
    private final String CLASS_LOG_PREFIX = "【同步组合产品数据任务】";

    //增量任务执行次数
    private static int incrTaskExcuteCount = 0;
    //全量任务执行次数
    private static int fullTaskExcuteCount = 0;
    //是否正在执行全量更新（作用：1.避免同时进行两个全量更新，2.避免执行全量更新的同时执行增量更新）
    public static boolean isRunningFullTask = false;

    private final GroupProductMessageHandler groupProductMessageHandler;
    private final StorageComponent storageComponent;
    private final GroupProductIndex groupProductIndex;

    @Autowired
    public GroupProductTask(GroupProductMessageHandler groupProductMessageHandler,
                            StorageComponent storageComponent,
                            GroupProductIndex groupProductIndex) {
        this.groupProductMessageHandler = groupProductMessageHandler;
        this.storageComponent = storageComponent;
        this.groupProductIndex = groupProductIndex;
    }

    /**
     * 组合产品增量更新
     */
    @Scheduled(cron = "${task.groupproduct.incr.cron}")
    public void groupProductIncrDataSyncTask() {
        long startTime = System.currentTimeMillis();

        if (0 == incrTaskExcuteCount) {
            ++incrTaskExcuteCount;
            log.info(CLASS_LOG_PREFIX + "组合产品增量更新，第一次执行任务，跳过。。。");
            return;
        }

        if (isRunningFullTask) {
            log.info(CLASS_LOG_PREFIX + "组合产品增量更新，正在执行全量更新，放弃此任务。。。");
            return;
        }

        ++incrTaskExcuteCount;
        log.info(CLASS_LOG_PREFIX + "组合产品增量更新开始。。。");
        boolean flag = groupProductMessageHandler.updateGroupProductInfo();
        if (flag) {
            log.info(CLASS_LOG_PREFIX + "组合产品增量更新成功。。。");
        } else {
            log.info(CLASS_LOG_PREFIX + "组合产品增量更新失败。。。");
        }

        log.info(CLASS_LOG_PREFIX + "组合产品增量更新完成，共耗时：{}", System.currentTimeMillis() - startTime);
    }

    /**
     * 组合产品全量更新
     */
    @Scheduled(cron = "${task.groupproduct.all.cron}")
    public void groupProductFullDataSyncTask() {
        long startTime = System.currentTimeMillis();

        if (0 == fullTaskExcuteCount) {
            ++fullTaskExcuteCount;
            log.info(CLASS_LOG_PREFIX + "组合产品全量更新，第一次执行任务，跳过。。。");
            return;
        }

        if (isRunningFullTask) {
            log.info(CLASS_LOG_PREFIX + "组合产品全量更新，正在执行全量更新，放弃此任务。。。");
            return;
        }

        isRunningFullTask = true;
        ++fullTaskExcuteCount;
        //更新缓存数据
        storageComponent.reloadAllGroupProductStorageInCache();
        log.info(CLASS_LOG_PREFIX + "组合产品全量更新开始。。。");
        try {
            groupProductIndex.syncGroupProductInfoData();
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "组合产品全量更新异常，exception：{}", e);
        } finally {
            isRunningFullTask = false;
        }
        log.info(CLASS_LOG_PREFIX + "组合产品全量更新完成。。。共耗时：{}", System.currentTimeMillis() - startTime);


    }
}
