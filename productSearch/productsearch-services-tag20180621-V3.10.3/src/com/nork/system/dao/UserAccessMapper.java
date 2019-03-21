package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.UserAccess;
import com.nork.system.model.search.UserAccessSearch;

/**   
 * @Title: UserAccessMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-用户访问表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-11-26 14:17:19
 * @version V1.0   
 */
@Repository
@Transactional
public interface UserAccessMapper {
    int insertSelective(UserAccess record);

    int updateByPrimaryKeySelective(UserAccess record);
  
    int deleteByPrimaryKey(Integer id);
        
    UserAccess selectByPrimaryKey(Integer id);
    
    int selectCount(UserAccessSearch userAccessSearch);
    
	List<UserAccess> selectPaginatedList(
			UserAccessSearch userAccessSearch);
			
    List<UserAccess> selectList(UserAccess userAccess);
    
	/**
	 * 其他
	 * 
	 */
}
