package com.sandu.api.commom;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/4/25
 * @since : sandu_yun_1.0
 */
public class ServiceAccessException extends Exception{

    private static final long serialVersionUID = -7168311127349525953L;

    private int errorCode;

    public ServiceAccessException(ServiceErrorCode code, String msg) {
        super(msg);
        this.errorCode = code.getCode();
    }

    public ServiceAccessException(ServiceErrorCode code) {
        super(code.defaultMsg);
        this.errorCode = code.getCode();
    }

    public ServiceAccessException(ServiceRuntimeException e) {
        super(e.getMessage(), e);
    }

    public ServiceAccessException(String msg){
        super(msg);
    }

    public ServiceAccessException(String msg, Throwable ex){
        super( msg, ex);
    }

    public ServiceAccessException(Throwable ex){
        super(ex.getMessage(), ex);
    }

    public int getErrorCode() {
        return errorCode;
    }


    public enum ServiceErrorCode {
        LOGIN_INVALID_USER(101, "Invalid the login user."),
        NO_LOGIN_USER(102, "Can't get the login user info.");


        private int code;
        private String defaultMsg;

        private ServiceErrorCode(int code, String defaultMsg) {
            this.code = code;
            this.defaultMsg = defaultMsg;
        }

        public int getCode() {
            return code;
        }
    }
}
