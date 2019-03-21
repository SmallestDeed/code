package com.sandu.user.service.impl;

import com.sandu.platform.BasePlatform;
import com.sandu.render.model.AutoRenderTask;
import com.sandu.system.service.BasePlatformService;
import com.sandu.user.dao.SysUserMessageMapper;
import com.sandu.user.model.SysUserMessage;
import com.sandu.user.service.SysUserMessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserMessageServiceImpl.java
 * @Package com.nork.system.service.impl
 * @Description:系统-我的消息表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2017-12-21 14:50:38
 */
@Service("sysUserMessageService")
public class SysUserMessageServiceImpl implements SysUserMessageService {
    private static Logger logger = Logger.getLogger(SysUserMessageServiceImpl.class);

    @Autowired
    private SysUserMessageMapper sysUserMessageMapper;
    @Autowired
    private BasePlatformService basePlatformService;

    /**
     * 新增数据
     *
     * @param sysUserMessage
     * @return int
     */
    @Override
    public int add(SysUserMessage sysUserMessage) {
        sysUserMessage.setIsDeleted(0);
        sysUserMessage.setIsRead(0);
        sysUserMessage.setGmtCreate(new Date());
        sysUserMessage.setGmtModified(new Date());

        sysUserMessageMapper.insertSelective(sysUserMessage);

        /*MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSuccess(sysUserMessage.getStatus()==0?false:true);
        messageResponse.setMessage(sysUserMessage.getTitle());
        messageResponse.setType(sysUserMessage.getMessageType());
        JSONObject jsonObject = JSONObject.fromObject(messageResponse);
        String message=jsonObject.toString();
        try {
            WebSocketUtils.sendMessage("app.webSocket.message", sysUserMessage.getUserId().toString(), message);
        } catch (Exception e) {
            logger.error("message websocket链接异常"+e);
            e.printStackTrace();
        }*/

        return sysUserMessage.getId();
    }

    /**
     * 更新数据
     *
     * @param sysUserMessage
     * @return int
     */
    @Override
    public int update(SysUserMessage sysUserMessage) {
        return sysUserMessageMapper.updateByPrimaryKeySelective(sysUserMessage);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(SysUserMessage sysUserMessage) {
        return sysUserMessageMapper.deleteByUserMessage(sysUserMessage);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return SysUserMessage
     */
    @Override
    public SysUserMessage get(Integer id) {
        return sysUserMessageMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param sysUserMessage
     * @return List<SysUserMessage>
     */
    @Override
    public List<SysUserMessage> getList(SysUserMessage sysUserMessage) {
        sysUserMessage.setIsDeleted(0);
        return sysUserMessageMapper.selectList(sysUserMessage);

    }

    /**
     * 清空所有数据
     *
     * @param sysUserMessage
     * @return
     */
    @Override
    public int removeAll(SysUserMessage sysUserMessage) {
        return sysUserMessageMapper.removeAll(sysUserMessage);
    }

    /**
     * 获取消息数量
     *
     * @param sysUserMessage
     * @return
     */
    public int getCount(SysUserMessage sysUserMessage) {
        sysUserMessage.setIsDeleted(0);
        return sysUserMessageMapper.selectCount(sysUserMessage);
    }

    /**
     * 设置消息为已读
     *
     * @param userId
     * @return
     */
    @Override
    public int updateIsRead(Integer userId) {
        return sysUserMessageMapper.updateIsRead(userId);
    }
    
    @Override
    public int updateIsRead(Integer userId,Integer messageType) {
        return sysUserMessageMapper.updateIsReadByUser(userId,messageType);
    }


    /**
     *  获取按时间倒序的前20个任务
     * @param autoRenderTask
     * @return
     */
    @Override
    public List<AutoRenderTask> getMyReplaceRecord(AutoRenderTask autoRenderTask) {
        String platformCode = autoRenderTask.getPlatformCode();
        BasePlatform basePlatform = basePlatformService.getByPlatformCode(platformCode);
        if (basePlatform != null) {
            autoRenderTask.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
            autoRenderTask.setPlatformId(basePlatform.getId());
        }
        autoRenderTask.setStart(0);
        autoRenderTask.setLimit(20);
        return sysUserMessageMapper.getAllReplaceRecord(autoRenderTask);
    }

}
