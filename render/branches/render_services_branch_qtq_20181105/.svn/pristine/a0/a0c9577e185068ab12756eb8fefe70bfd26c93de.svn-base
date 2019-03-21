package com.nork.productprops.model;

import java.io.Serializable;
import java.util.List;

/**
 * 产品对应排序属性list和过滤属性list
 * @author huangsongbo
 *
 */
public class BaseProductProps implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer productId;
	
	private boolean isBaimo;
	
	/**过滤属性list*/
	private List<ProductPropsSimple> productOrderPropList;
	
	/**排序属性list*/
	private List<ProductPropsSimple> productFilterPropList;
	
	public BaseProductProps() {
		super();
	}

	public BaseProductProps(Integer productId, boolean isBaimo, List<ProductPropsSimple> productOrderPropList,
			List<ProductPropsSimple> productFilterPropList) {
		super();
		this.productId = productId;
		this.isBaimo = isBaimo;
		this.productOrderPropList = productOrderPropList;
		this.productFilterPropList = productFilterPropList;
	}

	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public boolean isBaimo() {
		return isBaimo;
	}


	public void setBaimo(boolean isBaimo) {
		this.isBaimo = isBaimo;
	}


	public List<ProductPropsSimple> getProductOrderPropList() {
		return productOrderPropList;
	}


	public void setProductOrderPropList(List<ProductPropsSimple> productOrderPropList) {
		this.productOrderPropList = productOrderPropList;
	}


	public List<ProductPropsSimple> getProductFilterPropList() {
		return productFilterPropList;
	}


	public void setProductFilterPropList(List<ProductPropsSimple> productFilterPropList) {
		this.productFilterPropList = productFilterPropList;
	}

}
