package com.nork.system.service.impl;

import com.google.gson.Gson;
import com.nork.system.dao.SysUserMessageMapper;
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.SysUserMessage;
import com.nork.system.service.BaseMessageRecieveService;
import com.nork.system.service.SysUserMessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;


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
    private BaseMessageRecieveService baseMessageRecieveService;

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
        //关联插入消息接收表
        BaseMessageRecieve baseMessageRecieve = new BaseMessageRecieve();
        baseMessageRecieve.setMessageId(sysUserMessage.getId());
        baseMessageRecieve.setUserId(sysUserMessage.getUserId());
        baseMessageRecieve.setIsReaded(0);
        baseMessageRecieve.setIsDeleted(0);
        baseMessageRecieve.setAtt1("1");
        baseMessageRecieve.setCreator(sysUserMessage.getCreator());
        baseMessageRecieve.setModifier(sysUserMessage.getModifier());
        baseMessageRecieve.setGmtCreate(new Date());
        baseMessageRecieve.setGmtModified(new Date());
        baseMessageRecieveService.add(baseMessageRecieve);

        return sysUserMessage.getId();
    }

   
}
