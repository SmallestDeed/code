package com.nork.design.model;

import java.io.Serializable;

public class DesignPlanProductResult  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品风格Id
	 */
	private String proStyleValue;
	/**
	 * 产品风格名称
	 */
	private String proStyle;
	/**
	 * 产品分类
	 */
	private String productTypeValue;
	 /**
	  * 产品小分类
	 */
	private Integer productSmallTypeValue;
	/**
	 * 产品类型名称
	 */
	private String productType;
	
	/**
	 * 销售价格
	 */
	private String salePrice;
		
	/**产品价格单位名称*/
	private String salePriceValueName;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 品牌id
	 */
	private Integer brandId;
	/**
	 * 产品图片路径
	 */
	private String picPath;
	
	/**
	 * 产品收藏状态
	 */
	private Integer collectState;
	
	/**
	 * 商品数量
	 */
	private Integer count;

	/**
	 * 商品类型
	 */
	private String productCode;

	/**
	 * 图片id
	 */
	private Integer picId;
	
	private Integer salePriceValue;

	private String location;

	private String filePath;

	private String posName;

	private String posIndexPath;

	private Integer groupId;

	private Integer planProductId;

	private String planGroupId;



	private Integer secrecyFlag;	//是否保密：0不保密，1保密
	private Integer companyId;		//企业id

	public Integer getSecrecyFlag() {
		return secrecyFlag;
	}

	public void setSecrecyFlag(Integer secrecyFlag) {
		this.secrecyFlag = secrecyFlag;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPlanGroupId() {
		return planGroupId;
	}

	public void setPlanGroupId(String planGroupId) {
		this.planGroupId = planGroupId;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}

	public Integer getPlanProductId() {
		return planProductId;
	}

	public void setPlanProductId(Integer planProductId) {
		this.planProductId = planProductId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getSalePriceValue() {
		return salePriceValue;
	}

	public void setSalePriceValue(Integer salePriceValue) {
		this.salePriceValue = salePriceValue;
	}

	public String getSalePriceValueName() {
		return salePriceValueName;
	}

	public void setSalePriceValueName(String salePriceValueName) {
		this.salePriceValueName = salePriceValueName;
	}

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
 
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProStyleValue() {
		return proStyleValue;
	}

	public void setProStyleValue(String proStyleValue) {
		this.proStyleValue = proStyleValue;
	}

	public String getProStyle() {
		return proStyle;
	}

	public void setProStyle(String proStyle) {
		this.proStyle = proStyle;
	}

	public String getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

}
