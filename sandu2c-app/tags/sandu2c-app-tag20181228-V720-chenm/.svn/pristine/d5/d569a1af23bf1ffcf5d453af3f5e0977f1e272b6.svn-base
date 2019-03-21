package com.sandu.pay.order.metadata;

import java.io.Serializable;
import java.util.List;

public class ScanPayReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int orderId;
	private String code_url;
	private String qrCodePath;
	private String orderNo;
	private	Integer taskId;
	/**
	 * 未支付订单集合
	 */
	private List<String> npaidOrderList;
	public Integer getTaskId() {
		return taskId;
	}
	public List<String> getNpaidOrderList() {
		return npaidOrderList;
	}


	public void setNpaidOrderList(List<String> npaidOrderList) {
		this.npaidOrderList = npaidOrderList;
	}


	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getCode_url() {
		return code_url;
	}
	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
	public String getQrCodePath() {
		return qrCodePath;
	}
	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}
}
