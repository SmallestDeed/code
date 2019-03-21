package com.sandu.api.solution.service;

import com.sandu.api.solution.model.DesignPlan;

public interface DesignPlanService  {

    int deleteByPrimaryKey(Long id);

    int insert(DesignPlan record);

    int insertSelective(DesignPlan record);

    DesignPlan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlan record);

    int updateByPrimaryKeyWithBLOBs(DesignPlan record);

    int updateByPrimaryKey(DesignPlan record);
}
