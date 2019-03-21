package com.nork.design.service;

import com.nork.common.util.MessageUtil;

/**
 * @desc 同步推荐方案
 * @auth xiaoxc
 * @data 2018/11/9
 */
public interface SyncRecommendedPlanService {

    /**
     * 同步发布方案到ES库 ---> RPC服务
     * @param planIds    多个方案Id
     * @param actionType 操作类型
     * @data 2018-11-08
     * @return
     */
    MessageUtil syncRecommendedPlanByPlanIds(String planIds, int actionType);
}
