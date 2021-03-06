package com.sandu.activity.model;

import java.util.Date;

import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModelProperty;

/***
 * 会员领取优惠券信息
 * @author Administrator
 *
 */
public class CouponUser extends DataEntity<CouponUser> {

	private static final long serialVersionUID = 1L;

	//private Long id;
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "店铺ID")
	private long shopId;
	@ApiModelProperty(value = "用户ID")
	private long userId;
	@ApiModelProperty(value = "领取优惠券编号")
	private long receiveNo;
	@ApiModelProperty(value = "优惠券ID")
	private long couponId;
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

	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
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
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
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
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Override
	public String toString() {
		return "CouponUser{" +
				", companyId=" + companyId +
				", shopId=" + shopId +
				", userId=" + userId +
				", receiveNo=" + receiveNo +
				", couponId=" + couponId +
				", couponState=" + couponState +
				", usedState=" + usedState +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", usedTime=" + usedTime +
				", orderId=" + orderId +
				", receiveTime=" + receiveTime +
				'}';
	}
}
