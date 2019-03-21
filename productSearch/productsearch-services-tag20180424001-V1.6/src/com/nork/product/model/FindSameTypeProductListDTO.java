package com.nork.product.model;

import java.io.Serializable;
import java.util.List;
//findSameTypeProductList接口返回格式类
public class FindSameTypeProductListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer isSplit;
	private List<SplitTextureDTO> splitTexturesInfo;
	private List<BaseProduct> productList;
	
	public FindSameTypeProductListDTO() {
		super();
	}

	public FindSameTypeProductListDTO(Integer isSplit, List<SplitTextureDTO> splitTexturesInfo) {
		super();
		this.isSplit = isSplit;
		this.splitTexturesInfo = splitTexturesInfo;
	}
	
	public FindSameTypeProductListDTO(Integer isSplit, List<SplitTextureDTO> splitTexturesInfo,
			List<BaseProduct> productList) {
		super();
		this.isSplit = isSplit;
		this.splitTexturesInfo = splitTexturesInfo;
		this.productList = productList;
	}
	
	public Integer getIsSplit() {
		return isSplit;
	}
	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}
	public List<SplitTextureDTO> getSplitTexturesInfo() {
		return splitTexturesInfo;
	}
	public void setSplitTexturesInfo(List<SplitTextureDTO> splitTexturesInfo) {
		this.splitTexturesInfo = splitTexturesInfo;
	}
	public List<BaseProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<BaseProduct> productList) {
		this.productList = productList;
	}
}
