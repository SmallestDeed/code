package com.sandu.activity.model.vo;

import java.util.Date;

import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 优惠券产品信息输出实体类
 * @author Administrator
 *
 */
@ApiModel(value = "优惠券产品信息", description = "优惠券产品信息")
public class CouponProductVo extends BaseVo<CouponProductVo> {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "优惠券ID")
	private long couponId;
	@ApiModelProperty(value = "商品ID")
	private long goodsId;
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "店铺ID")
	private long shopId;
	@ApiModelProperty(value = "商品名称")
	private String spuName;
	@ApiModelProperty(value = "商品编码")
	private String spuCode;
	@ApiModelProperty(value = "商品图片路径")
	private String picPath;

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

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	
	

}
