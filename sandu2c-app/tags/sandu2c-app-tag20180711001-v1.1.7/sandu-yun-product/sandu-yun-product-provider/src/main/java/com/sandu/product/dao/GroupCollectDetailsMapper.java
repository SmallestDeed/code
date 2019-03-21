package com.sandu.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.product.model.GroupCollectDetails;
import com.sandu.product.model.search.GroupCollectDetailsSearch;



/**   
 * @Title: GroupCollectDetailsMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-组合收藏明细表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:41:47
 * @version V1.0   
 */
@Repository
@Transactional
public interface GroupCollectDetailsMapper {
    int insertSelective(GroupCollectDetails record);

    int updateByPrimaryKeySelective(GroupCollectDetails record);
  
    int deleteByPrimaryKey(Integer id);
        
    GroupCollectDetails selectByPrimaryKey(Integer id);
    
    int selectCount(GroupCollectDetailsSearch groupCollectDetailsSearch);
    
	List<GroupCollectDetails> selectPaginatedList(
			GroupCollectDetailsSearch groupCollectDetailsSearch);
			
    List<GroupCollectDetails> selectList(GroupCollectDetails groupCollectDetails);

	List<GroupCollectDetails> findAllByGroupId(Integer id);

	void deleteByGroupCollectId(Integer groupCollectId);
    
	/**
	 * 其他
	 * 
	 */
}
