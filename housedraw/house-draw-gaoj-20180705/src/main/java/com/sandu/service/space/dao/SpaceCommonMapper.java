package com.sandu.service.space.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.house.model.SpaceCommon;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceCommonMapper {
	Integer deleteByPrimaryKey(Long id);

    Integer insert(SpaceCommon record);

    Integer insertSelective(SpaceCommon record);

    SpaceCommon selectByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(SpaceCommon record);

    Integer updateByPrimaryKey(SpaceCommon record);
    
    SpaceCommon getSpaceCommonBySpaceCode(String spaceCode);

    Integer deleteSpaceCommon(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);
	
	Integer emptySpaceCommon(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);

	Integer deleteHouseSpaceRelation(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);
	
	Integer emptyHouseSpaceRelation(@Param("status")Integer status, @Param("emptySpaces")List<Long> emptySpaces);
}