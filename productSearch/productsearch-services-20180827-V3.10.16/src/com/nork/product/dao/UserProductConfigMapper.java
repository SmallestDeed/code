package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.UserProductConfig;
import com.nork.product.model.search.UserProductConfigSearch;

/**   
 * @Title: UserProductConfigMapper.java 
 * @Package com.nork.product.dao
 * @Description:用户产品配置-用户产品配置Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-03-28 16:23:01
 * @version V1.0   
 */
@Repository
@Transactional
public interface UserProductConfigMapper {
    int insertSelective(UserProductConfig record);

    int updateByPrimaryKeySelective(UserProductConfig record);
  
    int deleteByPrimaryKey(Integer id);
        
    UserProductConfig selectByPrimaryKey(Integer id);
    
    int selectCount(UserProductConfigSearch userProductConfigSearch);
    
	List<UserProductConfig> selectPaginatedList(
			UserProductConfigSearch userProductConfigSearch);
			
	List<UserProductConfig> selectList(UserProductConfig userProductConfig);
    
	List<UserProductConfig> selectUserConfigList(UserProductConfig userProductConfig);
	/**
	 * 其他
	 * 
	 */
}
