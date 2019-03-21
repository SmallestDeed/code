package com.sandu.search.datasync.task;

import com.sandu.search.initialize.RecommendationPlanIndex;
import com.sandu.search.storage.StorageComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步推荐方案数据任务
 *
 * @date 20171229
 * @auth pengxuangang
 */
@Slf4j
@Component
public class RecommendationTask {

    private final static String CLASS_LOG_PREFIX = "同步推荐方案数据任务:";

    private final StorageComponent storageComponent;
    private final RecommendationPlanIndex recommendationPlanIndex;

    //全量任务执行次数
    private static int fullTaskExcuteCount = 0;

    @Autowired
    public RecommendationTask(StorageComponent storageComponent, RecommendationPlanIndex recommendationPlanIndex) {
        this.storageComponent = storageComponent;
        this.recommendationPlanIndex = recommendationPlanIndex;
    }

    /**
     * 推荐方案数据全量同步
     *
     * @date 2018/6/1
     * @auth pengxuangang
     * @mail xuangangpeng@gmail.com
     * @strategy 每半小时一次
     */
    @Scheduled(fixedDelayString = "${task.recommendationplan.all}")
    public void recommendationPlanDataSyncTask() {
        //任务开始时间
        long startTime = System.currentTimeMillis();

        //第一次任务跳过
        if (0 == fullTaskExcuteCount) {
            ++fullTaskExcuteCount;
            log.info(CLASS_LOG_PREFIX + "推荐方案数据全量同步,第一次任务跳过...");
            return;
        }

        //更新缓存数据
        storageComponent.reloadAllStorageInCache();

        log.info(CLASS_LOG_PREFIX + "开始全量索引推荐方案数据....");
        recommendationPlanIndex.syncRecommendationPlanData();
        log.info(CLASS_LOG_PREFIX + "开始全量索引推荐方案数据完成...耗时:{}ms", (System.currentTimeMillis() - startTime));
    }
}
