package com.sandu.api.house.input;

import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月30日
 */

@Data
public class DrawBaseHouseCheck {
	
	private Long userId;
	private Long houseId;
	
	/**
	 * 审核状态
	 * 审核驳回(3), 审核通过(4)
	 */
	private Integer checkStatus;
	
	/**
	 * 审核驳回原因
	 */
	private String rejectReason;
}
