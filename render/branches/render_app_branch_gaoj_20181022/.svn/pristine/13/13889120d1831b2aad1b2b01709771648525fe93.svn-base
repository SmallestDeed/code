package com.nork.mobile.controller;


import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.mobile.service.MobilePayRenderService;
import com.sandu.common.LoginContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiaozunping
 * @CreateDate 2017年12月18日15:31:28
 */
@Controller
@RequestMapping("/{style}/mobile/pay")
public class MobilePayRenderController {
    private final static Logger logger = LogManager.getLogger(MobilePayRenderController.class);

    @Autowired
    private MobilePayRenderService mobilePayRenderService;


    /**
     * 查询该用户的渲染记录
     *
     * @param autoRenderTask
     * @return
     */
    @RequestMapping(value = "/getMyReplaceRecord")
    @ResponseBody
    public Object getMyReplaceRecord(@RequestBody AutoRenderTask autoRenderTask, HttpServletRequest request) {
        String platformCode = request.getHeader("Platform-Code");
        logger.info("MobilePayRenderController=====platformCode=====" + platformCode);
        Map map = LoginContext.getTokenData();
        if (null == map) {
            return new ResponseEnvelope<>(false, "请重新登录！");
        }
        LoginUser loginUser = new LoginUser();
        String loginName = (String) map.get("uname");
        long id = (long) map.get("uid");
        loginUser.setId(new Long(id).intValue());
        loginUser.setLoginName(loginName);
        autoRenderTask.setOperationUserId(loginUser.getId());
        autoRenderTask.setPlatformCode(platformCode);
        return mobilePayRenderService.getAllTaskByUserId(autoRenderTask);
    }


}
