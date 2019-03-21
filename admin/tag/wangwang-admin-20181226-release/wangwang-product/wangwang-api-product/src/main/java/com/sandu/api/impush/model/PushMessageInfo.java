package com.sandu.api.impush.model;

import java.io.Serializable;

public class PushMessageInfo implements Serializable {

    //目标客户端id
    private String targetSessionId;
    //消息类型: => 1.支付回调通知
    private String msgCode;
    //消息内容
    private String msgContent;

    public String getTargetSessionId() {
        return targetSessionId;
    }

    public void setTargetSessionId(String targetSessionId) {
        this.targetSessionId = targetSessionId;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
