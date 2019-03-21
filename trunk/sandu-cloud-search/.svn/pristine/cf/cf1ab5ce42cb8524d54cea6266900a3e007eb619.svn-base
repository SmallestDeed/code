package com.sandu.search.datasync.handler;

import com.sandu.search.common.tools.EntityUtil;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.entity.elasticsearch.constant.IndexConstant;
import com.sandu.search.entity.elasticsearch.constant.TypeConstant;
import com.sandu.search.entity.elasticsearch.dto.UpdateRequestDTO;
import com.sandu.search.entity.elasticsearch.index.RecommendationPlanIndexMappingData;
import com.sandu.search.entity.elasticsearch.response.BulkStatistics;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.service.elasticsearch.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐方案消息处理
 *
 * @date 2018/6/9
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Slf4j
@Component
public class RecommendationMessageHandler {

    private final static String CLASS_LOG_PREFIX = "Rabbit推荐方案消息处理:";

    private final ElasticSearchService elasticSearchService;

    @Autowired
    public RecommendationMessageHandler(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }


    /**
     * 新增/更新推荐方案消息处理
     *
     * @param recommendationPlanIndexMappingDataList 推荐方案索引对象
     * @return
     */
    public boolean addAndUpdate(List<RecommendationPlanIndexMappingData> recommendationPlanIndexMappingDataList) {

        if (null == recommendationPlanIndexMappingDataList || 0 >= recommendationPlanIndexMappingDataList.size()) {
            log.info(CLASS_LOG_PREFIX + "消费消息失败，消息对象为空。");
            return false;
        }

        //推荐方案增量更新
        List<RecommendationPlanIndexMappingData> recommendationPlanList = new ArrayList<>(recommendationPlanIndexMappingDataList.size());

        recommendationPlanIndexMappingDataList.forEach(recommendationPlanIndexMappingData -> {
            //判断更新类型
            List<String> fieldList = null;
            try {
                fieldList = EntityUtil.queryHasValueExcludeNameIsIdInField(recommendationPlanIndexMappingData);
            } catch (IllegalAccessException e) {
                log.warn(CLASS_LOG_PREFIX + "检查对象失败,IllegalAccessException:{}.", e);
            }

            if (null != fieldList && 0 < fieldList.size()) {
                //属性增量更新
                recommendationPlanList.add(recommendationPlanIndexMappingData);
            }
        });

        //属性增量更新
        if (null != recommendationPlanList && 0 < recommendationPlanList.size()) {
            //加入属性增量更新列表
            updateIncrRecommendationPlanInfo(recommendationPlanList);
            log.info(CLASS_LOG_PREFIX + "消费消息成功，推荐方案已加入属性增量更新列表!recommendationPlanIndexMappingDataArrayList:{},现有待更新推荐方案数据条数:{}.", JsonUtil.toJsonExcludeEmpty(recommendationPlanList), recommendationPlanList.size());
        }

        return true;
    }

    //更新增量推荐方案数据
    public boolean updateIncrRecommendationPlanInfo(List<RecommendationPlanIndexMappingData> recommendationPlanList) {

        if (null == recommendationPlanList || 0 == recommendationPlanList.size()) {
            return true;
        }

        //bulk操作列表
        List<Object> bulkUpdateList = new ArrayList<>();

        //构造更新对象
        for (RecommendationPlanIndexMappingData recommendationPlan : recommendationPlanList) {

            if (null == recommendationPlan || null == recommendationPlan.getId() || 0 == recommendationPlan.getId()) {
                continue;
            }

            //创建索引对象
            UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO(
                    IndexConstant.RECOMMENDATION_PLAN_INFO,
                    TypeConstant.TYPE_DEFAULT,
                    recommendationPlan.getId() + "",
                    JsonUtil.toJsonExcludeEmpty(recommendationPlan)
            );

            //加入待更新列表
            bulkUpdateList.add(updateRequestDTO);
        }

        //提交数据
        BulkStatistics bulkStatistics = null;
        try {
            bulkStatistics = elasticSearchService.bulk(bulkUpdateList, null);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "索引推荐方案数据异常:ElasticSearchException:{}", e);
        }
        log.info(CLASS_LOG_PREFIX + "索引推荐方案数据成功:成功索引数:{},BulkStatistics:{}", new String[]{
                bulkUpdateList.size() + "",
                null == bulkStatistics ? null : bulkStatistics.toString()
        });

        return true;
    }
}
