package com.sandu.service.task.impl;


import com.sandu.api.task.model.AutoRenderTaskState;
import com.sandu.api.task.service.AutoRenderTaskStateService;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.task.dao.AutoRenderTaskStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("autoRenderTaskStateService")
public class AutoRenderTaskStateServiceImpl implements AutoRenderTaskStateService {

    @Autowired
    private AutoRenderTaskStateMapper autoRenderTaskStateMapper;

    @Override
    public AutoRenderTaskState getMainTaskStateByTaskId(Integer mainTaskId) {
        return autoRenderTaskStateMapper.getMainTaskStateByTaskId(mainTaskId);
    }

    @Override
    public int insertSelective(AutoRenderTaskState autoRenderTaskState) {
        return autoRenderTaskStateMapper.insertSelective(autoRenderTaskState);
    }

    @Override
    public List<AutoRenderTaskState> getAllTaskStateByMainTaskId(Integer mainTaskId) {
        return autoRenderTaskStateMapper.getAllTaskStateByMainTaskId(mainTaskId);
    }

    @Override
    public int insertBatch(List<AutoRenderTaskState> taskStateList) {
        return autoRenderTaskStateMapper.insertBatch(taskStateList);
    }

    @Override
    public Integer addMainTask(Integer fullHouseId, Integer houseId, SysUser user, Integer mainTaskId) {
        AutoRenderTaskState autoRenderTaskState = new AutoRenderTaskState();
        autoRenderTaskState.setPlanId(-1);
        autoRenderTaskState.setTemplateId(-1);
        autoRenderTaskState.setRender720(0);
        autoRenderTaskState.setState(1);
        autoRenderTaskState.setTaskId(mainTaskId);
        autoRenderTaskState.setMainTaskId(mainTaskId);
        autoRenderTaskState.setIsDeleted(0);
        autoRenderTaskState.setGmtModified(new Date());
        autoRenderTaskState.setModifier(user.getNickName());
        autoRenderTaskState.setCreator(user.getNickName());
        autoRenderTaskState.setOperationSource(1);
        autoRenderTaskState.setOperationUserId(user.getId().intValue());
        autoRenderTaskState.setOperationUserName(user.getNickName());
        autoRenderTaskState.setTaskType(0);
        autoRenderTaskState.setRenderTypesStr("2");
        autoRenderTaskState.setTaskSource(0);
        autoRenderTaskState.setPlatformId(16);
        autoRenderTaskState.setFullHousePlanId(fullHouseId);
        autoRenderTaskState.setPlanHouseType(3);
        autoRenderTaskState.setHouseId(houseId);
        autoRenderTaskState.setNewFullHousePlanId(fullHouseId);
        autoRenderTaskState.setFullHousePlanAction(1);
        return autoRenderTaskStateMapper.insertSelective(autoRenderTaskState);
    }
}
