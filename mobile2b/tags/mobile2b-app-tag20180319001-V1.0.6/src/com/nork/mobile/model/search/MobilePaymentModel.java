package com.nork.mobile.model.search;

import java.io.Serializable;
/**
 * 开通移动端支付的model
 * Created by yangzhun on 2017/11/22.
 */
public class MobilePaymentModel implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String msgId;
    /**金额*/
    private Integer totalFee;
    /**支付类型（支付宝、微信、积分）*/
    private String payType;
    
    private String account;
    
    private String password;
    //订单号
    private String orderNo;
    //请求服务器地址
    private String serverUrl;
    
    
    
    public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
