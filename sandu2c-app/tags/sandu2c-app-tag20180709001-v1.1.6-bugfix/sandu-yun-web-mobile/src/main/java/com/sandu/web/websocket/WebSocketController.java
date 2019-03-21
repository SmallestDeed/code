package com.sandu.web.websocket;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 7:04 2018/6/12 0012
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.render.model.AutoRenderTask;
import com.sandu.user.model.SysUserMessage;
import com.sandu.user.service.SysUserMessageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/6/12 0012PM 7:04
 */
@Controller
@RequestMapping("/v1/mobile2c/websocket")
public class WebSocketController {

    @Autowired
    private SysUserMessageService sysUserMessageService;

    @RequestMapping(value = "/findunreadmessage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getUnreadMessage(@Param("userId")Integer userId, HttpServletRequest request) {
        SysUserMessage sysUserMessage = new SysUserMessage();
        sysUserMessage.setUserId(userId);
        sysUserMessage.setIsRead(0);
        int count = sysUserMessageService.getCount(sysUserMessage);
        AutoRenderTask autoRenderTask = new AutoRenderTask();
        autoRenderTask.setOperationUserId(userId);
        autoRenderTask.setPlatformCode("mobile2c");
        List<AutoRenderTask> taskList = sysUserMessageService.getMyReplaceRecord(autoRenderTask);
        WebSocketServer.sendMessage(new ResponseEnvelope(true, taskList, count),userId);
        return new ResponseEnvelope(true,"",taskList);
    }
}