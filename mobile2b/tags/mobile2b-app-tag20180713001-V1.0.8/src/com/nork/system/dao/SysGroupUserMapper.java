package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysGroupUser;
import com.nork.system.model.search.SysGroupUserSearch;

/**   
 * @Title: SysGroupUserMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-用户组用户表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-18 18:35:04
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysGroupUserMapper {
    int insertSelective(SysGroupUser record);

    int updateByPrimaryKeySelective(SysGroupUser record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysGroupUser selectByPrimaryKey(Integer id);
    
    int selectCount(SysGroupUserSearch sysGroupUserSearch);
    
	List<SysGroupUser> selectPaginatedList(
			SysGroupUserSearch sysGroupUserSearch);
			
    List<SysGroupUser> selectList(SysGroupUser sysGroupUser);
    
	/**
	 * 其他
	 * 
	 */
}
