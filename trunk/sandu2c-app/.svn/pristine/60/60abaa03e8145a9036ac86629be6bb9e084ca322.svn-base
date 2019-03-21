package com.sandu.common.exception;

public class BizException extends Exception {

	private static final long serialVersionUID = 5043134335873834012L;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

}
