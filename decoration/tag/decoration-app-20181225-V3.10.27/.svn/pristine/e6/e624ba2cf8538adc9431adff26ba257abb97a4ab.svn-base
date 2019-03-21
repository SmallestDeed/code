package com.nork.design.model.input;

import com.nork.design.model.DesignPlanCustomizedProductOrder;

import java.io.Serializable;

/**
 * 方案定制产品订单属性类
 * @thor xiaoxc
 * @data 2018/8/28
 */
public class DesignPlanCustomizedProductOrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键ID
    private Integer orderId;
    //方案ID
    private Integer planId;
    //合作伙伴
    private String partnersName;
    //外部订单ID
    private String exteriorOrderId;
    //订单号
    private String orderCode;
    //客户名称
    private String clientName;
    //合同号
    private String pactNo;
    //企业名称
    private String companyName;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPartnersName() {
        return partnersName;
    }

    public void setPartnersName(String partnersName) {
        this.partnersName = partnersName;
    }

    public String getExteriorOrderId() {
        return exteriorOrderId;
    }

    public void setExteriorOrderId(String exteriorOrderId) {
        this.exteriorOrderId = exteriorOrderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPactNo() {
        return pactNo;
    }

    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public DesignPlanCustomizedProductOrder getDesignPlanCustomizedProduct() {
        DesignPlanCustomizedProductOrder customizedProductOrder = new DesignPlanCustomizedProductOrder();
        customizedProductOrder.setId(this.orderId);
        customizedProductOrder.setPlanId(this.planId);
        customizedProductOrder.setPartnersName(this.partnersName);
        customizedProductOrder.setExteriorOrderId(this.exteriorOrderId);
        customizedProductOrder.setOrderCode(this.orderCode);
        customizedProductOrder.setClientName(this.clientName);
        customizedProductOrder.setPactNo(this.pactNo);
        customizedProductOrder.setCompanyName(this.companyName);
        return customizedProductOrder;
    }
}
