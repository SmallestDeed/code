package com.sandu.rendermachine.service.user;

import com.sandu.rendermachine.model.user.SysUser;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:45 2018/4/19 0019
 * @Modified By:
 */
public interface SysUserService {

    /**
     *  获取数据详情
     * @param id
     * @return  SysUser
     */
    SysUser get(Integer id);

    /**
     * 用户登录验证
     * @param terminalImei
     * @return
     */
    boolean checkWhiteList(String terminalImei);

    /**
     * 用token来加密app端需要的key
     * @param user
     * @return
     */
    SysUser EencryptKey(SysUser user);
}
