package com.nork.common.async;

public class UpdatePlanConfigParameter {
	
	private Integer planId;
	private String context;
	private Integer planProductId;
	
	public UpdatePlanConfigParameter(Integer planId, String context, Integer planProductId){
		this.planId = planId;
		this.context = context;
		this.planProductId = planProductId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getPlanProductId() {
		return planProductId;
	}

	public void setPlanProductId(Integer planProductId) {
		this.planProductId = planProductId;
	}
	
}
