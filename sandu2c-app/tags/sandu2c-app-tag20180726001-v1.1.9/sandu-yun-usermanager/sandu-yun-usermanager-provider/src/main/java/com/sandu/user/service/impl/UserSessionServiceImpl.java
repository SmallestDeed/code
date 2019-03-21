package com.sandu.user.service.impl;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.RedisLoginService;
import com.sandu.cache.service.RedisService;
import com.sandu.common.LoginContext;
import com.sandu.common.jwt.Jwt;
import com.sandu.common.jwt.TokenState;
import com.sandu.common.tool.StrTools;
import com.sandu.common.util.LoginUserCommonConstant;
import com.sandu.user.model.UserSO;
import com.sandu.user.model.UserTempSessionBo;
import com.sandu.user.service.UserSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


/**
 * @desc 用户会话服务
 * @date 20171017
 * @auth pengxuangang
 */
@Service("userSessionService")
public class UserSessionServiceImpl implements UserSessionService {

    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[用户会话操作]:";
    private final static Logger logger = LoggerFactory.getLogger(UserSessionServiceImpl.class);

    @Autowired
    private RedisLoginService redisLoginService;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean loginUserToCache(String key, LoginUser loginUser, int expireInSeconds) {

        //缓存用户会话----缓存的用户对象是JSON类型
        logger.info(CLASS_LOG_PREFIX + "缓存用户会话->UserSO:{}", loginUser.toString());
        //String json = gson.toJson(loginUser);
        //boolean resultStatus= redisService.set(key, json, expireInSeconds);
        boolean resultStatus2 = redisLoginService.set(key, loginUser, expireInSeconds);
        logger.info(CLASS_LOG_PREFIX + "缓存用户会话完成->resultStatus:{}", resultStatus2);

        return resultStatus2;
    }

    public LoginUser getUserFromCache(String key){
        logger.info("从缓存中获取用户信息:"+key);
//        LoginUser loginUser = (LoginUser) redisLoginService.get(key);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser ==null){
            return null;
        }
        logger.info("获取缓存用户回话完成:"+loginUser.toString());
        return loginUser;
    }

    /* @Override
    public boolean loginUserToCache(String key,UserSO userSO,int expireInSeconds) {
        //缓存用户会话----缓存的用户对象是JSON类型
        logger.info(CLASS_LOG_PREFIX + "缓存用户会话->UserSO:{}", userSO.toString());
        String json = gson.toJson(userSO);
       // boolean resultStatus = redisService.addMap(key, userSO.getSessionId(), gson.toJson(userSO));
        //redisService.expireAt(json, expireInSeconds);
        boolean resultStatus = redisService.set(key, json, expireInSeconds);
        logger.info(CLASS_LOG_PREFIX + "缓存用户会话完成->resultStatus:{}", resultStatus);

        return resultStatus;
    }*/


    @Override
    public boolean loginUserSessionToCache(UserSO userSO,String token) {
        //缓存用户会话----缓存的用户对象是JSON类型
        logger.info(CLASS_LOG_PREFIX + "缓存用户会话->UserSO:{}", userSO.toString());
        String json = gson.toJson(userSO);
        boolean resultStatus = redisService.addMap(token, userSO.getSessionId(), gson.toJson(userSO));
        redisService.expireAt(json,1);
        logger.info(CLASS_LOG_PREFIX + "缓存用户会话完成->resultStatus:{}", resultStatus);

        return resultStatus;
    }

    @Override
    public UserSO getUserSessionObjectBySessionId(String sessionId, int maxInactiveInterval) {

        //开发前端联调环境固定用户----start
        /*UserSO tempUserSO = UserObject.parseSysUserToUserSo(sysUserService.checkUserAccount("13714264914", "123"));
        if (1 == 1) {
            return tempUserSO;
        }*/
        //开发前端联调环境固定用户----end

        if (StringUtils.isEmpty(sessionId) || 0 == maxInactiveInterval) {
            return null;
        }

        //通过会话ID获取已登录用户信息----缓存的用户对象是JSON类型
        logger.info(CLASS_LOG_PREFIX + "通过会话ID获取已登录用户信息->SessuinId:{}", sessionId);
        String cacheUserObj = redisService.getMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, sessionId);
        logger.info(CLASS_LOG_PREFIX + "通过会话ID获取已登录用户信息完成->UserSo:{}", cacheUserObj);

        if (StringUtils.isEmpty(cacheUserObj)) {
            logger.warn(CLASS_LOG_PREFIX + "通过会话ID获取已登录用户信息完成--无用户会话信息:SessionId:{}", sessionId);
            return null;
        }

        //转换用户对象
        UserSO userSO = gson.fromJson(cacheUserObj, UserSO.class);

        //检查用户登录状态是否失效
        if (userSO.getCreateSessionTime() + (maxInactiveInterval * 1000) > System.currentTimeMillis()) {
            //有效时间大于当前时间---刷新会话
            logger.info(CLASS_LOG_PREFIX + "通过会话ID获取已登录用户信息完成--开始刷新会话.....");
            refreshUserSessionIntervalToCache(userSO);
            return userSO;
        } else {
            //删除缓存中已失效会话对象
            logger.info(CLASS_LOG_PREFIX + "通过会话ID获取已登录用户信息完成--开始删除缓存中已失效会话对象.....");
            logger.info(CLASS_LOG_PREFIX + ",maxInactiveInterval:"+maxInactiveInterval+",userSO.getCreateSessionTime():"+userSO.getCreateSessionTime()+",System.currentTimeMillis()"+System.currentTimeMillis());
            logoutUserSessionFromCache(userSO.getSessionId());
            return null;
        }
    }

    @Override
    public Integer getUserTypeBySessionId(String sessionId, int maxInactiveInterval) {
        UserSO userSO = this.getUserSessionObjectBySessionId(sessionId, maxInactiveInterval);
        if (null != userSO) {
            return userSO.getUserType();
        }
        return null;
    }

    @Override
    public Integer getUserIdBySessionId(String sessionId, int maxInactiveInterval) {
        UserSO userSO = this.getUserSessionObjectBySessionId(sessionId, maxInactiveInterval);
        if (null != userSO) {
            return userSO.getUserId();
        }
        return null;
    }

    /**
     * 刷新用户会话时间
     *
     * @param userSO    用户对象
     * @return
     */
    @Override
    public boolean refreshUserSessionIntervalToCache(UserSO userSO) {

        if (null != userSO && (!StringUtils.isEmpty(userSO.getSessionId()))) {
            userSO.setCreateSessionTime(System.currentTimeMillis());
            logger.info(CLASS_LOG_PREFIX + "刷新用户会话时间->UserId:{}", userSO.getUserId());
            redisService.addMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, userSO.getSessionId(), gson.toJson(userSO));
            logger.info(CLASS_LOG_PREFIX + "刷新用户会话时间成功->UserId:{}, 当前时间:{}", userSO.getUserId(), System.currentTimeMillis());
            return true;
        }
        return false;
    }

    /**
     * 刷新用户会话时间
     *
     * @param sessionId 会话ID
     * @return
     */
    @Override
    public boolean refreshUserSessionIntervalToCache(String sessionId, int maxInactiveInterval) {

        if (!StringUtils.isEmpty(sessionId)) {
            //获取用户对象
            UserSO userSO = getUserSessionObjectBySessionId(sessionId, maxInactiveInterval);
            if (null != userSO) {
                userSO.setCreateSessionTime(System.currentTimeMillis());
                logger.info(CLASS_LOG_PREFIX + "刷新用户会话时间->sessionId:{}", sessionId);
                redisService.addMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, userSO.getSessionId(), gson.toJson(userSO));
                logger.info(CLASS_LOG_PREFIX + "刷新用户会话时间成功->UserId:{}, 当前时间:{}", userSO.getUserId(), System.currentTimeMillis());
                return true;
            }
        }
        return false;
    }

    /**
     * 退出用户会话
     *
     * @param sessionId 会话ID
     * @return
     */
    @Deprecated
    @Override
    public boolean logoutUserSessionFromCache(String sessionId) {

        if (!StringUtils.isEmpty(sessionId)) {
            logger.info(CLASS_LOG_PREFIX + "删除用户会话缓存->SessionId:{}", sessionId);
            boolean delStatus = redisService.delMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, sessionId);
            //boolean delStatus = redisService.del(sessionId);
            logger.info(CLASS_LOG_PREFIX + "删除用户会话缓存完成->SessionId:{}", sessionId);
            return delStatus;
        }

        return false;
    }

    /**
     * 退出用戶登陸
     * @param key
     * @return
     */
    @Override
    public boolean loginOut(String key) {
        if (!StringUtils.isEmpty(key)) {
            logger.info(CLASS_LOG_PREFIX + "删除用户会话缓存->SessionId:{}", key);
            //boolean delStatus = redisService.delMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, sessionId);
            boolean delStatus = redisLoginService.del(key);
            logger.info(CLASS_LOG_PREFIX + "删除用户会话缓存完成->SessionId:{}", key);
            return delStatus;
        }
        return false;
    }


    @Override
    public String createTempUserSession(String sessionId, int maxInactiveInterval) {

        //查询已登录用户
        UserSO userSO = this.getUserSessionObjectBySessionId(sessionId, maxInactiveInterval);
        if (null == userSO) {
            logger.warn(CLASS_LOG_PREFIX + "生成用户临时会话失败!用户未登录.");
            return null;
        }

        //生成随机字符串
        String userVerificationCode = StrTools.generadeRandomString(32);
        logger.info(CLASS_LOG_PREFIX + "生成用户临时会话--生成随机字符串:{}", userVerificationCode);

        //构造缓存用户和有效期对象
        UserTempSessionBo userTempSessionBo = new UserTempSessionBo();
        userTempSessionBo.setUserSO(userSO);
        //有效时间为1000分钟
        userTempSessionBo.setValidTime(System.currentTimeMillis() + 100000 * 60 * 10);

        //缓存临时用户对象
        logger.info(CLASS_LOG_PREFIX + "生成用户临时会话--缓存用户临时对象:{}", gson.toJson(userTempSessionBo));
        redisService.addMap(RedisKeyConstans.USER_TEMP_SESSION_MAP, userVerificationCode, gson.toJson(userTempSessionBo));
        logger.info(CLASS_LOG_PREFIX + "生成用户临时会话--缓存用户临时对象完成randomStr:{}", userVerificationCode);

        return userVerificationCode;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    @Override
    public boolean checkTokenTimeOut(String token){
        boolean stateFlag = false;
        if(StringUtils.isEmpty(token)){
            return stateFlag;
        }
        if(!org.apache.commons.lang3.StringUtils.isEmpty(token)){
            Map<String, Object> dataMap = Jwt.validToken(token);
            TokenState state = TokenState.getTokenState((String) dataMap.get("state"));
            switch (state) {
                case VALID:
                    stateFlag = true;
                    break;
                case EXPIRED:
                    logger.error("token已过期");
                    dataMap.put("success", false);
                    break;
                case INVALID:
                    logger.error("无效的token");
                    break;
            }

        }
        return stateFlag;
    }

}
