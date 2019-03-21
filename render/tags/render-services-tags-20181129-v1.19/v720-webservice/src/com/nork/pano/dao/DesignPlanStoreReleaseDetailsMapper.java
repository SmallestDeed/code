package com.nork.pano.dao;

import com.nork.pano.model.DesignPlanStoreReleaseDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignPlanStoreReleaseDetailsMapper {

    int insertSelective(DesignPlanStoreReleaseDetails record);

    int updateByPrimaryKeySelective(DesignPlanStoreReleaseDetails record);

    int deleteByPrimaryKey(Integer id);

    DesignPlanStoreReleaseDetails selectByPrimaryKey(Integer id);

    /**
     * 根据分享主表ID查询详情列表
     * @param storeReleaseId
     * @return
     */
    List<DesignPlanStoreReleaseDetails> selectListByStoreReleaseId(Integer storeReleaseId);
}
