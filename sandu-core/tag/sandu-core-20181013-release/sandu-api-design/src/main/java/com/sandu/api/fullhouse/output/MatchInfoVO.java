package com.sandu.api.fullhouse.output;

import java.io.Serializable;

import lombok.Data;

@Data
public class MatchInfoVO implements Serializable {

	private static final long serialVersionUID = -8922036114453962305L;

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

	/**
	 * 空间id
	 */
	private Integer spaceCommonId;
	
	public MatchInfoVO() {
		super();
	}

	public MatchInfoVO(Integer designTempletId, Integer designRecommendedPlanId, Integer spaceFunctionId,
			String spaceCode, String templateCode, Integer spaceCommonId) {
		super();
		this.designTempletId = designTempletId;
		this.designRecommendedPlanId = designRecommendedPlanId;
		this.spaceFunctionId = spaceFunctionId;
		this.spaceCode = spaceCode;
		this.templateCode = templateCode;
		this.spaceCommonId = spaceCommonId;
	}
	
}
