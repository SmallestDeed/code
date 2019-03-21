package com.sandu.user.dao;

import com.sandu.render.model.AutoRenderTask;
import com.sandu.user.model.SysUserMessage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @version V1.0
 * @Title: SysUserMessageMapper.java
 * @Package com.nork.system.dao
 * @Description:系统-我的消息表Mapper
 * @createAuthor pandajun
 * @CreateDate 2017-12-21 14:50:38
 */
@Repository
@Transactional
public interface SysUserMessageMapper {
    int insertSelective(SysUserMessage record);

    int updateByPrimaryKeySelective(SysUserMessage record);

    int deleteByPrimaryKey(Integer id);

    SysUserMessage selectByPrimaryKey(Integer id);

    List<SysUserMessage> selectList(SysUserMessage sysUserMessage);

    int removeAll(SysUserMessage sysUserMessage);

    int selectCount(SysUserMessage sysUserMessage);

    int updateIsRead(Integer userId);

    int deleteByUserMessage(SysUserMessage sysUserMessage);

    List<AutoRenderTask> getAllReplaceRecord(AutoRenderTask autoRenderTask);

	int updateIsReadByUser(@Param("userId")Integer userId, @Param("messageType")Integer messageType);
}
