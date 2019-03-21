package com.nork.common.util;

import java.io.Serializable;

/**
 * 状态消息工具类
 * @auth xiaoxc
 * @data 2018-08-15
 */
public class MessageUtil implements Serializable {

    private static final long serialVersionUID = 3178512985163406046L;

    /**
     * 状态码
     */
    private boolean stauts;
    /**
     * 消息
     */
    private String message;

    public MessageUtil() {}

    public MessageUtil(boolean stauts) {
        this.stauts = stauts;
    }

    public MessageUtil(boolean stauts, String message) {
        this.stauts = stauts;
        this.message = message;
    }

    public boolean isStauts() {
        return stauts;
    }

    public void setStauts(boolean stauts) {
        this.stauts = stauts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
