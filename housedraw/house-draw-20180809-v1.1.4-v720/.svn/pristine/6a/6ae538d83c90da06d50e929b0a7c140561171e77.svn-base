package com.sandu.api.product.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月21日
 */

@Data
public class BaseProductBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 产品名
	 */
	private String productName;

	/**
	 * 产品编码
	 */
	private String productCode;
	
	/**
	 * 大分类名称
	 */
	private String productTypeName;
	
	/**
	 * 大分类value
	 */
	private Integer productTypeValue;
	
	/**
	 * 大分类valuekey
	 */
	private String productTypeCode;
	
	/**
	 * 小分类名称
	 */
	private String productSmallTypeName;
	
	/**
	 * 小分类
	 */
	private Integer productSmallTypeValue;
	
	/**
	 * 小分类valuekey
	 */
	private String productSmallTypeCode;
	
	/**
	 * 图片ID
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long picId;
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String picName;

	/**
	 * 图片路径
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String picPath;
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long modelId;
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String modelName;

	/**
	 * 模型路径
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String modelPath;
	
	/**
	 * 长
	 */
	private Double productLength;
	
	/**
	 * 宽
	 */
	private Double productWidth;
	
	/**
	 * 高
	 */
	private Double productHeight;
	
	@JsonIgnore
	private Long groupId;
	
	/**
	 * 是否主产品
	 * 0、否
	 * 1、是
	 */
	private Integer isMainProduct;
}
