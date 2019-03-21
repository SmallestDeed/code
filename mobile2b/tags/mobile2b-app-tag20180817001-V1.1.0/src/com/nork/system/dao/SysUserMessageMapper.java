package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.SysUserMessage;

/**   
 * @Title: SysUserMessageMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-我的消息表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-12-21 14:50:38
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysUserMessageMapper {
    int insertSelective(SysUserMessage record);

    int updateByPrimaryKeySelective(SysUserMessage record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysUserMessage selectByPrimaryKey(Integer id);
			
    List<SysUserMessage> selectList(SysUserMessage sysUserMessage);
    
    List<SysUserMessage> selectAllMessage(SysUserMessage sysUserMessage);
    
    int removeAll(SysUserMessage sysUserMessage);

    int selectCount(SysUserMessage sysUserMessage);
    
    int selectAllCount(SysUserMessage sysUserMessage);

    int updateIsRead(SysUserMessage sysUserMessage);
}
