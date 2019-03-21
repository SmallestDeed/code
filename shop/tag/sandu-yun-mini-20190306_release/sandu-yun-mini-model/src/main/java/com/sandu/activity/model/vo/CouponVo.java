package com.sandu.activity.model.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.activity.model.CouponProduct;
import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 优惠券信息输出类
 * @author Administrator
 *
 */
@ApiModel(value = "优惠券信息", description = "优惠券信息")
public class CouponVo extends BaseVo<CouponVo> {

	private static final long serialVersionUID = 1L;
	private List<CouponProduct> lstProduct;
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "店铺ID")
	private long shopId;
	@ApiModelProperty(value = "优惠券名称")
	private String couponName;
	@ApiModelProperty(value = "总量")
	private int qty;
	@ApiModelProperty(value = "已领取总数量")
	private int receiveQty;
	@ApiModelProperty(value = "领取人数")
	private int userReceiveCount;
	@ApiModelProperty(value = "领取率")
	private String receiveRate;
	@ApiModelProperty(value = "每人限领数量")
	private int maxReceiveQty;
	@ApiModelProperty(value = "已使用总量")
	private int usedQty;
	@ApiModelProperty(value = "使用率")
	private String usedRate;
	@ApiModelProperty(value = "可领取数量")
	private int availableStock;
	@ApiModelProperty(value = "状态")
	private int couponState;
	@ApiModelProperty(value = "优惠方式")
	private int discountMode;
	@ApiModelProperty(value = "优惠金额")
	private double discountAmount;
	@ApiModelProperty(value = "折扣系数")
	private double rebateFactor;
	@ApiModelProperty(value = "订单金额")
	private double orderAmount;
	@ApiModelProperty(value = "有效期方式 ")
	private int effectiveDateMode;
	@ApiModelProperty(value = "领券当日多少天有效")
	private int effectiveDays;
	@ApiModelProperty(value = "有效期开始日期")
	private Date startTime;
	@ApiModelProperty(value = "有效期截止日期")
	private Date endTime;
	@ApiModelProperty(value = "到期提醒方式")
	private int expireRemindMode;
	@ApiModelProperty(value = "距离到期日多少天")
	private int beforeExpireDays;
	@ApiModelProperty(value = "优惠券类型")
	private int couponType;
	@ApiModelProperty(value = "商品使用范围类别")
	private int productScopeType;
	@ApiModelProperty(value = "使用范围说明")
	private String scopeDesc;
	@ApiModelProperty(value = "使用门槛")
	private String useThreshold;
	@ApiModelProperty(value = "使用期限")
	private String useDuration;
	private long receiveId;
	private int receiveState;
	@ApiModelProperty(value = "优惠券是否过期")
	private Integer isEffectiveDate;

	public Integer getIsEffectiveDate() {
		return isEffectiveDate;
	}

	public void setIsEffectiveDate(Integer isEffectiveDate) {
		this.isEffectiveDate = isEffectiveDate;
	}

	public List<CouponProduct> getLstProduct() {
		return lstProduct;
	}

	public void setLstProduct(List<CouponProduct> lstProduct) {
		this.lstProduct = lstProduct;
	}

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

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(int receiveQty) {
		this.receiveQty = receiveQty;
	}

	public int getUsedQty() {
		return usedQty;
	}

	public void setUsedQty(int usedQty) {
		this.usedQty = usedQty;
	}

	public int getCouponState() {
		return couponState;
	}

	public void setCouponState(int couponState) {
		this.couponState = couponState;
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

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getEffectiveDateMode() {
		return effectiveDateMode;
	}

	public void setEffectiveDateMode(int effectiveDateMode) {
		this.effectiveDateMode = effectiveDateMode;
	}

	public int getEffectiveDays() {
		return effectiveDays;
	}

	public void setEffectiveDays(int effectiveDays) {
		this.effectiveDays = effectiveDays;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

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

	public int getExpireRemindMode() {
		return expireRemindMode;
	}

	public void setExpireRemindMode(int expireRemindMode) {
		this.expireRemindMode = expireRemindMode;
	}

	public int getBeforeExpireDays() {
		return beforeExpireDays;
	}

	public void setBeforeExpireDays(int beforeExpireDays) {
		this.beforeExpireDays = beforeExpireDays;
	}

	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

	public int getProductScopeType() {
		return productScopeType;
	}

	public void setProductScopeType(int productScopeType) {
		this.productScopeType = productScopeType;
	}

	public String getScopeDesc() {
		return scopeDesc;
	}

	public void setScopeDesc(String scopeDesc) {
		this.scopeDesc = scopeDesc;
	}

	public int getMaxReceiveQty() {
		return maxReceiveQty;
	}

	public void setMaxReceiveQty(int maxReceiveQty) {
		this.maxReceiveQty = maxReceiveQty;
	}

	public int getUserReceiveCount() {
		return userReceiveCount;
	}

	public void setUserReceiveCount(int userReceiveCount) {
		this.userReceiveCount = userReceiveCount;
	}

	public int getAvailableStock() {
		if(qty==0)
			availableStock=-1;
		else
			availableStock=qty-receiveQty;
		return availableStock;
	}

	public void setAvailableStock(int availableStock) {
		this.availableStock = availableStock;
	}

	public String getUsedRate() {
		return usedRate;
	}

	public void setUsedRate(String usedRate) {
		this.usedRate = usedRate;
	}

	public String getReceiveRate() {
		return receiveRate;
	}

	public void setReceiveRate(String receiveRate) {
		this.receiveRate = receiveRate;
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

	public long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}

	public int getReceiveState() {
		if(receiveId>0)
			receiveState=1;
		else
			receiveState=0;
		return receiveState;
	}

	public void setReceiveState(int receiveState) {
		this.receiveState = receiveState;
	}

}
