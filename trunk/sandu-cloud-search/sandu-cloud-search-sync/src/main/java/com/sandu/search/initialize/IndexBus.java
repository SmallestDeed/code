package com.sandu.search.initialize;

import com.sandu.search.config.ElasticSearchConfig;
import com.sandu.search.storage.StorageComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 索引总线
 *
 * @date 2018/6/21
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Slf4j
@Component
public class IndexBus {

    private final static String CLASS_LOG_PREFIX = "搜索引擎索引总线:";

    private final ProductIndex productIndex;
    private final GroupProductIndex groupProductIndex;
    private final StorageComponent storageComponent;
    private final ElasticSearchConfig elasticSearchConfig;
    private final RecommendationPlanIndex recommendationPlanIndex;

    @Autowired
    public IndexBus(ProductIndex productIndex, GroupProductIndex groupProductIndex, StorageComponent storageComponent, ElasticSearchConfig elasticSearchConfig, RecommendationPlanIndex recommendationPlanIndex) {
        this.productIndex = productIndex;
        this.groupProductIndex = groupProductIndex;
        this.storageComponent = storageComponent;
        this.elasticSearchConfig = elasticSearchConfig;
        this.recommendationPlanIndex = recommendationPlanIndex;
    }

    //初始化数据
    @PostConstruct
    public void initDataHub() {

        //是否需要索引推荐方案数据
        boolean indexRecommendationPlanData = elasticSearchConfig.isIndexRecommendationPlanData();
        log.info(CLASS_LOG_PREFIX + "是否需要索引推荐方案数据:{}", indexRecommendationPlanData);
        //是否需要索引产品数据
        boolean indexProductData = elasticSearchConfig.isIndexProductData();
        log.info(CLASS_LOG_PREFIX + "是否需要索引产品数据:{}", indexProductData);
        //是否需要索引组合产品数据
        boolean indexGroupProductData = elasticSearchConfig.isIndexGroupProductData();
        log.info(CLASS_LOG_PREFIX + "是否需要索引组合产品数据:{}", indexGroupProductData);

        //是否索引
        if (indexRecommendationPlanData || indexProductData || indexGroupProductData) {
            log.info(CLASS_LOG_PREFIX + "切换存储模式为内存...");
            //切换存储模式
            storageComponent.changeStorageMode(StorageComponent.MEMORY_MODE);
            //索引数据
            if (indexRecommendationPlanData) {
                //同步推荐方案数据
                log.info(CLASS_LOG_PREFIX + "开始同步推荐方案数据...");
                recommendationPlanIndex.syncRecommendationPlanData();
            }

            //索引数据
            if (indexProductData) {
                //同步产品信息数据
                log.info(CLASS_LOG_PREFIX + "开始同步产品信息数据...");
                productIndex.syncProductInfoData();
            }

            //索引数据
            if (indexGroupProductData) {
                //同步组合产品信息数据
                log.info(CLASS_LOG_PREFIX + "开始同步组合产品信息数据...");
                groupProductIndex.syncGroupProductInfoData();
            }

            //切换存储模式
            log.info(CLASS_LOG_PREFIX + "切换存储模式为缓存...");
            storageComponent.changeStorageMode(StorageComponent.CACHE_MODE);
        } else {
            //不索引数据时则更新缓存数据
            //初始化元数据
            storageComponent.startApplicationLoadMetaData();
        }

        log.info(CLASS_LOG_PREFIX + "初始化索引数据完成....");
    }
}
