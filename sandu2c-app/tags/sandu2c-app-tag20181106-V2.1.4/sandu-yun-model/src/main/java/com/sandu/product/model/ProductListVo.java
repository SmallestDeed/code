package com.sandu.product.model;

import java.io.Serializable;
import java.util.List;

public class ProductListVo implements Serializable{
	private Integer productId;
    /**
     * 商品名称
     */
    private String productName;
	private String productStyleIdInfo;
    /**
     * 产品图片路径
     */
    private String picPath;
    /**
     * 品牌名称
     */
    private String brandName;
    private Integer isFavorite;//是否被收藏.0:未收藏,1收藏
    private List<String> productStyleInfoList;
    
    public List<String> getProductStyleInfoList() {
		return productStyleInfoList;
	}

	public void setProductStyleInfoList(List<String> productStyleInfoList) {
		this.productStyleInfoList = productStyleInfoList;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Integer isFavorite) {
		this.isFavorite = isFavorite;
	}

	/**
     * 销售价格
     */
    private String salePrice;

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

	public String getProductStyleIdInfo() {
		return productStyleIdInfo;
	}

	public void setProductStyleIdInfo(String productStyleIdInfo) {
		this.productStyleIdInfo = productStyleIdInfo;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
    
}
