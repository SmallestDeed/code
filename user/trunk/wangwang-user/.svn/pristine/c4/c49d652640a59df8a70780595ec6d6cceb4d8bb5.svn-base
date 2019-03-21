package com.sandu.service.system.dao;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.system.input.SysDictionarySearch;
import com.sandu.api.system.model.SysDictionary;

@Repository
public interface SysDictionaryDao {
	
	List<SysDictionary> selectList(SysDictionary sysDictionary);

    SysDictionary findByTypeAndValueKey(@Param("type") String type, @Param("valueKey")String valueKey);

	List<SysDictionary> selectListBySearch(SysDictionarySearch sysDictionarySearch);

    List<SysDictionary> getListByTypeAndValues(@Param("type")String userType, @Param("value")List<Integer> userTypeValue);
}
