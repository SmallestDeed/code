package com.sandu.listener;

import com.google.gson.Gson;
import com.sandu.cache.RedisKeyConstans;
import com.sandu.cache.service.RedisService;
import com.sandu.common.util.SpringContextHolder;
import com.sandu.user.model.UserSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 用户会话监听---用于销毁缓存中已失效用户
 *
 * @date 20171101
 * @auth pengxuangang
 */
public class UserSessionListener implements HttpSessionListener {

    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[用户会话监听服务]:";
    private static Logger logger = LoggerFactory.getLogger(UserSessionListener.class);
    private static RedisService redisService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    //销毁用户
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

        if (null == redisService) {
            //加载缓存服务
            redisService = SpringContextHolder.getBean(RedisService.class);
        }

        //SessionId
        String sessionId = event.getSession().getId();
        logger.info(CLASS_LOG_PREFIX + "准备销毁缓存中用户会话 SessionId:{}", sessionId);
        //检查是否有用户存在
        String userSoJson = redisService.getMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, sessionId);

        if (!StringUtils.isEmpty(userSoJson)) {
            UserSO userSO = gson.fromJson(userSoJson, UserSO.class);
            if (null != userSO) {
                logger.info(CLASS_LOG_PREFIX + "准备销毁缓存中用户会话:用户ID:{},用户对象:{}", userSO.getUserId(), userSO.toString());
                //删除缓存中用户会话
                boolean delUserSessionStatus = redisService.delMap(RedisKeyConstans.SESSION_USER_OBJECT_SET, sessionId);
                logger.info(CLASS_LOG_PREFIX + "销毁缓存中用户会话完成,销毁状态:{}", delUserSessionStatus);
            }
        } else {
            logger.warn(CLASS_LOG_PREFIX + "销毁缓存中用户会话完成,缓存中用户会话不存在! SessionId:{}", sessionId);
        }
    }
}
