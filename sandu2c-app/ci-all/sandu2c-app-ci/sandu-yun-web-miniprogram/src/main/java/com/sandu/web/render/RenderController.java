package com.sandu.web.render;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.designplan.service.AutoRenderTaskService;
import com.sandu.render.model.vo.RenderStateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/miniprogram/render")
public class RenderController {
    private final static String CLASS_LOG_PREFIX = "[小程序渲染]";
    @Autowired
    private AutoRenderTaskService autoRenderTaskService;

    @GetMapping("/getUserTaskCount")
    public ResponseEnvelope getUserTaskCount(Integer taskType){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请登录!");
        }
        Integer count = autoRenderTaskService.getUserTaskCount(loginUser.getId(), taskType);
        return new ResponseEnvelope(true, "查询成功!", (count == null || count == 0) ? false : true, count == null ? 0 : count);
    }

    @GetMapping("/getTaskStatus")
    public ResponseEnvelope getTaskStatus(Integer taskId){
        if (taskId == null) {
            return new ResponseEnvelope(false, "参数为空!");
        }
        RenderStateVo state = autoRenderTaskService.getTaskStatus(taskId);
        if (state == null) {
            state = new RenderStateVo();
            state.setState(2);
        }
        return new ResponseEnvelope(true, "查询成功", state);
    }
}
