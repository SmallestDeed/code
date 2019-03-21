package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUserLoginLog;
import com.nork.system.model.SysUserSystemOperationLog;
import com.nork.system.model.search.SysUserSystemOperationLogSearch;

/**   
 * @Title: SysUserSystemOperationLogMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-用户系统操作记录Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-07-05 19:43:31
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysUserSystemOperationLogMapper {
    int insertSelective(SysUserSystemOperationLog record);

    int updateByPrimaryKeySelective(SysUserSystemOperationLog record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserSystemOperationLog selectByPrimaryKey(Integer id);
    
    int selectCount(SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch);
    
	List<SysUserSystemOperationLog> selectPaginatedList(
			SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch);
			
    List<SysUserSystemOperationLog> selectList(SysUserSystemOperationLog sysUserSystemOperationLog);
    
	/**
	 * 其他
	 * 
	 */
    
    /**
   	 * 批量插入
   	 */
     int insertLogBatch(List<SysUserSystemOperationLog > logList);
     
     
     /**
      * 按UUID筛选
      * */
     SysUserSystemOperationLog selectUuId(String id);
}
