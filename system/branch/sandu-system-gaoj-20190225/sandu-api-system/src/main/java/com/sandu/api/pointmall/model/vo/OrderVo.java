package com.sandu.api.pointmall.model.vo;

import com.sandu.api.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "订单对象", description = "订单对象")
public class OrderVo  extends BaseVo<OrderVo> {
    private static final long serialVersionUID = 201808040921018L;
    private  String order_no;
    private int total_point;
    private  String buyer_nick_name;
    private int buyer_id;
    private int status;
    private int refundment_status;
    private int shipment_status;
    private double express_fee;
    private String express_no;
    private  String express_compay;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }



    public int getTotal_point() {
        return total_point;
    }

    public void setTotal_point(int total_point) {
        this.total_point = total_point;
    }


    public String getBuyer_nick_name() {
        return buyer_nick_name;
    }

    public void setBuyer_nick_name(String buyer_nick_name) {
        this.buyer_nick_name = buyer_nick_name;
    }


    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getRefundment_status() {
        return refundment_status;
    }

    public void setRefundment_status(int refundment_status) {
        this.refundment_status = refundment_status;
    }


    public int getShipment_status() {
        return shipment_status;
    }

    public void setShipment_status(int shipment_status) {
        this.shipment_status = shipment_status;
    }


    public double getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(double express_fee) {
        this.express_fee = express_fee;
    }


    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }


    public String getExpress_compay() {
        return express_compay;
    }

    public void setExpress_compay(String express_compay) {
        this.express_compay = express_compay;
    }


}

