package com.nork.cityunion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.cityunion.model.UnionDesignPlanStoreCatory;
import com.nork.cityunion.model.search.UnionDesignPlanStoreCatorySearch;

/**   
 * @Title: UnionDesignPlanStoreCatoryMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟设计方案库类别Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:06
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionDesignPlanStoreCatoryMapper {
    int insertSelective(UnionDesignPlanStoreCatory record);

    int updateByPrimaryKeySelective(UnionDesignPlanStoreCatory record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionDesignPlanStoreCatory selectByPrimaryKey(Integer id);
    
    int selectCount(UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch);
    
	List<UnionDesignPlanStoreCatory> selectPaginatedList(
			UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch);
			
    List<UnionDesignPlanStoreCatory> selectList(UnionDesignPlanStoreCatory unionDesignPlanStoreCatory);
    
	/**
	 * 其他
	 * 
	 */
}
