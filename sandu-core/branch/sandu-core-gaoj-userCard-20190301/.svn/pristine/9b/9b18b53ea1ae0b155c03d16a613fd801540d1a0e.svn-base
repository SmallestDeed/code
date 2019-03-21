package com.sandu.service.task.impl;

import com.sandu.api.task.model.AutoRenderTask;
import com.sandu.api.task.service.AutoRenderTaskService;
import com.sandu.service.task.dao.AutoRenderTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("autoRenderTaskService")
public class AutoRenderTaskServiceImpl implements AutoRenderTaskService {

    @Autowired
    private AutoRenderTaskMapper autoRenderTaskMapper;

    @Override
    public AutoRenderTask getById(Integer mainTaskId) {
        return autoRenderTaskMapper.selectById(mainTaskId);
    }

    @Override
    public int insertSelective(AutoRenderTask autoRenderTask) {
        autoRenderTaskMapper.insertSelective(autoRenderTask);
        return autoRenderTask.getId();
    }
}
