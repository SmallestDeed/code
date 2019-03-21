package com.sandu.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;

@Repository
public interface SysDictionaryMapper {

	int insertSelective(SysDictionary record);

    int updateByPrimaryKeySelective(SysDictionary record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysDictionary selectByPrimaryKey(Integer id);
	
    Integer getTvbenchSysDictionaryValue();
    
    List<Integer> getValueByTypeAndValueKeylist(@Param("bigType") String bigType, @Param("smallTypeList") List<String> smallTypeList);
    
    /**
	 * 根据valuekeyList查询valueList
	 * 
	 * @author huangsongbo
	 * @param smallTypeValueKeyList
	 * @return
	 */
	List<Integer> getValueListBySmallTypeValueKeyList(@Param("smallTypeValueKeyList") List<String> smallTypeValueKeyList);
    
	List<SysDictionary> selectPaginatedList(SysDictionarySearch sysDictionarySearch);
	
	SysDictionary selectSmallTypeObj(Map<String,Object> map);
	
	List<SysDictionary> selectList(SysDictionary sysDictionary);
	
}
