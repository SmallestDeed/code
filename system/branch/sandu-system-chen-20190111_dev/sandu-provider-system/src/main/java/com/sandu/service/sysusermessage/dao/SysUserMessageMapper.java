package com.sandu.service.sysusermessage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.sysusermessage.model.SysUserMessage;

@Repository
public interface SysUserMessageMapper {

	int deleteByPrimaryKey(Long id);

	int insert(SysUserMessage record);

	int insertSelective(SysUserMessage record);

	SysUserMessage selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysUserMessage record);

	int updateByPrimaryKey(SysUserMessage record);


	int deleteByTaskId(@Param("taskId")Integer taskId);
}