package com.sandu.api.product.bo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年3月5日
 */

@Data
public class GroupProductBO implements Serializable {
	
	private Long groupId;
	private String groupCode;
	private String groupName;
	
	/**
	 * 产品位置关系
	 */
	private Long configId;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String configFile;
	
	/**
	 * 组合产品图
	 */
	private Long groupPicId;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String groupPicPath;
	
	/**
	 * 组合分类
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long groupType;
	
	/**
	 * 产品
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	List<BaseProductBO> products;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
