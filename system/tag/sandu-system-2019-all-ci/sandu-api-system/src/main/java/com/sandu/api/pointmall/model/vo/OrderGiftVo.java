package com.sandu.api.pointmall.model.vo;

import com.sandu.api.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "订单礼品对象", description = "订单礼品对象")
public class OrderGiftVo  extends BaseVo<OrderGiftVo> {
    private static final long serialVersionUID = 201808010901021L;
    public int order_id;
    public  int item_id;
    public  String item_sku;
    public String item_name;
    public String item_description;
    public  String item_original_price;
    public String item_point;
    public String item_count;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }



    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }



    public String getItem_sku() {
        return item_sku;
    }

    public void setItem_sku(String item_sku) {
        this.item_sku = item_sku;
    }


    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }


    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }


    public String getItem_original_price() {
        return item_original_price;
    }

    public void setItem_original_price(String item_original_price) {
        this.item_original_price = item_original_price;
    }


    public String getItem_point() {
        return item_point;
    }

    public void setItem_point(String item_point) {
        this.item_point = item_point;
    }


    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }
}
