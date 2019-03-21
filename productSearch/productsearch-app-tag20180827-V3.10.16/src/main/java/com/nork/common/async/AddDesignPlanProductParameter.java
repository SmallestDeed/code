package com.nork.common.async;

import javax.servlet.http.HttpServletRequest;

public class AddDesignPlanProductParameter {
	
	private String context;
	private String posIndexPath;
	private String posName;
	private Integer designPlanId;
	private Integer productId;
	private HttpServletRequest request;
	
	public AddDesignPlanProductParameter(String context,String posIndexPath
			,String posName,Integer designPlanId,Integer productId,HttpServletRequest request){
		this.context = context;
		this.posIndexPath = posIndexPath;
		this.posName = posName;
		this.designPlanId = designPlanId;
		this.productId = productId;
		this.request = request;
	}
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getPosIndexPath() {
		return posIndexPath;
	}
	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public Integer getDesignPlanId() {
		return designPlanId;
	}
	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
