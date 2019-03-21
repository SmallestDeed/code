package com.sandu.api.task.service;

import com.sandu.api.task.model.AutoRenderTask;

public interface AutoRenderTaskService {

    AutoRenderTask getById(Integer mainTaskId);

    int insertSelective(AutoRenderTask autoRenderTask);
}
