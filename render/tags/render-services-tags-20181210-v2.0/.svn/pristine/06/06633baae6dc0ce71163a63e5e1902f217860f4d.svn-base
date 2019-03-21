package com.nork.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.home.model.HouseSpaceResult;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.search.SpaceCommonSearch;

/**   
 * @Title: SpaceCommonMapper.java 
 * @Package com.nork.home.dao
 * @Description:户型房型-通用空间表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 15:48:39
 * @version V1.0   
 */
@Repository
public interface SpaceCommonMapper {
    int insertSelective(SpaceCommon record);

    int updateByPrimaryKeySelective(SpaceCommon record);
  
    int deleteByPrimaryKey(Integer id);
        
    SpaceCommon selectByPrimaryKey(Integer id);
    
    int selectCount(SpaceCommonSearch spaceCommonSearch);
    int findAllCode(SpaceCommonSearch spaceCommonSearch);
    
	List<SpaceCommon> selectPaginatedList(
			SpaceCommonSearch spaceCommonSearch);
			
    List<SpaceCommon> selectList(SpaceCommon spaceCommon);
    
    List<SpaceCommon> selectPageSelectList(SpaceCommonSearch spaceCommonSearch);

    int selectPageSelectCount(SpaceCommonSearch spaceCommonSearch);
    
    List<HouseSpaceResult> houseSpaceList(Integer houseId);

	//int getHouseSpaceListCount(int parseInt);
    int getHouseSpaceListCount(SpaceCommon spaceCommon);
	
	//int getHouseSpaceListCount2(int parseInt);
	int getHouseSpaceListCount2(SpaceCommon spaceCommon);

	/*List<HouseSpaceResult> getPaginatedHouseSpaceList(@Param("houseId")Integer houseId, @Param("start")Integer start,
			@Param("limit")Integer limit);*/
	List<HouseSpaceResult> getPaginatedHouseSpaceList(SpaceCommon spaceCommon);
	
	/*List<HouseSpaceResult> getPaginatedHouseSpaceList2(@Param("houseId")Integer houseId, @Param("start")Integer start,
			@Param("limit")Integer limit);*/
	List<HouseSpaceResult> getPaginatedHouseSpaceList2(SpaceCommon spaceCommon);
	
	List<SpaceCommon> spaceSearchList(SpaceCommon spaceCommon);
	int spaceSearchCount(SpaceCommon spaceCommon);

	List<String> findAllName();
	
	int deleteByCode(String spaceCode);

	List<Integer> getSpaceIdsByHouseIds(@Param("houseIds") List<Integer> houseIds);

	List<SpaceCommon> getSpaceCommonIds(@Param("spaceCommonIds") List<Integer> spaceCommonIds);
	
	List<Integer> findIdListByStatus(@Param("status") int status);
	
	void updateStatus(@Param("oldStatus") int oldStatus, @Param("newStatus") int newStatus);
	/**
	 * 
	   
	 * geVaildTotalNum 方法描述：      得到可用空间的数量。除去状态等于4下架的
	   
	 * @return
	
	 * @return int    返回类型   
	   
	 * @Exception 异常对象    
	   
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public int geVaildTotalNum();

	//空间智能搜素
	int spaceCapacityCount(SpaceCommon spaceCommon);
	
	List<SpaceCommon> spaceCapacityList(SpaceCommon spaceCommon);

	/**
	 * 获得该空间的房型类别
	 */
	Integer selectSpaceFunctionIdBySpaceCommonId(@Param(value = "id") Integer id);
}
