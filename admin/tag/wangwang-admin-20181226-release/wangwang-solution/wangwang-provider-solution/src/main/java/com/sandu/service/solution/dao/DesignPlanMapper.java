package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.DesignPlan;
import org.springframework.stereotype.Repository;

/**
 * @author sandu-lipeiyuan
 */
@Repository
public interface DesignPlanMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DesignPlan record);

    int insertSelective(DesignPlan record);

    DesignPlan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlan record);

    int updateByPrimaryKeyWithBLOBs(DesignPlan record);

    int updateByPrimaryKey(DesignPlan record);
}
