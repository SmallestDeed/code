package com.sandu.api.product.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月24日
 */

@Data
public class ProductTypeBO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long proId;
	private String proTypeName;
	private Integer proTypeVal;
	private String proTypeCode;
	private String proSmallTypeName;
	private Integer proSmallTypeVal;
	private String proSmallTypeCode;
}
