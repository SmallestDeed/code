package com.nork.cityunion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.search.UnionGroupDetailsSearch;

/**   
 * @Title: UnionGroupDetailsMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟组明细表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:02
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionGroupDetailsMapper {
    int insertSelective(UnionGroupDetails record);

    int updateByPrimaryKeySelective(UnionGroupDetails record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionGroupDetails selectByPrimaryKey(Integer id);
    
    int selectCount(UnionGroupDetailsSearch unionGroupDetailsSearch);
    
	List<UnionGroupDetails> selectPaginatedList(
			UnionGroupDetailsSearch unionGroupDetailsSearch);
			
    List<UnionGroupDetails> selectList(UnionGroupDetails unionGroupDetails);
    
	/**
	 * 其他
	 * 
	 */
    
    /**
     * 根据联盟方案发布Id得到联盟分组列表信息
     * @param releaseId
     * @return
     */
    public List<UnionGroupDetails> getUnionGroupDetailsLsByReleaseId(Integer releaseId);
    
}
