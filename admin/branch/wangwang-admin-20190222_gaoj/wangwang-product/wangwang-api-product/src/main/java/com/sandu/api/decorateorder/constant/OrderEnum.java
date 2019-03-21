package com.sandu.api.decorateorder.constant;

public enum OrderEnum {
	
    /**
     * 待支付
     */
    ZERO(0, "待支付"),

    /**
     * 订单超时
     */
    ONE(1, "订单超时"),

    /**
     * 待沟通
     */
    TWO(2, "待沟通"),

    /**
     * 有意向
     */
    THREE(3, "有意向"),

    /**
     * 无意向
     */
    FOUR(4, "无意向"),

    /**
     * 已签约
     */
    FIVE(5, "已签约"),

    /**
     * 已完成
     */
    SIX(6, "已完成");


    private int code;

    private String remark;

    OrderEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public int getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}

