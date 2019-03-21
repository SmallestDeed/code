package com.sandu.api.base.model.query;

public class BaseAreaQuery extends BaseQuery<BaseAreaQuery>{
	private static final long serialVersionUID = 2351172115404471288L;
    private Integer levelId;
	private String areaName;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
}
