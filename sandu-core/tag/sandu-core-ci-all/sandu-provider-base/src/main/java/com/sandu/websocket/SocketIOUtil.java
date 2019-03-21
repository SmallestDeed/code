package com.sandu.websocket;


import com.sandu.api.base.model.PushMessageInfo;
import io.socket.client.Socket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketIOUtil {

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


    /**
     * 评论消息推送
     */
    public static final String IM_PUSH_REVIEWS_MSG_CODE = "PUSH_REVIEWS_MSG";

    /**
     * 互动消息推送
     */
    public static final String IM_PUSH_INTERACTIVE_MSG_CODE = "PUSH_INTERACTIVE_MSG";

    /**
     * 用户名片访问消息
     */
    public static final String IM_PUSH_CARD_ACCESS_MSG_CODE = "PUSH_CARD_ACCESS_MSG";

    /**
     * 用户名片访问操作日志消息
     */
    public static final String IM_PUSH_CARD_ACCESS_OPERATION_LOG_MSG_CODE = "PUSH_CARD_ACCESS_OPERATION_LOG_MSG";


    private static Socket socket;

    @Autowired
    public void socket(Socket socketBean) {
        socket = socketBean;
    }

    /**
     * 向事件推送消息
     *
     * @param event
     * @param pushMessageInfo
     */
    public static void sendEventMessage(String event, PushMessageInfo pushMessageInfo) {
        try {
            log.info("send message start =>event:{}，pushMessageInfo:{}", event, pushMessageInfo);
            //获取socket对象
            String pushMessage = GsonUtil.toJson(pushMessageInfo);
            socket.emit(event, pushMessage);
            log.info("send message end");
        } catch (Exception e) {
            log.error("发送消息异常", e);
        }
    }

    /**
     * 构造通知内容
     *
     * @param sessionId
     * @param msgCode
     * @param messageData
     * @return
     */
    public static PushMessageInfo buildMessageInfo(String sessionId, String msgCode, String messageData) {
        PushMessageInfo messageInfo = new PushMessageInfo();
        messageInfo.setTargetSessionId(sessionId);
        messageInfo.setMsgCode(msgCode);
        messageInfo.setMsgContent(messageData);
        return messageInfo;
    }

    /**
     * 处理通知消息
     *
     * @param event
     * @param msgCode
     * @param sessionId
     * @param messageInfo
     */
    public static void handlerSendMessage(String event, String msgCode, String sessionId, String messageInfo) {
        sendEventMessage(event, buildMessageInfo(sessionId, msgCode, messageInfo));
    }

}
