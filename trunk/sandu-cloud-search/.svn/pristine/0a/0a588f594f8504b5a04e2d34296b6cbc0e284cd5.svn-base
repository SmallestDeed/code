package com.sandu.search.datasync.task;

import com.sandu.search.datasync.handler.ProductMessageHandler;
import com.sandu.search.initialize.ProductIndex;
import com.sandu.search.storage.StorageComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步产品分类数据任务
 *
 * @date 20171229
 * @auth pengxuangang
 */
@Slf4j
@Component
public class ProductTask {

    private final static String CLASS_LOG_PREFIX = "同步产品数据任务:";

    private final ProductIndex productIndex;
    private final StorageComponent storageComponent;
    private final ProductMessageHandler productMessageHandler;

    //增量任务执行次数
    private static int incrTaskExcuteCount = 0;
    //全量任务执行次数
    private static int fullTaskExcuteCount = 0;

    @Autowired
    public ProductTask(ProductIndex productIndex, StorageComponent storageComponent, ProductMessageHandler productMessageHandler) {
        this.productIndex = productIndex;
        this.storageComponent = storageComponent;
        this.productMessageHandler = productMessageHandler;
    }

    /**
     * 产品数据增量更新
     *
     * @date 20180324
     * @auth pengxuangang
     * @strategy 每5分钟一次(集成10分钟)
     */
    @Scheduled(fixedDelayString = "${task.product.incr}")
    public void productIncrementDataSyncTask() {

        long startTime = System.currentTimeMillis();

        //第一次任务跳过
        if (0 == incrTaskExcuteCount) {
            ++incrTaskExcuteCount;
            log.info(CLASS_LOG_PREFIX + "产品数据增量更新,第一次任务跳过...");
            return;
        }

        //更新缓存数据
        storageComponent.reloadAllStorageInCache();

        log.info(CLASS_LOG_PREFIX + "产品数据增量更新.....");
        productMessageHandler.updateProductInfo();
        log.info(CLASS_LOG_PREFIX + "产品数据增量更新完成...耗时:{}ms", (System.currentTimeMillis() - startTime));

    }

    /**
     * 产品数据全量同步
     *
     * @date 20171229
     * @auth pengxuangang
     * @strategy 每1小时一次(集成2小时)
     */
    @Scheduled(fixedDelayString = "${task.product.all}")
    public void productDataSyncTask() {
        //任务开始时间
        long startTime = System.currentTimeMillis();

        //第一次任务跳过
        if (0 == fullTaskExcuteCount) {
            ++fullTaskExcuteCount;
            log.info(CLASS_LOG_PREFIX + "产品数据全量同步,第一次任务跳过...");
            return;
        }

        //切换存储模式
        storageComponent.changeStorageMode(StorageComponent.MEMORY_MODE);

        log.info(CLASS_LOG_PREFIX + "开始全量索引产品数据....");
        productIndex.syncProductInfoData();
        log.info(CLASS_LOG_PREFIX + "开始全量索引产品数据完成...耗时:{}ms", (System.currentTimeMillis() - startTime));

        //切换存储模式
        storageComponent.changeStorageMode(StorageComponent.CACHE_MODE);
    }
}
