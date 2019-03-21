package com.sandu.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseEnvelope<T> implements Serializable{
	private static final long serialVersionUID = -5084759450031494382L;
	private Map header =new HashMap<String, String>();
	private String msgId;
	private boolean success = true;
	private String message = "ok";
	private Object obj;
	private long totalCount;
	private List<T> datalist;
    private boolean flag;
	private Integer exceptionCode;
	public ResponseEnvelope() {
		this.datalist = new ArrayList<T>();
	}

	public ResponseEnvelope(Object obj) {
		this.obj = obj;
	}
	
	public ResponseEnvelope(boolean success,Object obj) {
		this.success=success;
		this.obj = obj;
	}

	public ResponseEnvelope(boolean success,Object obj,String message,String msgId) {
		this.success=success;
		this.obj = obj;
		this.msgId = msgId;
		this.message = message;
	}
	public ResponseEnvelope(long totalCount, List<T> datalist) {

		this.totalCount = totalCount;
		this.datalist = datalist;
	}
	

	public ResponseEnvelope(List<T> datalist, String msgId) {
		this.datalist = datalist;
		this.msgId = msgId;
	}
	
	public ResponseEnvelope(Object obj, String msgId,boolean flag) {
		this.obj = obj;
		this.msgId = msgId;
		this.flag = flag ;
	}
	
	public ResponseEnvelope(boolean success, Object obj, String msgId) {
		this.success = success;
		this.obj = obj;
		this.msgId = msgId;
	}

	public ResponseEnvelope(String msgId,boolean success,String message,long totalCount,List<T> dataList){
		this.msgId = msgId;
		this.success = success;
		this.totalCount = totalCount;
		this.datalist = dataList;
	}

	public ResponseEnvelope(long totalCount, List<T> datalist,String msgId) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.msgId = msgId;
	}
	
	public ResponseEnvelope(boolean success,String msgId,long totalCount, List<T> datalist) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.msgId = msgId;
		this.success = success;
	}
	
	public ResponseEnvelope(Integer totalCount, List<T> datalist,String msgId) {
		this.totalCount = totalCount;
		this.datalist = datalist;
		this.msgId = msgId;
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

	public ResponseEnvelope(boolean success,String msgId,boolean flag) {
		this.success = success;
		this.msgId = msgId;
		this.flag = flag;
	}
	public ResponseEnvelope(boolean success, String message,String msgId,boolean flag) {
		this.success = success;
		this.message = message;
		this.msgId = msgId;
		this.flag=flag;
	}

	public ResponseEnvelope(boolean success, String message,String msgId,boolean flag,Integer exceptionCode) {
		this.success = success;
		this.message = message;
		this.msgId = msgId;
		this.flag=flag;
		this.exceptionCode = exceptionCode;
	}
	
	public ResponseEnvelope(boolean success, String message,String msgId,boolean flag,Object obj,Integer exceptionCode) {
		this.success = success;
		this.message = message;
		this.msgId = msgId;
		this.flag=flag;
		this.obj = obj;
		this.exceptionCode = exceptionCode;
	}
	

	public ResponseEnvelope(boolean success, String message,String msgId,Object obj,long totalCount,List<T> datalist) {
		this.success = success;
		this.message = message;
		this.msgId = msgId;
		this.obj = obj;
		this.totalCount = totalCount;
		this.datalist = datalist;
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
		return datalist==null?new ArrayList<T>():datalist;
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

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

    public Map getHeader() {
        return header;
    }

    public void setHeader(Map header) {
        this.header = header;
    }

	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

    

}
