package com.sandu.rendermachine.service.user;

import com.sandu.rendermachine.model.user.SysUserMessage;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:07 2018/4/18 0018
 * @Modified By:
 */
public interface SysUserMessageService {
    /**
     * 新增数据
     *
     * @param sysUserMessage
     * @return  int
     */
    int add(SysUserMessage sysUserMessage);
}
