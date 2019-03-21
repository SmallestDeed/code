package com.sandu.web.user.controller;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.cache.service.RedisService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.platform.BasePlatform;
import com.sandu.render.model.AutoRenderTask;
import com.sandu.user.model.SysUserMessage;
import com.sandu.user.service.SysUserMessageService;
import com.sandu.user.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description 系统-我的消息表Controller
 * @Date:Created Administrator in 下午 3:41 2017/12/21 0021
 * @Modified By:
 */
@Controller
@RequestMapping("/v1/sysUserMessage")
public class UserMessageController {
    private final static Logger logger = LoggerFactory.getLogger(UserMessageController.class);
    private final static String CLASS_LOG_PREFIX = "[我的消息服务]";
    private final static String BRAND_WEBSITE_PLATFORM_CODE = "brand2c";
    private final static String BASE_PLATFORM_INFO = "basePlatformInfo";
    private final static Gson gson = new Gson();
    @Autowired
    private SysUserMessageService sysUserMessageService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private RedisService redisService;

    /**
     * 查询 我的消息列表
     *
     * @param sysUserMessage
     * @return
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(@ModelAttribute SysUserMessage sysUserMessage, HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请登录!");
        }
        sysUserMessage.setUserId(loginUser.getId());

        //从缓存中获取当前平台
        String basePlatformInfo = redisService.getMap(BASE_PLATFORM_INFO, BRAND_WEBSITE_PLATFORM_CODE);
        if (StringUtils.isNotBlank(basePlatformInfo)) {
            BasePlatform basePlatform = gson.fromJson(basePlatformInfo,
                    BasePlatform.class);
            sysUserMessage.setPlatformId(basePlatform.getId());
        }

        int count = sysUserMessageService.getCount(sysUserMessage);
        if (count == 0) {
            return new ResponseEnvelope<>(true, "您还没有做过渲染");
        }
        List<SysUserMessage> allMessages = sysUserMessageService.getList(sysUserMessage);
        try {
            sysUserMessageService.updateIsRead(sysUserMessage.getUserId());
        } catch (Exception e) {
            logger.error(CLASS_LOG_PREFIX, e);
            e.printStackTrace();
        }

        return new ResponseEnvelope<>(true, "success", allMessages, count);
    }

    /**
     * 查询 有没有未读的消息
     *
     * @param
     * @return
     */
    @RequestMapping("/findUnread")
    @ResponseBody
    public Object findUnread(HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请登录!");
        }
        SysUserMessage sysUserMessage = new SysUserMessage();
        sysUserMessage.setUserId(loginUser.getId());
        sysUserMessage.setIsRead(0);
        int count = sysUserMessageService.getCount(sysUserMessage);

        String platformCode = request.getHeader("Platform-Code");
        AutoRenderTask autoRenderTask = new AutoRenderTask();
        autoRenderTask.setOperationUserId(loginUser.getId());
        autoRenderTask.setPlatformCode(platformCode);

        List<AutoRenderTask> taskList = sysUserMessageService.getMyReplaceRecord(autoRenderTask);

        return new ResponseEnvelope(true, taskList, count);
    }

    /**
     * 清空所有消息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/removeAll")
    @ResponseBody
    public Object removeAll(HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请登录!");
        }
        SysUserMessage sysUserMessage = new SysUserMessage();
        sysUserMessage.setUserId(loginUser.getId());

        int count = sysUserMessageService.getCount(sysUserMessage);
        if (count <= 0) {
            return new ResponseEnvelope(false, "请确认是否有消息！");
        }

        sysUserMessageService.removeAll(sysUserMessage);

        return new ResponseEnvelope(true, "清空数据成功！");
    }


    /**
     * 删除消息
     *
     * @param sysUserMessage
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@ModelAttribute SysUserMessage sysUserMessage, HttpServletRequest request) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请登录!");
        }
        int status = sysUserMessageService.delete(sysUserMessage);
        if (status <= 0) {
            return new ResponseEnvelope(false, "删除消息失败！");
        }
        return new ResponseEnvelope(true, "删除消息成功！");
    }


}
