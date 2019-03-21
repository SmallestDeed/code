package com.nork.system.service.impl;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.system.dao.SysUserMessageMapper;
import com.nork.system.model.BasePlatform;
import com.nork.system.model.SysUserMessage;
import com.nork.system.service.BasePlatformService;
import com.nork.system.service.SysUserMessageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.nork.system.controller.PlatformController.BASE_PLATFORM_INFO;
import static com.nork.system.controller.PlatformController.MOBILE2B_PLATFORM_CODE;

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
    private static final Gson GSON = new Gson();
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
    public int delete(Integer id) {
        return sysUserMessageMapper.deleteByPrimaryKey(id);
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
    public List<SysUserMessage> getList(SysUserMessage sysUserMessage,Integer type) {
    	List<SysUserMessage> allMessages = null;
    	if (type == 1) {
    		allMessages = sysUserMessageMapper.selectAllMessage(sysUserMessage);
		}else {
			//添加平台过滤，能看到B端所有消息
			String platformBussinessType = null;
			String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
			if (StringUtils.isNotEmpty(basePlatformInfo)) {
				BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
				platformBussinessType = basePlatform.getPlatformBussinessType();
			}
			sysUserMessage.setPlatformBussinessType(platformBussinessType);
			sysUserMessage.setIsDeleted(0);
			allMessages = sysUserMessageMapper.selectList(sysUserMessage);
		}
        return allMessages;

    }

    /**
     * 清空所有数据
     *
     * @param sysUserMessage
     * @return
     */
    @Override
    public int removeAll(SysUserMessage sysUserMessage) {
        //添加平台过滤，能看到B端所有消息
        String platformBussinessType = null;
        String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
        if (StringUtils.isNotEmpty(basePlatformInfo)) {
            BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
            platformBussinessType = basePlatform.getPlatformBussinessType();
        }
        sysUserMessage.setPlatformBussinessType(platformBussinessType);
        return sysUserMessageMapper.removeAll(sysUserMessage);
    }

    /**
     * 获取消息数量
     *
     * @param sysUserMessage
     * @return
     */
    public int getCount(SysUserMessage sysUserMessage,Integer type) {
    	Integer count = null;
    	if (type ==1) {
    		count = sysUserMessageMapper.selectAllCount(sysUserMessage);
			logger.info("获取经销商子账号和主账号的共享数据总量为="+count);
		}else {
			//添加平台过滤，能看到B端所有消息
			String platformBussinessType = null;
			String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
			if (StringUtils.isNotEmpty(basePlatformInfo)) {
				BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
				platformBussinessType = basePlatform.getPlatformBussinessType();
			}
			sysUserMessage.setPlatformBussinessType(platformBussinessType);
			sysUserMessage.setIsDeleted(0);
			count = sysUserMessageMapper.selectCount(sysUserMessage);
		}
        return count;
    }

    /**
     * 设置消息为已读
     *
     * @param sysUserMessage
     * @return
     */
    @Override
    public int updateIsRead(SysUserMessage sysUserMessage) {
        if (sysUserMessage.getUserId() == null) {
            return 0;
        }
        //添加平台过滤，能看到B端所有消息
        String platformBussinessType = null;
        String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
        if (StringUtils.isNotEmpty(basePlatformInfo)) {
            BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
            platformBussinessType = basePlatform.getPlatformBussinessType();
        }
        sysUserMessage.setPlatformBussinessType(platformBussinessType);

        return sysUserMessageMapper.updateIsRead(sysUserMessage);
    }

}
