package com.sandu.rendermachine.service.user.impl;

import com.sandu.rendermachine.dao.user.SysUserMessageMapper;
import com.sandu.rendermachine.model.user.SysUserMessage;
import com.sandu.rendermachine.service.user.SysUserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:07 2018/4/18 0018
 * @Modified By:
 */
@Service("sysUserMessageService")
public class SysUserMessageServiceImpl implements SysUserMessageService {

    @Autowired
    private SysUserMessageMapper sysUserMessageMapper;

    @Override
    public int add(SysUserMessage sysUserMessage) {
        sysUserMessage.setIsDeleted(0);
        sysUserMessage.setIsRead(0);
        sysUserMessage.setGmtCreate(new Date());
        sysUserMessage.setGmtModified(new Date());

        sysUserMessageMapper.insertSelective(sysUserMessage);
        return sysUserMessage.getId();
    }
}
