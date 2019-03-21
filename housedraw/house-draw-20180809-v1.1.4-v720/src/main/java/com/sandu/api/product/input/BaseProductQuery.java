package com.sandu.api.product.input;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月21日
 */

@Data
public class BaseProductQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	/**
	 * 分类值
	 */
	@ApiModelProperty(value = "产品分类", required = true)
	private String productTypeValue;
	
	/**
	 * 小分类
	 */
	private Integer productSmallTypeValue;
	
	private String productCode;
	
	/**
	 * 组合类型ID
	 */
	@ApiModelProperty("组合产品分类")
	private Integer groupType;
	
	/**
	 * 房间类型
	 * 默认 ==> -1
	 */
	private Integer houseType = -1;
	
	/**
	 * 上架状态
	 */
	private Integer[] putawayState;
	
	private Integer pageNum; 
	
	private Integer pageSize;
}
