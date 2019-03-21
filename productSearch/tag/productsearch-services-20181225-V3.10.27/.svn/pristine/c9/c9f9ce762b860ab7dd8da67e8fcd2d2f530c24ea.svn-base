package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUserLoginLog;
import com.nork.system.model.search.SysUserLoginLogSearch;
/**   
 * @Title: SysUserLoginLogMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-用户信息采集Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-06-28 16:14:59
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysUserLoginLogMapper {
    int insertSelective(SysUserLoginLog record);

    int updateByPrimaryKeySelective(SysUserLoginLog record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserLoginLog selectByPrimaryKey(Integer id);
    
    int selectCount(SysUserLoginLogSearch sysUserLoginLogSearch);
    
	List<SysUserLoginLog> selectPaginatedList(
			SysUserLoginLogSearch sysUserLoginLogSearch);
			
    List<SysUserLoginLog> selectList(SysUserLoginLog sysUserLoginLog);
    
	/**
	 * 其他
	 * 
	 */
    
    
    /**
	 * 批量插入
	 */
    int insertLogBatch(List<SysUserLoginLog > logList);
}
