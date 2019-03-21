package com.nork.cityunion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;

/**   
 * @Title: UnionDesignPlanStoreReleaseMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟素材发布表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:43
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionDesignPlanStoreReleaseMapper {
    int insertSelective(UnionDesignPlanStoreRelease record);

    int updateByPrimaryKeySelective(UnionDesignPlanStoreRelease record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionDesignPlanStoreRelease selectByPrimaryKey(Integer id);
    
    int selectCount(UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch);
    
	List<UnionDesignPlanStoreRelease> selectPaginatedList(
			UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch);
			
    List<UnionDesignPlanStoreRelease> selectList(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease);
    
	/**
	 * 其他
	 * 
	 */
}
