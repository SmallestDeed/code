package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PushMessageInfo implements Serializable {

    //目标客户端id
    private String targetSessionId;
    //消息类型: => 1.支付回调通知
    private String msgCode;
    //消息内容
    private String msgContent;

}
