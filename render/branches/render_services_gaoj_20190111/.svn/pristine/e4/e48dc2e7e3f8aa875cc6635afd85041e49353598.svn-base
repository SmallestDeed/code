package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.CollectCatalog;
import com.nork.product.model.search.CollectCatalogSearch;

/**   
 * @Title: CollectCatalogMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品管理-收藏目录表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-07-01 10:46:26
 * @version V1.0   
 */
@Repository
@Transactional
public interface CollectCatalogMapper {
    int insertSelective(CollectCatalog record);

    int updateByPrimaryKeySelective(CollectCatalog record);
  
    int deleteByPrimaryKey(Integer id);
        
    CollectCatalog selectByPrimaryKey(Integer id);
    
    int selectCount(CollectCatalogSearch collectCatalogSearch);
    
	List<CollectCatalog> selectPaginatedList(
			CollectCatalogSearch collectCatalogSearch);
			
    List<CollectCatalog> selectList(CollectCatalog collectCatalog);
    
	/**
	 * 其他
	 * 
	 */
}
