package com.nork.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.UserProductCollect;
import com.nork.product.model.UserProductsConllect;
import com.nork.product.model.search.UserProductCollectSearch;

/**   
 * @Title: UserProductCollectMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-我的产品收藏Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 18:27:21
 * @version V1.0   
 */
@Repository
@Transactional
public interface UserProductCollectMapper {
    int insertSelective(UserProductCollect record);

    int updateByPrimaryKeySelective(UserProductCollect record);
  
    int deleteByPrimaryKey(Integer id);
    
    Integer cancelConllect(UserProductCollect userProductCollect);
        
    UserProductCollect selectByPrimaryKey(Integer id);
    
    int selectCount(UserProductCollectSearch userProductCollectSearch);
    
	List<UserProductCollect> selectPaginatedList(UserProductCollectSearch userProductCollectSearch);
			
    List<UserProductCollect> selectList(UserProductCollect userProductCollect);
    
    UserProductCollect getUserProductConllect(UserProductCollect upc);
    
    List<UserProductsConllect> getUserProductsConllectList(UserProductsConllect userProductsConllect);
    List<UserProductsConllect> getAllList(UserProductsConllect userProductsConllect);
    
    int getUserProductsConllectCount(UserProductsConllect userProductsConllect);
    
    List<UserProductsConllect> selectUserNamelist(UserProductsConllect userProductsConllect);

	void transferCollection(UserProductCollectSearch userProductCollectSearch);

	List getUserProductsCollectByValue(List<Map<Object, Object>> valueList);

	int getUserProductsConllectCount_All(
			UserProductsConllect userProductsCollect);

	List<UserProductsConllect> getAllList_All(
			UserProductsConllect userProductsCollect);
    
	/**
	 * 其他
	 * 
	 */
	/**
	 * 批量删除
	 * */
	int deleteBatch(List<Integer> list);
}
