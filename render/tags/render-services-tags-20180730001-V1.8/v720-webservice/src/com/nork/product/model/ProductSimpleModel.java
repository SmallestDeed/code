package com.nork.product.model;

import java.io.Serializable;

public class ProductSimpleModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer id;
	
	String productCode;
	
	String windowsU3dModelPath;
	
	String bigTypeValue;
	
	String smallTypeValue;
	
	String productIdentification;
	
	public String getProductIdentification() {
		return productIdentification;
	}
	public void setProductIdentification(String productIdentification) {
		this.productIdentification = productIdentification;
	}
	public String getBigTypeValue() {
		return bigTypeValue;
	}
	public void setBigTypeValue(String bigTypeValue) {
		this.bigTypeValue = bigTypeValue;
	}
	public String getSmallTypeValue() {
		return smallTypeValue;
	}
	public void setSmallTypeValue(String smallTypeValue) {
		this.smallTypeValue = smallTypeValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getWindowsU3dModelPath() {
		return windowsU3dModelPath;
	}
	public void setWindowsU3dModelPath(String windowsU3dModelPath) {
		this.windowsU3dModelPath = windowsU3dModelPath;
	}
	
	
}
