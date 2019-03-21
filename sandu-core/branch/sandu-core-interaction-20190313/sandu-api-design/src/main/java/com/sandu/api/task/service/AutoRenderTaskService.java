package com.sandu.api.task.service;

import com.sandu.api.task.model.AutoRenderTask;

import java.util.List;

public interface AutoRenderTaskService {

    AutoRenderTask getById(Integer mainTaskId);

    int insertSelective(AutoRenderTask autoRenderTask);

    List<AutoRenderTask> getSubTaskByMainTaskId(Integer mainTaskId);

    int insertSubTaskBatch(List<AutoRenderTask> subTaskList);

    int updateMainTaskId(Integer newMainTaskId);
}
