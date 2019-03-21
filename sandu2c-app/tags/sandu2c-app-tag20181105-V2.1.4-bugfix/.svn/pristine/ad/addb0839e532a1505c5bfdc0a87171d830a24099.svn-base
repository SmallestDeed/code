package com.sandu.pay.order.model;

import java.io.Serializable;

public class ResultMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success = false;
	private String message = "failed";
	private Object obj;
	private String msgId;
	//TODO  余额支付临时处理
	private String orderNo;
	 /**
     * 渲染类型
     */
    private String renderingType;
    /**
     * 任务id
     */
    private Integer taskId;

    private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getRenderingType() {
		return renderingType;
	}
	public void setRenderingType(String renderingType) {
		this.renderingType = renderingType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Override
	public String toString() {
		return "ResultMessage{" +
				"success=" + success +
				", message='" + message + '\'' +
				", obj=" + obj +
				", msgId='" + msgId + '\'' +
				", orderNo='" + orderNo + '\'' +
				", renderingType='" + renderingType + '\'' +
				", taskId=" + taskId +
				", status=" + status +
				'}';
	}
}
