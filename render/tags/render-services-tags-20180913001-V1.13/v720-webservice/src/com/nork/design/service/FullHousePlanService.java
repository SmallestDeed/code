package com.nork.design.service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.FullHouseDesignPlan;

public interface FullHousePlanService {

    FullHouseDesignPlan get(Integer fullHousePlanId);

    /**
     * 逻辑删除我的全屋、我的任务、我的消息
     * @param fullHouseId
     * @return
     */
    ResponseEnvelope deleteFullHousePlanAndTask(Integer fullHouseId, Integer userId);
}
