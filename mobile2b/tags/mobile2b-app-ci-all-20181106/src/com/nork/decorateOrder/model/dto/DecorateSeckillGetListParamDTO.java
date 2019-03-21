package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DecorateSeckillGetListParamDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 市区编码(过滤条件)
	 */
	private String cityCode;
	
	/**
	 * 预算value值(关联数据字典(type = decorateBudget)的value值)
	 */
	private Integer decorateBudgetValue;
	
	/**
	 * 装修类型value值(关联数据字典(type = decorateType)的value值)
	 */
	private Integer decorateTypeValue;
	
	/**
	 * 风格id
	 */
	private Long baseProductStyleId;
	
	/**
	 * orderType 排序设置
	 */
	private Integer orderType;
	
	/**
	 * 分页start
	 */
	private Integer start;
	
	/**
	 * 分页limit
	 */
	private Integer limit;
	
}
