package com.nork.design.model;

import java.io.Serializable;

public class MatchInfoVO implements Serializable {

	/**
	 * 样板房id
	 */
	private Integer designTempletId;
	/**
	 * 推荐方案id
	 */
	private Integer designRecommendedPlanId;
	/**
	 * 样板房对应的空间类型
	 */
	private Integer spaceFunctionId;
	/**
	 * 样板房对应的空间编码
	 */
	private String spaceCode;
	/**
	 * 样板房编码
	 */
	private String templateCode;


	/*空间ID*/
	private Integer spaceCommonId;

	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}

	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}


	public String getSpaceCode() {
		return spaceCode;
	}

	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	public Integer getDesignTempletId() {
		return designTempletId;
	}

	public void setDesignTempletId(Integer designTempletId) {
		this.designTempletId = designTempletId;
	}

	public Integer getDesignRecommendedPlanId() {
		return designRecommendedPlanId;
	}

	public void setDesignRecommendedPlanId(Integer designRecommendedPlanId) {
		this.designRecommendedPlanId = designRecommendedPlanId;
	}
}