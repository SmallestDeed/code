package com.nork.common.async;

import javax.servlet.http.HttpServletRequest;

import com.nork.design.model.DesignPlanProduct;

/***
 * 设计方案产品保存任务参数
 * @author qiu.jun
 * @date 2016.06.16
 *
 */
public class ProductSaveParameter {
	private DesignPlanProduct designPlanProduct;
	private HttpServletRequest request;
	private Integer designPlanId;
	private String context;
	private Integer reUploadConfig;
	
	public ProductSaveParameter(DesignPlanProduct designPlanProduct,Integer designPlanId, String context, Integer reUploadConfig, HttpServletRequest request){
		this.designPlanProduct=designPlanProduct;
		this.designPlanId = designPlanId;
		this.context = context;
		this.reUploadConfig = reUploadConfig;
		
		this.request=request;
	}
	
	public DesignPlanProduct getDesignPlanProduct() {
		return designPlanProduct;
	}

	public void setDesignPlanProduct(DesignPlanProduct designPlanProduct) {
		this.designPlanProduct = designPlanProduct;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getReUploadConfig() {
		return reUploadConfig;
	}

	public void setReUploadConfig(Integer reUploadConfig) {
		this.reUploadConfig = reUploadConfig;
	}

	
	
}
