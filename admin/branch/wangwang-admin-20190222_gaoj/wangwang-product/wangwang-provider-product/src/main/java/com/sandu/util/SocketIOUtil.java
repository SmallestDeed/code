package com.sandu.util;


import com.sandu.api.impush.model.PushMessageInfo;
import com.sandu.commons.gson.GsonUtil;
import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
     * 消息推送类型(系统消息)
     */
    public static final String IM_PUSH_SYSTEM_MSG_CODE = "PUSH_SYSTEM_MSG";


    private static Socket socket;

    @Autowired
    public void socket(Socket socketBean){
        socket = socketBean;
    }

    /**
     * 向事件推送消息
     * @param event
     * @param pushMessageInfo
     */
    public static void sendEventMessage(String event, PushMessageInfo pushMessageInfo) {
        try {
            logger.info("send message start =>{}" + event + "pushMessageInfo =>{}" + pushMessageInfo);
            //获取socket对象
            String pushMessage = GsonUtil.toJson(pushMessageInfo);
            socket.emit(event, pushMessage);
            logger.info("send message end");
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

}
