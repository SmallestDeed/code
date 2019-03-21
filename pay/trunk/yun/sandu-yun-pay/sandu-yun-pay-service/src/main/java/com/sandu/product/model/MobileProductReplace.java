package com.sandu.product.model;

import java.io.Serializable;

/**
 * 移动端一键替换产品  实体类
 * @author yangzhun
 */

public class MobileProductReplace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	
	/**
	 * 设计方案id，对应的是design_plan_render_scene表id
	 */
	private Integer planId;
	
	/**
	 * 订单产品类型(具体可以见ProductType.java)
	 */
	private String productType;
	
	/**
	 * 订单总金额，单位为分
	 */
	private Double totalFee;
	
	/**
	 * 支付类型
	 */
	private String payType;

	private String companyDomainName; // 公司品牌网站域名

	public String getCompanyDomainName() {
		return companyDomainName;
	}

	public void setCompanyDomainName(String companyDomainName) {
		this.companyDomainName = companyDomainName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
	
}
