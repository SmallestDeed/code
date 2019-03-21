package com.nork.product.model;

/**
 * Created by Administrator on 2016/4/27.
 */
public class CorrectProduct {

    private Integer productId;
    private String modelPath;
    private String productLength;
    private String productWidth;
    private String productHeight;

    public String getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getProductLength() {
        return productLength;
    }

    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    public String getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }
}
