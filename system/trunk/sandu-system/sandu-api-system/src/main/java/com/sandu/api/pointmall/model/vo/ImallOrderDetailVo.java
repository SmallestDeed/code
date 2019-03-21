package com.sandu.api.pointmall.model.vo;

import java.math.BigDecimal;

import com.sandu.api.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

/***
 * 用于接收客户端订单详情
 */
@ApiModel(value = "购买订单详情", description = "购买订单详情对象")
public class ImallOrderDetailVo extends BaseVo<ImallOrderDetailVo> {
    private static final long serialVersionUID = 201808010901098L;
    public int uid; //用户ID
    public int totalPoint; //订单总积分
    public int totalCount; //商品数量
    public int paymentPoint; //实付积分
    public BigDecimal expressFee; //配送费
    public String expressNo; //'快递订单号'
    public String expressCompay; //'快递公司'
    public String remark;//'订单备注'
    public int giftsId; //礼品ID
    public Integer country ;//'国家'
    public Integer province;// '省份'
    public Integer city; //'城市'
    public Integer area;  //区域'
    public String address; //详细地址'
    public String zipcode; //邮编'
    public String consignee; //收件人'
    public String mobile; //联系电话'
    public String creator; //创建者
    public int getGiftsId() {
        return giftsId;
    }

    public void setGiftsId(int giftsId) {
        this.giftsId = giftsId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPaymentPoint() {
        return paymentPoint;
    }

    public void setPaymentPoint(int paymentPoint) {
        this.paymentPoint = paymentPoint;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressCompay() {
        return expressCompay;
    }

    public void setExpressCompay(String expressCompay) {
        this.expressCompay = expressCompay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
