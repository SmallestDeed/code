package com.nork.imPush.utils;

import com.nork.common.spring.SpringContextUtils;
import com.nork.common.util.GsonUtil;
import com.nork.imPush.model.PushMessageInfo;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserMessage;
import com.nork.system.service.SysUserService;
import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class SocketIOUtil {

    private static Logger logger = LoggerFactory.getLogger(SocketIOUtil.class);

    /**
     * 支付回调通知事件
     */
    public static final String IM_PUSH_MSG_EVENT = "im_push_msg_event";

    /**
     * 消息推送类型
     */
    public static final String IM_PUSH_MSG_CODE = "PUSH_RENDER_MSG";

    /**
     * 向事件推送消息
     * @param event
     * @param pushMessageInfo
     */
    public static void sendEventMessage(String event, PushMessageInfo pushMessageInfo) {
        try {
            logger.error("send message start =>{}" + event + "pushMessageInfo =>{}" + pushMessageInfo);
            //获取socket对象
            Socket socket = (Socket) SpringContextUtils.getApplicationContext().getBean("socket");
            String pushMessage = GsonUtil.bean2Json(pushMessageInfo);
            socket.emit(event, pushMessage);
            logger.error("send message end");
        } catch (Exception e) {
            logger.error("发送消息异常", e);
        }
    }

    /**
     * 构造通知内容
     * @param sessionId
     * @param msgCode
     * @param messageData
     * @return
     */
    public static PushMessageInfo buildMessageInfo(String sessionId, String msgCode, String messageData){
         PushMessageInfo messageInfo = new PushMessageInfo();
         messageInfo.setTargetSessionId(sessionId);
         messageInfo.setMsgCode(msgCode);
         messageInfo.setMsgContent(messageData);
         return  messageInfo;
    }

    /**
     * 处理通知消息
     * @param event
     * @param msgCode
     * @param sessionId
     * @param messageInfo
     */
    public static void handlerSendMessage(String event,String msgCode,String sessionId,String messageInfo){
        sendEventMessage(event,buildMessageInfo(sessionId,msgCode,messageInfo));
    }

    /**
     * 封装渲染完成通知
     * @param sysUserMessage
     */
    public static void adviceRenderResultToUser(SysUserMessage sysUserMessage){
        SysUserService sysUserService =(SysUserService) SpringContextUtils.getApplicationContext().getBean("sysUserService");
        SysUser sysUser = sysUserService.get(sysUserMessage.getUserId());
        if (Objects.isNull(sysUser)){
            logger.error("获取用户信息失败,不发送消息");
            return;
        }
        logger.error("send to targetUUID =>{}"+sysUser.getUuid());
        SocketIOUtil.handlerSendMessage(SocketIOUtil.IM_PUSH_MSG_EVENT,SocketIOUtil.IM_PUSH_MSG_CODE,sysUser.getUuid(), GsonUtil.bean2Json(sysUserMessage));
    }

}
