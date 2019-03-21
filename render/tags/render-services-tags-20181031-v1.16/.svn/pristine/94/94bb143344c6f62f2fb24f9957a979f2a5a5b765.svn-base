package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.GroupProductCollect;
import com.nork.product.model.search.GroupProductCollectSearch;

/**   
 * @Title: GroupProductCollectMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-组合收藏表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 19:46:06
 * @version V1.0   
 */
@Repository
@Transactional
public interface GroupProductCollectMapper {
    int insertSelective(GroupProductCollect record);

    int updateByPrimaryKeySelective(GroupProductCollect record);
  
    int deleteByPrimaryKey(Integer id);
        
    GroupProductCollect selectByPrimaryKey(Integer id);
    
    int selectCount(GroupProductCollectSearch groupProductCollectSearch);
    
	List<GroupProductCollect> selectPaginatedList(
			GroupProductCollectSearch groupProductCollectSearch);
			
    List<GroupProductCollect> selectList(GroupProductCollect groupProductCollect);
    
	/**
	 * 其他
	 * 
	 */
    
    int groupProductCollectCount(GroupProductCollectSearch groupProductCollectSearch);
    
    List<GroupProductCollect> groupProductCollectList(
			GroupProductCollectSearch groupProductCollectSearch);
}
