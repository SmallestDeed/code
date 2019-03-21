package com.nork.cityunion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.search.UnionGroupSearch;

/**   
 * @Title: UnionGroupMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟分组表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:23:22
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionGroupMapper {
    int insertSelective(UnionGroup record);

    int updateByPrimaryKeySelective(UnionGroup record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionGroup selectByPrimaryKey(Integer id);
    
    int selectCount(UnionGroupSearch unionGroupSearch);
    
	List<UnionGroup> selectPaginatedList(
			UnionGroupSearch unionGroupSearch);
			
    List<UnionGroup> selectList(UnionGroup unionGroup);
    
	/**
	 * 其他
	 * 
	 */
}
