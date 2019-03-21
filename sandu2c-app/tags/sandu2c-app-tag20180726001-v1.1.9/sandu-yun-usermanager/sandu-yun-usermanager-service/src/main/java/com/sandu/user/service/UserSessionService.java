package com.sandu.user.service;


import com.nork.common.model.LoginUser;
import com.sandu.user.model.UserSO;

/**
 * @desc 用户会话服务
 * @date 20171017
 * @auth pengxuangang
 */
public interface UserSessionService {


    public boolean loginUserToCache(String key, LoginUser loginUser, int expireInSeconds);

    /**
     * 登录用户缓存会话
     *
     * @param userSO 用户会话对象
     * @return
     */
    boolean loginUserSessionToCache(UserSO userSO, String token);

    /***
     * 从缓存中获取用户会话
     * @param key
     * @return
     */
    LoginUser getUserFromCache(String key);

    /**
     * 通过会话ID获取已登录用户信息
     *
     * @param sessionId           会话ID
     * @param maxInactiveInterval 最大的不活动的间隔时间(second)--[Demo:request.getSession().getMaxInactiveInterval()]
     * @return
     */
    UserSO getUserSessionObjectBySessionId(String sessionId, int maxInactiveInterval);

    /**
     * 通过会话ID获取用户类型
     *
     * @param sessionId           会话ID
     * @param maxInactiveInterval 最大的不活动的间隔时间(second)--[Demo:request.getSession().getMaxInactiveInterval()]
     * @return
     */
    Integer getUserTypeBySessionId(String sessionId, int maxInactiveInterval);

    /**
     * 通过会话ID获取用户ID
     *
     * @param sessionId           会话ID
     * @param maxInactiveInterval 最大的不活动的间隔时间(second)--[Demo:request.getSession().getMaxInactiveInterval()]
     * @return
     */
    Integer getUserIdBySessionId(String sessionId, int maxInactiveInterval);

    /**
     * 退出用户会话
     *
     * @param sessionId 会话ID
     * @return
     */
    boolean logoutUserSessionFromCache(String sessionId);

    /**
     * 用户退出登录
     *
     * @param key
     * @return
     */
    boolean loginOut(String key);

    /**
     * 生成用户临时会话---用于支付系统做身份认证
     *
     * @param sessionId           会话ID
     * @param maxInactiveInterval 最大的不活动的间隔时间(second)--[Demo:request.getSession().getMaxInactiveInterval()]
     * @return 用户临时身份字符串
     */
    String createTempUserSession(String sessionId, int maxInactiveInterval);


    /**
     * 刷新用户会话
     *
     * @param sessionId           会话ID
     * @param maxInactiveInterval 最大的不活动的间隔时间(second)--[Demo:request.getSession().getMaxInactiveInterval()]
     * @return 是否刷新成功
     */
    boolean refreshUserSessionIntervalToCache(String sessionId, int maxInactiveInterval);

    /**
     * 刷新用户会话
     *
     * @param userSo 用户对象
     * @return 是否刷新成功
     */
    boolean refreshUserSessionIntervalToCache(UserSO userSo);

    /**
     * 判断token否过期
     *
     * @param token
     * @return
     */
    boolean checkTokenTimeOut(String token);

}
