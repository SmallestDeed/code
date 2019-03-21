package com.nork.home.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.home.model.BaseHouse;
import com.nork.home.model.BaseHouseResult;
import com.nork.home.model.search.BaseHouseSearch;


/**   
 * @Title: BaseHouseMapper.java 
 * @Package com.nork.business.dao
 * @Description:业务-户型Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:53:51
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseHouseMapper {
    int insertSelective(BaseHouse record);

    int updateByPrimaryKeySelective(BaseHouse record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseHouse selectByPrimaryKey(Integer id);
    
    int selectCount(BaseHouseSearch baseHouseSearch);
    
    int getCountBySearch(BaseHouseSearch baseHouseSearch);
    
    Integer getCountByCreator(BaseHouseSearch baseHouseSearch);
    
	List<BaseHouse> selectPaginatedList(
			BaseHouseSearch baseHouseSearch);
			
    List<BaseHouse> selectList(BaseHouse baseHouse);
    
    List<BaseHouse> selectHouseCommonCodeList(BaseHouse baseHouse);
    
    /**小区搜索*/
    List<BaseHouseResult> houseSearchList(BaseHouseResult baseHouseResult);

    /**小区搜索count*/
	int getHouseCount(BaseHouseResult baseHouseResult);

	/*列出所有小区名*/
	List<String> findAllName();

	List<Integer> getAllDistinctLivingId();

	List<Integer> getIdsBySearch(BaseHouseSearch baseHouseSearch);

	/**
	 * 根据livingId得到户型IdList
	 * @author huangsongbo
	 * @param livingId
	 * @return
	 */
	List<Integer> getHouseIdsByLivingId(Integer livingId);
	
	/**
	 * 
	 * @Title: selectUnitsName   
	 * @Description: 获取房间户型名称 </p>   
	 * @param: @param map
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	String selectUnitsName(Map<String,Object> map);
	
	/**
	 * 根据livingId得到户型IdList
	 * @author jiangwei
	 * @param id
	 * @return
	 */
	List<String> getPaginatedListMoreInfo(@Param("id")Integer id);
	
	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	int getHaveSpaceCount(BaseHouseSearch baseHouseSearch);
	/**
	 * 获取拥有空间的户型
	 * @param baseHouseSearch
	 * @return
	 */
	List<BaseHouse> getHaveSpaceList(BaseHouseSearch baseHouseSearch);
}
