package com.sandu.api.pointmall.model.vo;

import com.sandu.api.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

@ApiModel(value = "我的订单礼品对象", description = "我的订单礼品对象")
public class OrderGiftDetailVo extends BaseVo<OrderGiftDetailVo> {
    private static final long serialVersionUID = 201808010901098L;
    public String orderNo;
    public String giftName;
    public double itemOriginalPrice;
    public int itemPoint;
    public int itemCount;
    public String createTime;
    public int status;
    public String filename;

    /**订单备注**/
    private String remark;
    /**运费**/
    private BigDecimal expressFee;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public double getItemOriginalPrice() {
        return itemOriginalPrice;
    }

    public void setItemOriginalPrice(double itemOriginalPrice) {
        this.itemOriginalPrice = itemOriginalPrice;
    }

    public int getItemPoint() {
        return itemPoint;
    }

    public void setItemPoint(int itemPoint) {
        this.itemPoint = itemPoint;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
