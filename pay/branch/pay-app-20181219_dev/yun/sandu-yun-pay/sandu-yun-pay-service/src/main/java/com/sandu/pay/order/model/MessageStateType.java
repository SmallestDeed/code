package com.sandu.pay.order.model;

/**
 * Created by Administrator on 2017/10/27 0027.
 */
public class MessageStateType {

    //支付度币不足
    public static final int PAY_INTEGRAL_INSUFFICIENT_STATE = 1;

    //用户户型不足
    public static final int HOUSE_INSUFFICIENT_STATE = 2;

    //支付失败
    public static final int PAY_FAILED_STATE = 3;

    //其他
    public static final int PAY_OTHER_STATE = 4;

    //已使用过的户型和方案
    public static final int ALREADY_USED_HOUSE_AND_PLAN_STATE = 5;

}
