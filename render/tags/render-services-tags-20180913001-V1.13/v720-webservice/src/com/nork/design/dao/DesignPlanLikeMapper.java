package com.nork.design.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlanLike;
import com.nork.design.model.search.DesignPlanLikeSearch;

/**   
 * @Title: DesignPlanLikeMapper.java 
 * @Package com.nork.设计方案.dao
 * @Description:设计方案点赞库-设计方案点赞Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-11-25 14:36:56
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanLikeMapper {
    int insertSelective(DesignPlanLike record);

    int updateByPrimaryKeySelective(DesignPlanLike record);
  
    int deleteByPrimaryKey(Integer id);
    
    int deleteById(DesignPlanLike designPlanLike);
        
    DesignPlanLike selectByPrimaryKey(Integer id);
    
    int selectCount(DesignPlanLikeSearch designPlanLikeSearch);
    
	List<DesignPlanLike> selectPaginatedList(
			DesignPlanLikeSearch designPlanLikeSearch);
			
    List<DesignPlanLike> selectList(DesignPlanLike designPlanLike);
    
	/**
	 * 其他
	 * 
	 */
}
