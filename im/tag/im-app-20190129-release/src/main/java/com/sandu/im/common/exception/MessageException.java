package com.sandu.im.common.exception;

public class MessageException extends RuntimeException{

	private static final long serialVersionUID = -8807031941278309344L;
	
	private String errorCode;
	public MessageException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode.getCode();
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
