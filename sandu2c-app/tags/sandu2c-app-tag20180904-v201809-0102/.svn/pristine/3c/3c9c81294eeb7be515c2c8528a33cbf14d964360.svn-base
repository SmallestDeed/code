package com.sandu.home.dao;

import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.HouseSpaceResult;
import com.sandu.home.model.SpaceCommon;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: SpaceCommonMapper.java
 * @Package com.sandu.home.dao
 * @Description:户型房型-通用空间表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 15:48:39
 */
@Repository
public interface SpaceCommonMapper {
    int insertSelective(SpaceCommon record);

    int updateByPrimaryKeySelective(SpaceCommon record);

    int deleteByPrimaryKey(Integer id);

    SpaceCommon selectByPrimaryKey(Integer id);

    int getHouseSpaceListCount(SpaceCommon spaceCommon);

    int getHouseSpaceListCount2(SpaceCommon spaceCommon);

    List<HouseSpaceResult> getPaginatedHouseSpaceList(SpaceCommon spaceCommon);

    List<HouseSpaceResult> getPaginatedHouseSpaceList2(SpaceCommon spaceCommon);

    List<SpaceCommon> spaceSearchList(SpaceCommon spaceCommon);

    int spaceSearchCount(SpaceCommon spaceCommon);

    /*空间智能搜素*/
    int spaceCapacityCount(SpaceCommon spaceCommon);

    List<SpaceCommon> spaceCapacityList(SpaceCommon spaceCommon);

    /**
     * 获取全屋方案图片
     * @param houseId
     * @return
     */
    BaseHouse getHousePicById(Integer houseId);
}
