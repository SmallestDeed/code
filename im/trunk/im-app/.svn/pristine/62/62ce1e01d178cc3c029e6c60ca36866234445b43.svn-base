package com.sandu.im.common;
import java.io.Serializable;

import com.sandu.im.common.exception.ErrorCode;

public class ResponseEnvelope<T> implements Serializable{
	private static final long serialVersionUID = -1L;
	private String msgId;
	private String resultCode;
	private String resultMsg;
	private T data;
	
	public ResponseEnvelope() {
		
	}
	
	
	public ResponseEnvelope(String resultCode, String resultMsg,T data) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.data = data;
	}
	
	public ResponseEnvelope(String resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public ResponseEnvelope(ErrorCode errorCode) {
		this.resultCode = errorCode.getCode();
		this.resultMsg = errorCode.getMessage();
	}
	
	public static ResponseEnvelope success() {
		return new ResponseEnvelope("SUCCESS", "操作成功!");
	}
	
	public static ResponseEnvelope bizSuccess(Object data) {
		return new ResponseEnvelope("SUCCESS", "操作成功!",data);
	}
	
	public static ResponseEnvelope bizError(ErrorCode errorCode) {
		return new ResponseEnvelope(errorCode.getCode(), errorCode.getMessage());
	}
	
	public static ResponseEnvelope bizError(String resultCode, String resultMsg) {
		return new ResponseEnvelope(resultCode, resultMsg);
	}
	
	public static ResponseEnvelope sysError() {
		return new ResponseEnvelope("UNKNOWN_ERROR", "未知异常!");
	}
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
