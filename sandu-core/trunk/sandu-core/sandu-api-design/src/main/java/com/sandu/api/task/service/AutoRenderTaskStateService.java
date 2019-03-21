package com.sandu.api.task.service;

import com.sandu.api.task.model.AutoRenderTaskState;

import java.util.List;

public interface AutoRenderTaskStateService {

    AutoRenderTaskState getMainTaskStateByTaskId(Integer mainTaskId);

    int insertSelective(AutoRenderTaskState autoRenderTaskState);

    List<AutoRenderTaskState> getAllTaskStateByMainTaskId(Integer mainTaskId);

    int insertBatch(List<AutoRenderTaskState> taskStateList);
}
