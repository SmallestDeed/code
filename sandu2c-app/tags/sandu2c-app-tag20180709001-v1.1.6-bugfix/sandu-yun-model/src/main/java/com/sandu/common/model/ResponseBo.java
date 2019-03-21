package com.sandu.common.model;

import java.io.Serializable;

public class ResponseBo implements Serializable{

	/**
	 * 响应信息
	 */
	private static final long serialVersionUID = 1L;
	private Boolean status;
	private String msg;
	private Integer totalCount;
	
    public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	//返回对象
    private Object obj;
    
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResponseBo{" +
				"status=" + status +
				", msg='" + msg + '\'' +
				", totalCount=" + totalCount +
				", obj=" + obj +
				'}';
	}
}
