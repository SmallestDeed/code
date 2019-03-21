package com.sandu.panorama.dao;

import com.sandu.panorama.model.DesignPlanStoreReleaseDetails;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 通过ID获取图片路径 TEMP
     * @param resPicId
     * @return
     */
    String getResourcePathById(@Param("resPicId") Integer resPicId, @Param("tabName") String tabName);

    /**
     * TEMP
     * @param id
     * @return
     */
    String getResourceThumbPicPath(Integer id);

    /**
     * 删除我的分享详情
     * @param storeReleaseId
     * @return
     */
    int deleteByStoreReleaseId(@Param("storeReleaseId") Integer storeReleaseId);

    /**
     * 通过主键获取某表的某列值 TEMP
     * @param tabName
     * @param columnName
     * @param id
     * @return
     */
    String getColumnValueFromTableById(@Param("tabName") String tabName, @Param("columnName") String columnName, @Param("id") Integer id);
}
