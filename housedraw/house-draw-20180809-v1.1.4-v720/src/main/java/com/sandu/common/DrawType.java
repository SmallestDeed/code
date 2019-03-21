package com.sandu.common;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年2月28日
 */

public interface DrawType {
	
	/**
	 * 绘制工具新绘制
	 */
	int DRAW_NEW = 1;
	
	/**
	 * 老数据临摹绘制
	 */
	int DRAW_OLD = 2;
	
	/**
	 * 普通绘制者上传户型
	 */
	int DRAW_AUDITOR_EDIT = 4;

	/**
	 * 毛坯房绘制
	 */
	int DRAW_ROUGH_HOUSE = 5;
}
