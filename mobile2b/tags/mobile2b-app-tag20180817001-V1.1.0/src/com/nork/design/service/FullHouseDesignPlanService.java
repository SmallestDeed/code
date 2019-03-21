package com.nork.design.service;

import com.nork.common.model.ResponseEnvelope;

public interface FullHouseDesignPlanService {

    /**
     * 逻辑删除我的全屋、我的任务、我的消息
     * @param fullHouseId
     * @return
     */
    ResponseEnvelope deleteFullHousePlanAndTask(Integer fullHouseId,Integer userId);
}
