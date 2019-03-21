package com.sandu.service.designplan.impl;

import com.sandu.api.designplan.model.DesignPlanRenderScene;
import com.sandu.api.designplan.service.DesignPlanRenderSceneService;
import com.sandu.service.designplan.dao.DesignPlanRenderSceneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("designPlanRenderSceneService")
public class DesignPlanRenderSceneServiceImpl implements DesignPlanRenderSceneService {
    @Autowired
    private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return designPlanRenderSceneMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.insert(record);
    }

    @Override
    public int insertSelective(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.insertSelective(record);
    }

    @Override
    public DesignPlanRenderScene selectByPrimaryKey(Long id) {
        return designPlanRenderSceneMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(DesignPlanRenderScene record) {
        return designPlanRenderSceneMapper.updateByPrimaryKey(record);
    }
}
