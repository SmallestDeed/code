package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUserFans;
import com.nork.system.model.search.SysUserFansSearch;

/**   
 * @Title: SysUserFansMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-用户粉丝表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 17:34:55
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysUserFansMapper {
    int insertSelective(SysUserFans record);

    int updateByPrimaryKeySelective(SysUserFans record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserFans selectByPrimaryKey(Integer id);
    
    SysUserFans selectByObject(SysUserFans sysUserFans);
    
    int selectCount(SysUserFansSearch sysUserFansSearch);
    
	List<SysUserFans> selectPaginatedList(
			SysUserFansSearch sysUserFansSearch);
			
    List<SysUserFans> selectList(SysUserFans sysUserFans);
    
	/**
	 * 其他
	 * 
	 */
    int selectFansCount(SysUserFans sysUserFans);
}
