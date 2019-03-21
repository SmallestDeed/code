package com.sandu.system.dao;

import org.springframework.stereotype.Repository;

import com.sandu.system.model.ResFile;

@Repository
public interface ResFileMapper {

	int insertSelective(ResFile record);

	int updateByPrimaryKeySelective(ResFile record);

	int deleteByPrimaryKey(Integer id);

	ResFile selectByPrimaryKey(Integer id);
	
}
