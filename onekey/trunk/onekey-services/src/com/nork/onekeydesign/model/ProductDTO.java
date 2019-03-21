package com.nork.onekeydesign.model;

import java.io.Serializable;

public class ProductDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer productId;
	private String productTypeValue;
	private String valueKey;
	private String posIndexPath;

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductTypeValue() {
		return productTypeValue;
	}
	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}
	public String getValueKey() {
		return valueKey;
	}
	public void setValueKey(String valueKey) {
		this.valueKey = valueKey;
	}
	
	

	

}
