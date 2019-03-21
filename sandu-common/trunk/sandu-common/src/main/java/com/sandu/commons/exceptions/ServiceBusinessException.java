package com.sandu.commons.exceptions;

/**
 * @Author chenqiang
 * @Description 自定义异常
 * @Date 2018/7/13 0013 11:56
 * @Modified By
 */
public class ServiceBusinessException extends RuntimeException{

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMessage;

    /**
     * 构造一个异常.
     *
     * @param errorCode         错误编码
     * @param errorMessage      错误描述
     * @param message           返回描述
     */
    public ServiceBusinessException(String errorCode, String errorMessage, String message) {
        super(message);
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMessage);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     * @param cause   根异常类（可以存入任何异常）
     */
    public ServiceBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
