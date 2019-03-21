package com.nork.cityunion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.cityunion.model.UnionDesignPlanStoreReleaseDetails;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseDetailsSearch;

/**   
 * @Title: UnionDesignPlanStoreReleaseDetailsMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟素材发布明细表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:26:23
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionDesignPlanStoreReleaseDetailsMapper {
    int insertSelective(UnionDesignPlanStoreReleaseDetails record);

    int updateByPrimaryKeySelective(UnionDesignPlanStoreReleaseDetails record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionDesignPlanStoreReleaseDetails selectByPrimaryKey(Integer id);
    
    int selectCount(UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch);
    
	List<UnionDesignPlanStoreReleaseDetails> selectPaginatedList(
			UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch);
			
    List<UnionDesignPlanStoreReleaseDetails> selectList(UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails);
    
	/**
	 * 其他
	 * 
	 */
    int updateByReleaseId(UnionDesignPlanStoreReleaseDetails releaseDetails);

    Integer deleteStoreReleaseDetailsByDesignPlanId(@Param("designPlanId") Integer designPlanId);

    Integer deleteStoreReleaseDetailsByDesignSceneId(@Param("designSceneId") Integer designSceneId);
}
