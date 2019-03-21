package com.nork.cityunion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
    int findMyDesignPlanStoreCount(@Param("designPlanName") String designPlanName, @Param("userId") Integer userId, @Param("start") Integer start, @Param("limit") Integer limit);

    List<UnionDesignPlanStore> findMyDesignPlanStoreList(@Param("designPlanName") String designPlanName, @Param("userId") Integer userId, @Param("start") Integer start, @Param("limit") Integer limit);

    Integer getStoreIdByStoreIds(@Param("storeIdList") List<Integer> storeIdList);

    List<Integer> findStoreIdByStoreIdsList(@Param("storeIdList") List<Integer> storeIdList);

    UnionDesignPlanStore getDesignPlanStore(@Param("designPlanId") Integer designPlanId, @Param("renderPicSmallId") Integer renderPicSmallId);

    String getRenderPicCodeByStoreId(@Param("storeId") Integer storeId);

    void deleteDesignPlanStoreByDesignPlanId(@Param("designPlanId") Integer designPlanId);

    void deleteDesignPlanStoreByDesignSceneId(@Param("designSceneId") Integer designSceneId);

    List<UnionDesignPlanStore> getStorePicByStoreIds(@Param("storeIdList") List<Integer> storeIdList);

}
