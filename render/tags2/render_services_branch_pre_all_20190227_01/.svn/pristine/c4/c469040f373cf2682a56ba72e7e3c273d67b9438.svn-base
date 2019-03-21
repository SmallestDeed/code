package com.nork.design.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignTemplateJumpPositionRelMapper {

    /**
     * 通过两个样板房ID，找到穿透位置信息 TEMP
     * @param originId
     * @param targetId
     * @return
     */
    String getCoordinatePosition(@Param("originId") Integer originId, @Param("targetId") Integer targetId);

}
