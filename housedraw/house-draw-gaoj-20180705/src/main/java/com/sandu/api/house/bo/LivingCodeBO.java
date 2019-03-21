package com.sandu.api.house.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月25日
 */

@Data
public class LivingCodeBO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer maxCount;
	private Long zipCode;
}
