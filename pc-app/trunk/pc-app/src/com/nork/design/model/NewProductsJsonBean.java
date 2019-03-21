package com.nork.design.model;

import java.io.Serializable;

public class NewProductsJsonBean implements Serializable{
	
	private static final long serialVersionUID = 491887187261644834L;

	/**
	 * 设计方案产品表posIndexPath
	 */
	private String posIndexPath;
	
	/**
	 * 设计方案产品表productId:产品id
	 */
	private Integer productId;
	
	/**
	 * 设计方案产品表posName
	 */
	private String posName;
	
	/**
	 * 设计方案产品表(影响initProductId)
	 */
	private Integer bindProductId;

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

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public Integer getBindProductId() {
		return bindProductId;
	}

	public void setBindProductId(Integer bindProductId) {
		this.bindProductId = bindProductId;
	}
	
}
