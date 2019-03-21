package com.sandu.commons;

import java.io.Serializable;

/**
 * 状态消息工具类
 * @auth xiaoxc
 * @data 2019-01-17
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

    /**
     * 返回结果对象
     */
    private Object object;

    public MessageUtil() {}

    public MessageUtil(boolean stauts) {
        this.stauts = stauts;
    }

    public MessageUtil(boolean stauts, String message) {
        this.stauts = stauts;
        this.message = message;
    }

    public MessageUtil(Object object, boolean stauts) {
        this.stauts = stauts;
        this.object = object;
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
