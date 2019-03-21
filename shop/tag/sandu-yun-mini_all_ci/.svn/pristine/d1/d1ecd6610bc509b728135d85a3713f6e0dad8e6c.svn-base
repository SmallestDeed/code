package com.sandu.activity.model.query;

import java.math.BigInteger;
import java.util.List;

import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModelProperty;

public class CouponProductQuery extends BaseQuery<CouponProductQuery>{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "优惠券ID")
	private BigInteger couponId;
	@ApiModelProperty(value = "商品ID")
	private BigInteger goodsId;
	private List<BigInteger> lstGoodsIds;
	public BigInteger getCouponId() {
		return couponId;
	}
	public void setCouponId(BigInteger couponId) {
		this.couponId = couponId;
	}
	public BigInteger getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(BigInteger goodsId) {
		this.goodsId = goodsId;
	}
	public List<BigInteger> getLstGoodsIds() {
		return lstGoodsIds;
	}
	public void setLstGoodsIds(List<BigInteger> lstGoodsIds) {
		this.lstGoodsIds = lstGoodsIds;
	}

}
