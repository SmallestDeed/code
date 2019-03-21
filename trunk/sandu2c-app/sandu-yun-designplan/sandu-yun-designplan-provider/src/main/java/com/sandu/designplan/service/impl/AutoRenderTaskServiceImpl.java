package com.sandu.designplan.service.impl;

import com.sandu.designplan.dao.AutoRenderTaskMapper;
import com.sandu.designplan.service.AutoRenderTaskService;
import com.sandu.render.model.vo.RenderStateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("autoRenderTaskService")
public class AutoRenderTaskServiceImpl implements AutoRenderTaskService {
    private static final String CLASS_LOG_PREFIX = "[渲染任务服务]";
    @Autowired
    private AutoRenderTaskMapper autoRenderTaskMapper;

    @Override
    public Integer getUserTaskCount(Integer userId, Integer taskType) {
        return autoRenderTaskMapper.getUserTaskCount(userId, taskType);
    }

    @Override
    public RenderStateVo getTaskStatus(Integer taskId) {
        return autoRenderTaskMapper.getTaskStatus(taskId);
    }
}
