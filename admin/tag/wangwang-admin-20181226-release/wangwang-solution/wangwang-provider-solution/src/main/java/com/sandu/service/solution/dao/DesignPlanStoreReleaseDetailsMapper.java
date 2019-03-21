package com.sandu.service.solution.dao;


import com.sandu.api.solution.model.DesignPlanStoreReleaseDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignPlanStoreReleaseDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DesignPlanStoreReleaseDetails record);

    int insertSelective(DesignPlanStoreReleaseDetails record);

    DesignPlanStoreReleaseDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlanStoreReleaseDetails record);

    int updateByPrimaryKey(DesignPlanStoreReleaseDetails record);

    List<DesignPlanStoreReleaseDetails> selectByStoreId(@Param("initId") Long initId);
}