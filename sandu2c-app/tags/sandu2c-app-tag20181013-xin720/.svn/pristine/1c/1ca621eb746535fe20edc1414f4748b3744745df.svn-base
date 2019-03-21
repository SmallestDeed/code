package com.sandu.home.service;

import com.sandu.home.model.HouseSpaceResult;
import com.sandu.home.model.SpaceCommon;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: SpaceCommonService.java
 * @Package com.sandu.home.service
 * @Description:户型房型-通用空间表Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 15:48:39
 */
public interface SpaceCommonService {
    /**
     * 新增数据
     *
     * @param spaceCommon
     * @return int
     */
    int add(SpaceCommon spaceCommon);

    /**
     * 更新数据
     *
     * @param spaceCommon
     * @return int
     */
    int update(SpaceCommon spaceCommon);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return SpaceCommon
     */
    SpaceCommon get(Integer id);


    /**
     * 空间搜索
     *
     * @param spaceCommon
     * @return List<SpaceCommon>
     */
    List<SpaceCommon> getSpaceSearchList(SpaceCommon spaceCommon);

    int getSpaceSearchCount(SpaceCommon spaceCommon);

    int getHouseSpaceListCount(SpaceCommon spaceCommon);


    List<HouseSpaceResult> getPaginatedHouseSpaceList(SpaceCommon spaceCommon);

    SpaceCommon setPicParams(SpaceCommon spaceCommonSingle);

    /**
     * 获取全屋空间HouseSpaceResult
     * @return
     */
    HouseSpaceResult getFullHouseSpace(Integer houseId);

    Map<Integer,Integer> idAndSpaceTypeMap(List<Integer> spaceCommonIds);
}
