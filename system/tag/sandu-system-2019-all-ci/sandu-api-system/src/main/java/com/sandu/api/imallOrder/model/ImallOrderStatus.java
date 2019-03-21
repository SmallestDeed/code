package com.sandu.api.imallOrder.model;

/**
 * 积分订单状态
 */
public class ImallOrderStatus {

    /**
     * 待付款
     */
    public static int ORDER_WAITPAYMENT=0;

    /**
     * 待确认
     */
    public static int ORDER_WAITAFFIRM=1;

    /**
     * 待发货
     */
    public static int ORDER_WAITSEND=2;

    /**
     * 关闭订单
     */
    public static int ORDER_CLOSE=3;

    /**
     * 发货
     */
    public static int ORDER_SEND=4;

    /**
     * 完成
     */
    public static int ORDER_COMPLETE=5;

}
