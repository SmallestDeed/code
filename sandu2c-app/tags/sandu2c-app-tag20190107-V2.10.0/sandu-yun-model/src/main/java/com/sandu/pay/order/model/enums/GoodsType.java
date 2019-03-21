package com.sandu.pay.order.model.enums;

/**
 * Created by Administrator on 2017/9/20.
 */
public enum GoodsType {
    /** 运营网站充值订单 **/
    OPM_CHARGE("运营网站充值");

    private String name;

    GoodsType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
