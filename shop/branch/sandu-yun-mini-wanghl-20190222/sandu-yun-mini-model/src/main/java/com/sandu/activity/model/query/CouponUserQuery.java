package com.sandu.activity.model.query;

import java.math.BigInteger;
import java.util.Date;

import com.sandu.base.model.query.BaseQuery;

import io.swagger.annotations.ApiModelProperty;

public class CouponUserQuery extends BaseQuery<CouponUserQuery> {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "公司ID")
	private BigInteger companyId;
	@ApiModelProperty(value = "用户ID")
	private BigInteger userId;
	@ApiModelProperty(value = "优惠券ID")
	private BigInteger couponId;
	@ApiModelProperty(value = "状态 ")
	private Integer couponState;
	@ApiModelProperty(value = "使用状态")
	private Integer usedState;

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getCouponId() {
		return couponId;
	}

	public void setCouponId(BigInteger couponId) {
		this.couponId = couponId;
	}

	public Integer getCouponState() {
		return couponState;
	}

	public void setCouponState(Integer couponState) {
		this.couponState = couponState;
	}

	public Integer getUsedState() {
		return usedState;
	}

	public void setUsedState(Integer usedState) {
		this.usedState = usedState;
	}

	public BigInteger getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigInteger companyId) {
		this.companyId = companyId;
	}

}
