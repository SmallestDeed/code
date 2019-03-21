
package com.sandu.api.commom;

import java.util.List;

public class JsonModelError {
	
	private String message;
	
	private String msgKey; // the error message returned
	private List<Object> msgValues;
	
	//private int errorCode; // place holder for future server internal error code
	
	private List<FieldError> validationErrors;//usage for Form JsonSubmit action.

	public JsonModelError(String message, String msgKey,
			List<Object> msgValues, List<FieldError> validationErrors) {
		super();
		this.message = message;
		this.msgKey = msgKey;
		this.msgValues = msgValues;
		this.validationErrors = validationErrors;
	}

	public JsonModelError(String message) {
		this.message = message;
	}
	
	public JsonModelError(List<FieldError> validationErrors) {
		this("Error msg");
		this.validationErrors = validationErrors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public List<Object> getMsgValues() {
		return msgValues;
	}

	public void setMsgValues(List<Object> msgValues) {
		this.msgValues = msgValues;
	}

	public List<FieldError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<FieldError> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public static class FieldError{
		private String id;
		private String msg;
		
		public FieldError(String id,String msg) {
			this.id = id;
			this.msg = msg;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}	
	}
}
