package com.sandu.api.base.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 状态消息响应
 * @auth xiaoxc
 * @data 2018-06-26
 */
@Data
public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 3178512985163406046L;

    /**
     * 状态码
     */
    private boolean stauts;
    /**
     * 消息
     */
    private String message;

    public MessageResponse() {}

    public MessageResponse(boolean stauts) {
        this.stauts = stauts;
    }

    public MessageResponse(boolean stauts, String message) {
        this.stauts = stauts;
        this.message = message;
    }
}
