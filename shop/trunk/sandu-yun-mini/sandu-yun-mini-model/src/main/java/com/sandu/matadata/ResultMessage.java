package com.sandu.matadata;

import lombok.Data;

import java.io.Serializable;
/***
 * 消息处理
 * @author Administrator
 *
 */
@Data
public class ResultMessage implements Serializable{
	private static final long serialVersionUID = 1517527084492896120L;
	// 返回结果
	private int code = ResultCode.Fail;
	// 返回消息
	private String message = "";

	private Object data;
	//业务类型
	private String businessType;
	
	public ResultMessage() {
		setCode(ResultCode.Fail);
	}

	public ResultMessage(int code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	public ResultMessage(int code, String message, String businessType) {
		this.setCode(code);
		this.setMessage(message);
		this.setBusinessType(businessType);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}

