package com.nork.cityunion.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.cityunion.model.UnionStorefront;
import com.nork.cityunion.model.search.UnionStorefrontSearch;

/**   
 * @Title: UnionStorefrontMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟店面信息表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:22:46
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionStorefrontMapper {
    int insertSelective(UnionStorefront record);

    int updateByPrimaryKeySelective(UnionStorefront record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionStorefront selectByPrimaryKey(Integer id);
    
    int selectCount(UnionStorefrontSearch unionStorefrontSearch);
    
	List<UnionStorefront> selectPaginatedList(
			UnionStorefrontSearch unionStorefrontSearch);
			
    List<UnionStorefront> selectList(UnionStorefront unionStorefront);
    
	/**
	 * 其他
	 * 
	 */
}
