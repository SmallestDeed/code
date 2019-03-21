package com.sandu.common.objectconvert.user;

import com.sandu.user.model.*;
import com.sandu.user.model.view.LoginUserVO;

/**
 * 用户对象转换类
 */
public class UserObject {

    /**
     * 用户对象转换(UserPO -> UserSO)
     *
     * @param userPO
     * @return
     */
    public static UserSO parseToUserSOFromUserPO(UserPO userPO) {
        //TODO
        return null;
    }

    /**
     * 用户对象转换(UserSO -> LoginUserVO)
     *
     * @param userSO
     * @return
     */
    public static LoginUserVO parseToLoginUserVOFromUserSO(UserSO userSO) {
        //TODO
        return null;
    }

    public static UserVo parseToUserVOFromSysUser(SysUser sysUser) {
        UserVo userVo = new UserVo();
        userVo.setIsDeleted(sysUser.getIsDeleted());
        userVo.setJob(sysUser.getJob());
        userVo.setMobile(sysUser.getMobile());
        userVo.setNickName(sysUser.getNickName());
        userVo.setPicId(sysUser.getPicId());
        userVo.setEmail(sysUser.getEmail());
        userVo.setPicPath(sysUser.getPicPath());
        userVo.setSex(sysUser.getSex());
        userVo.setUserName(sysUser.getUserName());
        userVo.setUserType(sysUser.getUserType());
        userVo.setUserId(sysUser.getId());
        return userVo;
    }

    public static SysUser parseToSysUserFromUserVo(UserVo userVo, SysUser sysUser) {
        sysUser.setIsDeleted(userVo.getIsDeleted());
        sysUser.setJob(userVo.getJob());
        sysUser.setMobile(userVo.getMobile());
        sysUser.setNickName(userVo.getNickName());
        sysUser.setPicId(userVo.getPicId());
        sysUser.setPicPath(userVo.getPicPath());
        sysUser.setSex(userVo.getSex());
        sysUser.setUserName(userVo.getUserName());
        sysUser.setUserType(userVo.getUserType());
        sysUser.setEmail(userVo.getEmail());
        return sysUser;
    }

    //转换为用户会话对象，用于缓存
    public static UserSO parseSysUserToUserSo(SysUser sysUser) {

        UserSO userSO = null;
        if (null != sysUser) {
            //初始化对象
            userSO = new UserSO();
            userSO.setUserId(sysUser.getId());
            userSO.setUserName(sysUser.getUserName());
            userSO.setUserType(sysUser.getUserType());
            userSO.setUserMobile(sysUser.getMobile());
        }
        return userSO;
    }

    /**
     * 将UserSo对象转换为LoginUser对象
     *
     * @param userSO
     * @return
     */
    public static LoginUser parseUserSoToLoginUser(UserSO userSO) {

        LoginUser loginUser = null;
        if (null != userSO) {
            //初始化对象
            loginUser = new LoginUser();
            loginUser.setId(userSO.getUserId());
        }

        return loginUser;
    }

}
