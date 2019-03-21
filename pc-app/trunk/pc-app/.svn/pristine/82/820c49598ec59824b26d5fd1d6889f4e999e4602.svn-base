package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.ResTexture;
import com.nork.system.model.search.ResTextureSearch;

/**   
 * @Title: ResTextureMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-材质库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-06-30 14:10:42
 * @version V1.0   
 */
@Repository
public interface ResTextureMapper {
	@Transactional
    int insertSelective(ResTexture record);

	@Transactional
    int updateByPrimaryKeySelective(ResTexture record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResTexture selectByPrimaryKey(Integer id);
    
    int selectCount(ResTextureSearch resTextureSearch);
    
	List<ResTexture> selectPaginatedList(
			ResTextureSearch resTextureSearch);
			
    List<ResTexture> selectList(ResTexture resTexture);


	List<ResTexture> getBatchGet(ResTexture resTexture);
    
	/**
	 * 其他
	 * 
	 */
	ResTexture selectByPrimaryKey2(Integer id);
}
