package com.sandu.service.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.base.model.SysDictionary;

public interface SysDictionaryMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(SysDictionary record);

    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDictionary record);

    int updateByPrimaryKey(SysDictionary record);

	List<SysDictionary> getAllHouseTypeSysDictionary();

	SysDictionary selectOneByTypeAndValue(@Param("type") String type, @Param("value") Integer value);

    SysDictionary getNameByType(SysDictionary query);
}