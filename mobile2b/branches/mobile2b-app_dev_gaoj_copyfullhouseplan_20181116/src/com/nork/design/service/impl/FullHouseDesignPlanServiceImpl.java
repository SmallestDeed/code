package com.nork.design.service.impl;

import com.nork.common.model.ResponseEnvelope;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.dao.FullHouseDesignPlanMapper;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.FullHouseDesignPlan;
import com.nork.design.service.FullHouseDesignPlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fullHouseDesignPlanService")
public class FullHouseDesignPlanServiceImpl implements FullHouseDesignPlanService {
    private static Logger logger = Logger.getLogger(FullHouseDesignPlanServiceImpl.class);
    @Autowired
    private FullHouseDesignPlanMapper fullHouseDesignPlanMapper;
    @Autowired
    private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;


    @Override
    public ResponseEnvelope deleteFullHousePlanAndTask(Integer fullHouseId, Integer userId) {

        FullHouseDesignPlan fullHouseDesignPlan = fullHouseDesignPlanMapper.getFullHousePlanById(fullHouseId);
        if (fullHouseDesignPlan == null) {
            return new ResponseEnvelope(false, "方案id有误，查询不到全屋方案");
        }
        if (!userId.equals(fullHouseDesignPlan.getUserId())) {
            return new ResponseEnvelope(false, "只能删除自己的方案");
        }

        fullHouseDesignPlan.setIsDeleted(1);
        try {
            fullHouseDesignPlanMapper.updateByPrimaryKey(fullHouseDesignPlan);
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
    public Integer updateFullHousePlanState(Integer fullHousePlanId, Integer state) {
        FullHouseDesignPlan fullHouseDesignPlan = new FullHouseDesignPlan();
        fullHouseDesignPlan.setId(fullHousePlanId);
        fullHouseDesignPlan.setRenderState(Integer.valueOf(state));
        return fullHouseDesignPlanMapper.updateByPrimaryKey(fullHouseDesignPlan);
    }

    @Override
    public Integer getNumberOfRendingTaskByFullHousePlanId(Integer fullHousePlanId) {
        AutoRenderTask task = new AutoRenderTask();
        task.setMaxSize(1000);
        task.setNewFullHousePlanId(fullHousePlanId);
        AutoRenderTaskState taskState = new AutoRenderTaskState();
        taskState.setNewFullHousePlanId(fullHousePlanId);
        taskState.setState(Integer.valueOf(2));

        List<AutoRenderTask> taskList = designPlanAutoRenderMapper.getAutoRenderTaskList(task);
        logger.error("FullHouseDesignPlanServiceImpl ---- ：task="+task + "size==" + taskList.size());
        List<AutoRenderTaskState>taskStateList =  designPlanAutoRenderMapper.getAutoRenderTaskStateList(taskState);
        logger.error("FullHouseDesignPlanServiceImpl ---- ：taskStateList="+taskState + "size==" + taskStateList.size());
        int size = taskList.size() + taskStateList.size();
        return Integer.valueOf(size);
    }

    @Override
    public FullHouseDesignPlan getByUUID(String planUUID) {
        return fullHouseDesignPlanMapper.getFullHousePlanByUUID(planUUID);
    }

}
