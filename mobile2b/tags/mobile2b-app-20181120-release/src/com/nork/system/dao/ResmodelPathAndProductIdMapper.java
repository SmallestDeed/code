package com.nork.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.ResmodelPathAndProductId;

/*
 *2017年9月1日下午4:19:50	
 *Author:ws
 *
 */
@Repository
@Transactional
public interface ResmodelPathAndProductIdMapper {
	List<ResmodelPathAndProductId> getAllProductIdToResmodelPath(@Param("start") int start, @Param("limit") int limit);
}
