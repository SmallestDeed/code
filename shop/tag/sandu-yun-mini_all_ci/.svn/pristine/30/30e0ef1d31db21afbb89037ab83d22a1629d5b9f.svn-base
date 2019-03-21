package com.sandu.activity.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModelProperty;

public class CouponUserVo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	@ApiModelProperty(value = "公司ID")
	private long compayId;
	@ApiModelProperty(value = "店铺ID")
	private long shopId;
	@ApiModelProperty(value = "用户ID")
	private long userId;
	@ApiModelProperty(value = "领取优惠券编号")
	private long receiveNo;
	@ApiModelProperty(value = "优惠券ID")
	private long couponId;
	@ApiModelProperty(value = "订单金额")
	private double orderAmount;
	@ApiModelProperty(value = "状态 ")
	private long couponState;
	@ApiModelProperty(value = "使用状态")
	private long usedState;
	@ApiModelProperty(value = "有效期开始日期")
	private Date startTime;
	@ApiModelProperty(value = "有效期截止日期")
	private Date endTime;
	@ApiModelProperty(value = "使用时间")
	private Date usedTime;
	@ApiModelProperty(value = "订单号")
	private long orderId;
	@ApiModelProperty(value = "领取时间")
	private Date receiveTime;
	@ApiModelProperty(value = "优惠券类型")
	private int couponType;
	private int effectiveDateMode;
	private int effectiveDays;
	@ApiModelProperty(value = "优惠方式")
	private int discountMode;
	@ApiModelProperty(value = "优惠金额")
	private double discountAmount;
	@ApiModelProperty(value = "折扣系数")
	private double rebateFactor;

	@ApiModelProperty(value = "使用门槛")
	private String useThreshold;
	@ApiModelProperty(value = "使用期限")
	private String useDuration;
	@ApiModelProperty(value = "使用范围说明")
	private String scopeDesc;
	@ApiModelProperty(value = "优惠券修改时间")
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public int getEffectiveDateMode() {
		return effectiveDateMode;
	}
	public void setEffectiveDateMode(int effectiveDateMode) {
		this.effectiveDateMode = effectiveDateMode;
	}
	public String getUseThreshold() {
		if(getOrderAmount()==0) {
			useThreshold="无门槛";
		}
		else {
			useThreshold="订单满"+getOrderAmount()+"元";
		}
		return useThreshold;
	}
	public void setUseThreshold(String useThreshold) {
		this.useThreshold = useThreshold;
	}
	public String getUseDuration() {
		return useDuration;
	}
	public void setUseDuration(String useDuration) {
		this.useDuration = useDuration;
	}
	public long getCompayId() {
		return compayId;
	}
	public void setCompayId(long compayId) {
		this.compayId = compayId;
	}
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getReceiveNo() {
		return receiveNo;
	}
	public void setReceiveNo(long receiveNo) {
		this.receiveNo = receiveNo;
	}
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public long getCouponState() {
		return couponState;
	}
	public void setCouponState(long couponState) {
		this.couponState = couponState;
	}
	public long getUsedState() {
		return usedState;
	}
	public void setUsedState(long usedState) {
		this.usedState = usedState;
	}
	public Date getStartTime() {
		return startTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getScopeDesc() {
		return scopeDesc;
	}
	public void setScopeDesc(String scopeDesc) {
		this.scopeDesc = scopeDesc;
	}
	public int getCouponType() {
		return couponType;
	}
	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}
	public int getEffectiveDays() {
		return effectiveDays;
	}
	public void setEffectiveDays(int effectiveDays) {
		this.effectiveDays = effectiveDays;
	}
	public int getDiscountMode() {
		return discountMode;
	}
	public void setDiscountMode(int discountMode) {
		this.discountMode = discountMode;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getRebateFactor() {
		return rebateFactor;
	}
	public void setRebateFactor(double rebateFactor) {
		this.rebateFactor = rebateFactor;
	}

}
