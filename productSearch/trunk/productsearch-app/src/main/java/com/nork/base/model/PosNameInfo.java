package com.nork.base.model;

import java.io.Serializable;

public class PosNameInfo implements Serializable {
	
	private static final long serialVersionUID = 8839570865694685026L;
	
	private String posName;
	
	private Integer deignPlanProductId;
	
	public String getPosName() {
		return posName;
	}
	
	public void setPosName(String posName) {
		this.posName = posName;
	}
	
	public Integer getDeignPlanProductId() {
		return deignPlanProductId;
	}
	
	public void setDeignPlanProductId(Integer deignPlanProductId) {
		this.deignPlanProductId = deignPlanProductId;
	}
	
}