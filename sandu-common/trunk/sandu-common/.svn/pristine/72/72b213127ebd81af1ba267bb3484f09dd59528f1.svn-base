package com.sandu.commons;
import java.io.Serializable;
import java.util.List;

public class ResponseEnvelope<T> implements Serializable{
	private static final long serialVersionUID = -1L;
	private String msgId;
	private boolean success = true;
	private Integer exceptionCode;
	private String message = "ok";
	private Object obj;
	private long totalCount;
	private List<T> datalist;
	
	public ResponseEnvelope() {
	}
	
	public ResponseEnvelope(boolean success) {
		this.success=success;
	}
	
	public ResponseEnvelope(boolean success, Object obj) {
		this.success=success;
		this.obj = obj;
	}
	
	public ResponseEnvelope(boolean success, String message, Object obj) {
		this.success=success;
		this.obj = obj;
		this.message = message;
	}
	
	public ResponseEnvelope(boolean success, long totalCount, List<T> datalist) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.success = success;
	}
	
	public ResponseEnvelope(boolean success, long totalCount, List<T> datalist,String msgId) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.success = success;
		this.msgId = msgId;
	}
	public ResponseEnvelope(boolean success, long totalCount, List<T> datalist,Object obj,String msgId) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.success = success;
		this.obj = obj;
		this.msgId = msgId;
	}

	public ResponseEnvelope(boolean success,Object obj,String msgId,String message) {
		this.success = success;
		this.obj = obj;
		this.msgId = msgId;
		this.message = message;
	}

	public ResponseEnvelope(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public ResponseEnvelope(boolean success, String message,String msgId) {
		this.success = success;
		this.message = message;
		this.msgId = msgId;
	}


	public ResponseEnvelope(String msgId,boolean success,Object obj,String message) {
		this.success = success;
		this.obj = obj;
		this.msgId = msgId;
		this.message = message;
	}

	public ResponseEnvelope(String msgId,boolean success, long totalCount, List<T> datalist) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.success = success;
		this.msgId = msgId;
	}

	public ResponseEnvelope(String msgId,boolean success, long totalCount, List<T> datalist,String message) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.success = success;
		this.msgId = msgId;
	}

	public ResponseEnvelope(String msgId,boolean success, String message) {
		this.success = success;
		this.message = message;
		this.msgId = msgId;
	}

	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public List<T> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<T> datalist) {
		this.datalist = datalist;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
