package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.DesignPlan;
import com.sandu.api.solution.service.DesignPlanService;
import com.sandu.service.solution.dao.DesignPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sandu-lipeiyuan
 */
@Service(value = "designPlanService")
public class DesignPlanServiceImpl implements DesignPlanService {

    @Autowired
    private DesignPlanMapper designPlanMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return designPlanMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DesignPlan record) {
        return designPlanMapper.insert(record);
    }

    @Override
    public int insertSelective(DesignPlan record) {
        return designPlanMapper.insertSelective(record);
    }

    @Override
    public DesignPlan selectByPrimaryKey(Long id) {
        return designPlanMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(DesignPlan record) {
        return designPlanMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(DesignPlan record) {
        return designPlanMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(DesignPlan record) {
        return designPlanMapper.updateByPrimaryKey(record);
    }
}
