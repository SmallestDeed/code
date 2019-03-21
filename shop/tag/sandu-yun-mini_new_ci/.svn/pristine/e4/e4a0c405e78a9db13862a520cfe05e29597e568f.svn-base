package com.sandu.activity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 优惠券信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "优惠券信息", description = "优惠券信息")
public class Coupon implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String productIds;
	private List<CouponProduct> lstProduct;
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "店铺ID")
	private long shopId;
	@ApiModelProperty(value = "优惠券名称")
	private String couponName;
	@ApiModelProperty(value = "总量")
	private int qty;
	@ApiModelProperty(value = "每人限领数量")
	private int maxReceiveQty;
	@ApiModelProperty(value = "领取总数量")
	private int receiveQty;
	@ApiModelProperty(value = "已使用总量")
	private long usedQty;
	@ApiModelProperty(value = "状态")
	private int couponState;
	@ApiModelProperty(value = "优惠方式")
	private int discountMode;
	@ApiModelProperty(value = "优惠金额")
	private long discountAmount;
	@ApiModelProperty(value = "折扣系数")
	private double rebateFactor;
	@ApiModelProperty(value = "订单金额")
	private Double orderAmount;
	@ApiModelProperty(value = "有效期方式 ")
	private int effectiveDateMode;
	@ApiModelProperty(value = "领券当日多少天有效")
	private int effectiveDays;
	@ApiModelProperty(value = "有效期开始日期")
	private Date startTime;
	@ApiModelProperty(value = "有效期截止日期")
	private Date endTime;
	@ApiModelProperty(value = "有效期开始日期(前端传入)")
	private String strStartTime;
	@ApiModelProperty(value = "有效期截止日期(前端传入)")
	private String strEndTime;
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
	@ApiModelProperty(value = "是否是新增記錄")
	public int getMaxReceiveQty() {
		return maxReceiveQty;
	}
	public void setMaxReceiveQty(int maxReceiveQty) {
		this.maxReceiveQty = maxReceiveQty;
	}

	private boolean IsNewRecord;

	@ApiModelProperty(value = "领取优惠卷编号")
	private Long receiveNo;

	private int isDeleted = 0;

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/***
	 * 修改日期
	 */
	private Date gmtModified;
	/***
	 * 创建日期
	 */
	private Date gmtCreate;

	private String modifier;

	/***
	 * 创建者账号
	 */
	private String creator;

	private String sysCode;

	/**
	 * 优惠券是否领取过 0.未领取 1.领取过
	 */
	private Integer isAlreadyGet;

	public Integer getIsAlreadyGet() {
		return isAlreadyGet;
	}

	public void setIsAlreadyGet(Integer isAlreadyGet) {
		this.isAlreadyGet = isAlreadyGet;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void preInsert(){
		this.gmtModified = new Date();
		this.gmtCreate = this.gmtModified;
	}

	public void preUpdate(){
		this.gmtModified = new Date();
	}

	public Long getReceiveNo() {
		return receiveNo;
	}

	public void setReceiveNo(Long receiveNo) {
		this.receiveNo = receiveNo;
	}

	public boolean isIsNewRecord() {
		return IsNewRecord;
	}

	public void setIsNewRecord(boolean isNewRecord) {
		IsNewRecord = isNewRecord;
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

	public long getUsedQty() {
		return usedQty;
	}

	public void setUsedQty(long usedQty) {
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

	public long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getRebateFactor() {
		return rebateFactor;
	}

	public void setRebateFactor(double rebateFactor) {
		this.rebateFactor = rebateFactor;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
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

	public String getStrStartTime() {
		return strStartTime;
	}

	public void setStrStartTime(String strStartTime) {
		this.strStartTime = strStartTime;
	}

	public String getStrEndTime() {
		return strEndTime;
	}

	public void setStrEndTime(String strEndTime) {
		this.strEndTime = strEndTime;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	
	

}
