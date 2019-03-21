package com.sandu.design.dao;

import com.sandu.design.model.DesignRenderRoam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRenderRoamMapper {

    /**
     * 通过封面图获取
     * @return
     */
    DesignRenderRoam selectByScreenShotId(Integer screenShotId);

    /**
     * 通过渲染资源ID获取样板房ID TEMP
     * @param resRenderPicId
     * @return
     */
    Integer getDesignTemplateIdByResRenderPicId(Integer resRenderPicId);

    /**
     * 通过渲染资源ID获取渲染的相机位置    TEMP
     * @param resRenderPicId
     * @return
     */
    String getRenderCoordinatePosition(Integer resRenderPicId);

    /**
     * 通过两个样板房ID，找到穿透位置信息 TEMP
     * @param originId
     * @param targetId
     * @return
     */
    String getCoordinatePosition(@Param("originId") Integer originId, @Param("targetId") Integer targetId);

}
