package com.sandu.service.task.impl;


import com.sandu.api.task.model.AutoRenderTaskState;
import com.sandu.api.task.service.AutoRenderTaskStateService;
import com.sandu.service.task.dao.AutoRenderTaskStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
