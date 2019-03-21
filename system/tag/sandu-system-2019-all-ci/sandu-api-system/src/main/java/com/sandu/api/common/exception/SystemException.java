package com.sandu.api.common.exception;

public class SystemException extends RuntimeException{

	private static final long serialVersionUID = -8807031941278309344L;
	
	private String errorCode;
	public SystemException(String errorCode,String errorMsg) {
		super(errorMsg);
		this.errorCode =errorCode;
	}
	
	public SystemException(String errorMsg) {
		super(errorMsg);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
