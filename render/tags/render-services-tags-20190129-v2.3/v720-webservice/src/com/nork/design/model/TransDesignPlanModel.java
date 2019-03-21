package com.nork.design.model;

import java.io.Serializable;

public class TransDesignPlanModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String livingId;
	String designPlanId;
	String planRecommendedId;
	String residentialUnitsName;
	String type;
	public String getLivingId() {
		return livingId;
	}
	public void setLivingId(String livingId) {
		this.livingId = livingId;
	}
	public String getDesignPlanId() {
		return designPlanId;
	}
	public void setDesignPlanId(String designPlanId) {
		this.designPlanId = designPlanId;
	}
	public String getPlanRecommendedId() {
		return planRecommendedId;
	}
	public void setPlanRecommendedId(String planRecommendedId) {
		this.planRecommendedId = planRecommendedId;
	}
	public String getResidentialUnitsName() {
		return residentialUnitsName;
	}
	public void setResidentialUnitsName(String residentialUnitsName) {
		this.residentialUnitsName = residentialUnitsName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
