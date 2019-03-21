package com.sandu.api.task.service;

import com.sandu.api.task.model.AutoRenderTaskState;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AutoRenderTaskStateService {

    AutoRenderTaskState getMainTaskStateByTaskId(Integer mainTaskId);

    int insertSelective(AutoRenderTaskState autoRenderTaskState);

    List<AutoRenderTaskState> getAllTaskStateByMainTaskId(Integer mainTaskId);

    int insertBatch(List<AutoRenderTaskState> taskStateList);

    Integer addMainTask(Integer fullHouseId, Integer houseId, SysUser user, Integer mainTaskId);
}
