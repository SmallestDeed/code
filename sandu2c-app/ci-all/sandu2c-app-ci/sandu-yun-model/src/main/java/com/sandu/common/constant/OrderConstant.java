package com.sandu.common.constant;

/**
 * @Author Gao Jun
 * @Description   游客信息常量类
 * @Date:Created Administrator in 上午 11:00 2018/3/10 0010
 * @Modified By:
 */
public class OrderConstant {

    public static final Integer ORDER_UN_CONFIRM = 0;//未确认

    public static final Integer ORDER_IS_CONFIRM = 1;//已确认

    public static final Integer ORDER_IS_CANCEL = 2;//已取消

    public static final Integer ORDER_IS_INVALID = 3;//无效

    public static final Integer ORDER_IS_COMPLETE= 4;//已完成

    public static final Integer ORDER_IS_REFUND =5;//退货



    public static final Integer SHIPPING_UN_SEND = 0;//未发货

    public static final Integer SHIPPING_IS_SEND = 1;//已发货

    public static final Integer SHIPPING_IS_RECEIVED = 2;//已收货

    public static final Integer SHIPPING_IS_PREPARE = 3;//备货中

    public static final Integer shipping_is_refund =4;//退货




    public static final Integer PAY_UN_PAY = 0;//未付款


    public static final Integer PAY_IN_PAY = 1;//付款中

    public static final Integer PAY_IS_PAY = 2;//已付款



}
