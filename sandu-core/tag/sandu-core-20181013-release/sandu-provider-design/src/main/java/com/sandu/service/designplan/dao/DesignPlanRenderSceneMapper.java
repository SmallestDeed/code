package com.sandu.service.designplan.dao;

import com.sandu.api.designplan.model.DesignPlanRenderScene;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignPlanRenderSceneMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DesignPlanRenderScene record);

    int insertSelective(DesignPlanRenderScene record);

    DesignPlanRenderScene selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlanRenderScene record);

    int updateByPrimaryKey(DesignPlanRenderScene record);
}