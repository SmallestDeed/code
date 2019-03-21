package com.nork.design.service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.FullHouseDesignPlan;

import java.util.List;

public interface FullHouseDesignPlanService {

    /**
     * 逻辑删除我的全屋、我的任务、我的消息
     * @param fullHouseId
     * @return
     */
    ResponseEnvelope deleteFullHousePlanAndTask(Integer fullHouseId,Integer userId);

    /**
     * 修改全屋方案状态
     * @param fullHousePlanId
     * @param state
     * @return
     */
    Integer updateFullHousePlanState(Integer fullHousePlanId,Integer state);

    /**
     * 判断当前全屋方案是否还有子任务在渲染中
     * @param fullHousePlanId
     * @return
     */
    Integer getNumberOfRendingTaskByFullHousePlanId(Integer fullHousePlanId);

    FullHouseDesignPlan getByUUID(String planUUID);
}
