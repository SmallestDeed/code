package com.sandu.api.base.common;
import java.io.Serializable;
import java.util.List;

public class ResponseEnvelope<T> implements Serializable{
	private static final long serialVersionUID = -1L;
	private String msgId;
	private boolean success = true;
	private String message = "ok";
	private Object obj;
	private long totalCount;
	private List<T> datalist;
	//状态码，不同状态不同业务逻辑处理（StatusCode类）
	private String statusCode;
	//返回状态
	private boolean status = true;
	
	public ResponseEnvelope() {
	}
	
	public ResponseEnvelope(boolean success,Object obj) {
		this.success=success;
		this.obj = obj;
	}
	
	public ResponseEnvelope(boolean success,String message,Object obj) {
		this.success=success;
		this.obj = obj;
		this.message = message;
	}
	
	public ResponseEnvelope(boolean success,long totalCount, List<T> datalist) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.success = success;
	}
	
	public ResponseEnvelope(boolean success, String message) {
		this.success = success;
		this.message = message;
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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
