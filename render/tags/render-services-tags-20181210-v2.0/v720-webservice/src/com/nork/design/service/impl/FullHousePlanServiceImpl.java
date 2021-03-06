package com.nork.design.service.impl;

import com.nork.common.model.ResponseEnvelope;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.dao.FullHousePlanMapper;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.FullHouseDesignPlan;
import com.nork.design.service.FullHousePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fullHousePlanService")
public class FullHousePlanServiceImpl implements FullHousePlanService {

    @Autowired
    private FullHousePlanMapper fullHousePlanMapper;
    @Autowired
    private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;


    @Override
    public FullHouseDesignPlan get(Integer fullHousePlanId) {
        return fullHousePlanMapper.selectByPrimaryKey(fullHousePlanId);
    }


    @Override
    public ResponseEnvelope deleteFullHousePlanAndTask(Integer fullHouseId, Integer userId) {

        FullHouseDesignPlan fullHouseDesignPlan = fullHousePlanMapper.selectByPrimaryKey(fullHouseId);
        if (fullHouseDesignPlan == null) {
            return new ResponseEnvelope(false, "方案id有误，查询不到全屋方案");
        }
        if (!userId.equals(fullHouseDesignPlan.getUserId())) {
            return new ResponseEnvelope(false, "只能删除自己的方案");
        }

        fullHouseDesignPlan.setIsDeleted(1);
        try {
            fullHousePlanMapper.updateByPrimaryKey(fullHouseDesignPlan);
            AutoRenderTaskState taskState = designPlanAutoRenderMapper.getFullHouseMainTaskByNewFullHouseId(fullHouseId);
            if (taskState != null) {
                //删除主任务
                taskState.setIsDeleted(1);
                designPlanAutoRenderMapper.updateTaskStateByTaskId(taskState);
                //删除子任务
                designPlanAutoRenderMapper.deleteSubTask(taskState.getTaskId());
            }

        } catch (Exception e) {
            return new ResponseEnvelope(false, "删除我的全屋方案及任务失败" + e);
        }

        return new ResponseEnvelope(true, "删除我的全屋方案及任务成功");

    }

    @Override
    public Integer updateFullHousePlan(FullHouseDesignPlan fullHouseDesignPlan) {
        return fullHousePlanMapper.updateByPrimaryKey(fullHouseDesignPlan);
    }

    @Override
    public FullHouseDesignPlan selectPlanByVrResourceUuid(FullHouseDesignPlan fullHouseDesignPlan) {
        return fullHousePlanMapper.selectPlanByVrResourceUuid(fullHouseDesignPlan);
    }
}
