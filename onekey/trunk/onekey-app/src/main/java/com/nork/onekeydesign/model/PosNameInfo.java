package com.nork.onekeydesign.model;

import java.io.Serializable;

public class PosNameInfo implements Serializable{

	private static final long serialVersionUID = -3927025799508741744L;

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
