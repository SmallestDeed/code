package com.sandu.base.model.vo;

import java.io.Serializable;

/**
 * @author weis
 *		品牌Vo对象
 */
public class BaseBrandVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 品牌LOG
	 */
	private String brandLogo;
	/**
	 * 品牌描述
	 */
	private String brandDesc;
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandLogo() {
		return brandLogo;
	}
	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
	public String getBrandDesc() {
		return brandDesc;
	}
	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}
	@Override
	public String toString() {
		return "BaseBrandVo [brandId=" + brandId + ", brandName=" + brandName + ", brandLogo=" + brandLogo
				+ ", brandDesc=" + brandDesc + "]";
	}
	
}
