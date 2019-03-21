package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUserLastLoginLog;
import com.nork.system.model.search.SysUserLastLoginLogSearch;

/**   
 * @Title: SysUserLastLoginLogMapper.java 
 * @Package com.nork.系统模块.dao
 * @Description:system-用户最后登录时间Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-07-04 10:03:13
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysUserLastLoginLogMapper {
    int insertSelective(SysUserLastLoginLog record);

    int updateByPrimaryKeySelective(SysUserLastLoginLog record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserLastLoginLog selectByPrimaryKey(Integer id);
    
    int selectCount(SysUserLastLoginLogSearch sysUserLastLoginLogSearch);
    
	List<SysUserLastLoginLog> selectPaginatedList(
			SysUserLastLoginLogSearch sysUserLastLoginLogSearch);
			
    List<SysUserLastLoginLog> selectList(SysUserLastLoginLog sysUserLastLoginLog);
    
	/**
	 * 其他
	 * 
	 */
}
