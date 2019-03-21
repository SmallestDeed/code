package com.nork.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.business.model.HouseSpace;
import com.nork.business.model.search.HouseSpaceSearch;

/**   
 * @Title: HouseSpaceMapper.java 
 * @Package com.nork.business.dao
 * @Description:业务-房型空间定义Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:58:14
 * @version V1.0   
 */
@Repository
@Transactional
public interface HouseSpaceMapper {
    int insertSelective(HouseSpace record);

    int updateByPrimaryKeySelective(HouseSpace record);
  
    int deleteByPrimaryKey(Integer id);
    
    int deleteByHouseId(Integer id);
        
    HouseSpace selectByPrimaryKey(Integer id);
    
    int selectCount(HouseSpaceSearch houseSpaceSearch);
    
    int selectCountByType(HouseSpaceSearch houseSpaceSearch);
    
    Integer getHouseCountBySearch(HouseSpaceSearch houseSpaceSearch);
    
    Integer getCountByType(HouseSpaceSearch houseSpaceSearch);
    
	List<HouseSpace> selectPaginatedList(
			HouseSpaceSearch houseSpaceSearch);
			
    List<HouseSpace> selectList(HouseSpace houseSpace);
    
    List<HouseSpace> selectDeduplicationList(HouseSpace houseSpace);
    
    List<HouseSpace> selectModifiedList(HouseSpace houseSpace);
	/**
	 * 其他
	 * 
	 */
    List<HouseSpace> spaceHousePicList(HouseSpaceSearch houseSpaceSearch);

    int spaceHousePicCount(HouseSpaceSearch houseSpaceSearch);
    
    Integer getLivingCountBySearch(HouseSpaceSearch houseSpaceSearch);
    
    List<HouseSpace> mySpaceHousePicList(HouseSpaceSearch houseSpaceSearch);
    
    int mySpaceHousePicCount(HouseSpaceSearch houseSpaceSearch);
    
    
	List<String> getSpaceTypeListByHouseId(Integer id);

	List<Integer> getDistinctHouseIdWhereSpaceIdIsNotNull();
}
