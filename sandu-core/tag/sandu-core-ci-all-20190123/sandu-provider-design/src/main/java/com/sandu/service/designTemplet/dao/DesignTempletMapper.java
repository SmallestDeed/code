package com.sandu.service.designTemplet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.designTemplet.model.DesignTemplet;

public interface DesignTempletMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(DesignTemplet record);

    int insertSelective(DesignTemplet record);

    DesignTemplet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignTemplet record);

    int updateByPrimaryKey(DesignTemplet record);

	List<DesignTemplet> selectByHouseId(@Param("houseId") Integer houseId);
	
}