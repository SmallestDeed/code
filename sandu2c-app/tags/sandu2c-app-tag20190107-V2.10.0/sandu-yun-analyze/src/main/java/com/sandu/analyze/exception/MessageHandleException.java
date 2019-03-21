package com.sandu.analyze.exception;

/**
 * 消息处理异常
 *
 * @date 2018/5/7
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
public class MessageHandleException extends Exception{

    private static final String CLASS_PREFIX_LOG = "[消息处理异常]:";

    public MessageHandleException() {
    }

    public MessageHandleException(String message) {
        super(CLASS_PREFIX_LOG + message);
    }

    public MessageHandleException(String message, Throwable cause) {
        super(CLASS_PREFIX_LOG + message, cause);
    }

    public MessageHandleException(Throwable cause) {
        super(cause);
    }

    public MessageHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(CLASS_PREFIX_LOG + message, cause, enableSuppression, writableStackTrace);
    }
}
