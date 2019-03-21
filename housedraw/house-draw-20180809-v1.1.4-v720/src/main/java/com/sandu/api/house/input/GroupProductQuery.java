package com.sandu.api.house.input;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月21日
 */


@Setter
@Getter
@ToString
public class GroupProductQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 组合类型ID
	 */
	private Integer groupType;
	
	private Integer pageNum; 
	
	private Integer pageSize;
	
	private Integer[] putawayState;
}
