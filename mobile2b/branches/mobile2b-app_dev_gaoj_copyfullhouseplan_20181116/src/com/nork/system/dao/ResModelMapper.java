package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.sync.model.ResEntity;
import com.nork.system.model.ResModel;
import com.nork.system.model.search.ResModelSearch;

/**   
 * @Title: ResModelMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-模型资源库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:05:22
 * @version V1.0   
 */
@Repository
@Transactional
public interface ResModelMapper {
    int insertSelective(ResModel record);

    int updateByPrimaryKeySelective(ResModel record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResModel selectByPrimaryKey(Integer id);
    
    int selectCount(ResModelSearch resModelSearch);
    
	List<ResModel> selectPaginatedList(
			ResModelSearch resModelSearch);
			
    List<ResModel> selectList(ResModel resModel);
    
	/**
	 * 其他
	 * 
	 */
    int modelPathCount(String modelPath);
    
    ResEntity selectResEntity(Integer id);
    
    /**
     * 保存ResEntity对象
     * @param resEntity
     * @return
     */
    int insertEntity(ResEntity resEntity);

	List<ResModel> getAllModelPathAndBusinessID();
}
