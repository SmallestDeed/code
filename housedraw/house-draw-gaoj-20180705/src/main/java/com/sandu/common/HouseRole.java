package com.sandu.common;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月30日
 */

public interface HouseRole {

	/**
	 * 户型审核员
	 */
	String HOUSE_DRAW_APPROVED = "HOUSE_DRAW_APPROVER";

	/**
	 * 内部户型绘制员(绘制角色)
	 */
	String INTERNAL_HOUSE_DRAW = "INTERNAL_HOUSE_DRAW";

	/**
	 * 户型完善员
	 */
	String HOUSE_DRAW_IMPROVER = "HOUSE_DRAW_IMPROVER";

	/**
	 * 普通硬装绘制(普通绘制员)
	 */
	String COMMON_HOUSE_DRAW = "COMMON_HOUSE_DRAW";
	
	/**
	 * 外部全流程绘制(内部演示员)
	 */
	String INTERNAL_DEMO_DRAW = "INTERNAL_DEMO_DRAW";

}
