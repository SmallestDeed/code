package com.nork.cityunion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.cityunion.model.UnionDesignPlanStore;
import com.nork.cityunion.model.search.UnionDesignPlanStoreSearch;

/**   
 * @Title: UnionDesignPlanStoreMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟设计方案素材库表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:21:19
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionDesignPlanStoreMapper {
    int insertSelective(UnionDesignPlanStore record);

    int updateByPrimaryKeySelective(UnionDesignPlanStore record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionDesignPlanStore selectByPrimaryKey(Integer id);
    
    int selectCount(UnionDesignPlanStoreSearch unionDesignPlanStoreSearch);
    
	List<UnionDesignPlanStore> selectPaginatedList(
			UnionDesignPlanStoreSearch unionDesignPlanStoreSearch);
			
    List<UnionDesignPlanStore> selectList(UnionDesignPlanStore unionDesignPlanStore);
    
	/**
	 * 其他
	 * 
	 */
}
