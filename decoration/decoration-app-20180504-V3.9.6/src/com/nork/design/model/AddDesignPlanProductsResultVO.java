package com.nork.design.model;

import java.io.Serializable;

/**
 * function:DesignPlanService.addDesignPlanProducts返回结果bean
 * 
 * @author huangsongbo
 *
 */
public class AddDesignPlanProductsResultVO implements Serializable{

	private static final long serialVersionUID = 4359375866925657512L;

	/**
	 * posName
	 */
	private String posName;
	
	/**
	 * 设计方案产品id
	 */
	private Integer designPlanProductId;

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public Integer getDesignPlanProductId() {
		return designPlanProductId;
	}

	public void setDesignPlanProductId(Integer designPlanProductId) {
		this.designPlanProductId = designPlanProductId;
	}
	
}
