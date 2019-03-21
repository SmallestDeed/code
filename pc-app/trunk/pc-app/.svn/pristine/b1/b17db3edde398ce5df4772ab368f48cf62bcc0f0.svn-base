package com.nork.design.service.impl;

import com.nork.common.util.MessageUtil;
import com.nork.design.common.SyncFullSearchRecommendedPlan;
import com.nork.design.service.SyncRecommendedPlanService;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.service.design.RecommendedPlanService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @desc 同步推荐方案实现
 * @auth xiaoxc
 * @data 20181109
 */
@Service("syncRecommendedPlanService")
public class SyncRecommendedPlanServiceImpl implements SyncRecommendedPlanService {

    private static Logger logger = LoggerFactory.getLogger(SyncFullSearchRecommendedPlan.class);
    private final static String CLASS_LOG_PREFIX = "[方案同步]推荐方案信息同步ES库:";
    @Autowired
    private RecommendedPlanService recommendedPlanService;

    @Override
    public MessageUtil syncRecommendedPlanByPlanIds(String planIds, int actionType) {
        logger.error(CLASS_LOG_PREFIX + "planIds:{},actionType:{}", planIds, actionType);
        if (StringUtils.isEmpty(planIds)) {
            return new MessageUtil(false, "planId is empty!");
        }
        try {
            if (recommendedPlanService != null) {
                recommendedPlanService.syncRecommendedPlan(planIds, actionType);
            } else {
                logger.error(CLASS_LOG_PREFIX + "RPC服务recommendedPlanService为空！");
                return new MessageUtil(false, "RPC服务recommendedPlanService为空!");
            }
        } catch (ElasticSearchException e) {
            logger.error(CLASS_LOG_PREFIX + "调用RPC服务异常！e：{}", e);
            return new MessageUtil(false, "调用RPC服务异常！e：" + e);
        }
        return new MessageUtil(true);
    }
}
