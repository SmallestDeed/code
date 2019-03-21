package com.sandu.common.exception;

public class BizException extends Exception {

    private String message;

    private Integer exceptionCode;

    public BizException() {
    }

    public BizException( String message, Integer exceptionCode) {
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public BizException( String message) {
        this.message = message;
    }

    public BizException( Integer exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

}
