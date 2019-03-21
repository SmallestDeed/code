package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysAuditorUser;
import com.nork.system.model.search.SysAuditorUserSearch;

/**   
 * @Title: SysAuditorUserMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-审核人员和被审核人员绑定表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-12-25 17:34:30
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysAuditorUserMapper {
    int insertSelective(SysAuditorUser record);

    int updateByPrimaryKeySelective(SysAuditorUser record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysAuditorUser selectByPrimaryKey(Integer id);
    
    int selectCount(SysAuditorUserSearch sysAuditorUserSearch);
    
	List<SysAuditorUser> selectPaginatedList(
			SysAuditorUserSearch sysAuditorUserSearch);
			
    List<SysAuditorUser> selectList(SysAuditorUser sysAuditorUser);
    
	/**
	 * 其他
	 * 
	 */
}
