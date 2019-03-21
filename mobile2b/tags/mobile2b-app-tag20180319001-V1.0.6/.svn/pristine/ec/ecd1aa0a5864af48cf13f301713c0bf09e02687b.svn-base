package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.UserProductCombinationCollect;
import com.nork.product.model.search.UserProductCombinationCollectSearch;

/**   
 * @Title: UserProductCombinationCollectMapper.java 
 * @Package com.nork.porduct.dao
 * @Description:产品组合收藏-产品组合收藏Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 11:13:32
 * @version V1.0   
 */
@Repository
@Transactional
public interface UserProductCombinationCollectMapper {
    int insertSelective(UserProductCombinationCollect record);

    int updateByPrimaryKeySelective(UserProductCombinationCollect record);
  
    int deleteByPrimaryKey(Integer id);
        
    UserProductCombinationCollect selectByPrimaryKey(Integer id);
    
    int selectCount(UserProductCombinationCollectSearch userProductCombinationCollectSearch);
    
	List<UserProductCombinationCollect> selectPaginatedList(
			UserProductCombinationCollectSearch userProductCombinationCollectSearch);
			
    List<UserProductCombinationCollect> selectList(UserProductCombinationCollect userProductCombinationCollect);
    
	/**
	 * 其他
	 * 
	 */
}
