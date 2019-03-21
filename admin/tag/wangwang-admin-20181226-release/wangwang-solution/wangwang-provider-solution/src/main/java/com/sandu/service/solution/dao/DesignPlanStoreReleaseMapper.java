package com.sandu.service.solution.dao;


import com.sandu.api.solution.model.DesignPlanStoreRelease;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignPlanStoreReleaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DesignPlanStoreRelease record);

    int insertSelective(DesignPlanStoreRelease record);

    DesignPlanStoreRelease selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlanStoreRelease record);

    int updateByPrimaryKey(DesignPlanStoreRelease record);

    DesignPlanStoreRelease selectByVRUUID(@Param("oldUUID") String oldUUID);
}