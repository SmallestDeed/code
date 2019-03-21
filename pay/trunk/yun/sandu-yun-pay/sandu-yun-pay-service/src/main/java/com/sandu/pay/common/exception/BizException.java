package com.sandu.pay.common.exception;

/**
 * 定义异常时，异常代码表示模块信息和错误代码。
 * 例如：添加SSO模块异常可以定义为 [10010001] 前四位数为系统模块编号，后4位为错误代码 ,唯一 <br>
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    public BizException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BizException() {
        super();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message) {
        super(message);
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    /**
     * 实例化异常
     *
     * @param msgFormat
     * @param args
     * @return
     */
    public BizException newInstance(String msgFormat, Object... args) {
        return new BizException(this.code, msgFormat, args);
    }
}
