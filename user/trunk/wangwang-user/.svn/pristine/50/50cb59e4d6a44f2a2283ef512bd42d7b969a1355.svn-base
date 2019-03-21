package com.sandu.common.util;

import com.sandu.api.user.model.SysUser;
import org.apache.commons.lang3.StringUtils;

public class SysUserUtil {

    /**
     * 返回用户的名称 =>{} 优先返回userName，mobile，nickName
     * @param sysUser
     * @return
     */
    public static String getUserName(SysUser sysUser){
        return StringUtils.isEmpty(sysUser.getUserName()) ? StringUtils.isEmpty(sysUser.getMobile()) ? sysUser.getNickName() : sysUser.getMobile() : sysUser.getUserName();
    }

}
