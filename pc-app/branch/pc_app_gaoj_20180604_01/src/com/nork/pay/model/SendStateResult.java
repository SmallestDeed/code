package com.nork.pay.model;

import java.io.Serializable;

import com.nork.common.model.Mapper;

/**
 * Created by Administrator on 2016/9/25.
 */
public class SendStateResult extends Mapper implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单 号
     */
    private String orderNo;
    /**
     * 支付状态
     */
    private String payState;
    
    /**
     * 渲染类型
     */
    private String renderingType;

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

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }
}
