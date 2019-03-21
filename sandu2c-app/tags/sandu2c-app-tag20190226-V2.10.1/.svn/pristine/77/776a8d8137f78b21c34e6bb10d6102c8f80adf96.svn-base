package com.sandu.user.service;

import com.sandu.render.model.AutoRenderTask;
import com.sandu.user.model.SysUserMessage;

import java.util.List;


/**
 * @version V1.0
 * @Title: SysUserMessageService.java
 * @Package com.nork.system.service
 * @Description:系统-我的消息表Service
 * @createAuthor pandajun
 * @CreateDate 2017-12-21 14:50:38
 */
public interface SysUserMessageService {
    /**
     * 新增数据
     *
     * @param sysUserMessage
     * @return int
     */
    int add(SysUserMessage sysUserMessage);

    /**
     * 更新数据
     *
     * @param sysUserMessage
     * @return int
     */
    int update(SysUserMessage sysUserMessage);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(SysUserMessage sysUserMessage);

    /**
     * 获取数据详情
     *
     * @param id
     * @return SysUserMessage
     */
    SysUserMessage get(Integer id);

    /**
     * 所有数据
     *
     * @param sysUserMessage
     * @return List<SysUserMessage>
     */
    List<SysUserMessage> getList(SysUserMessage sysUserMessage);

    /**
     * 清空所有数据
     *
     * @param sysUserMessage
     * @return
     */
    int removeAll(SysUserMessage sysUserMessage);

    /**
     * 获取消息数量
     *
     * @param sysUserMessage
     * @return
     */
    int getCount(SysUserMessage sysUserMessage);

    /**
     * 更新用户消息为已读
     *
     * @param userId
     * @return
     */
    int updateIsRead(Integer userId);
    
    public int updateIsRead(Integer userId,Integer messageType);

    /**
     * 获取我的任务列表状态
     *
     * @param autoRenderTask
     * @return
     */
    List<AutoRenderTask> getMyReplaceRecord(AutoRenderTask autoRenderTask);
}
