package com.sandu.common.constant.house;

public class DrawBaseProductConstant {

	/**
	 * 产品状态-未上架
	 */
	public final static Integer PUTAWAYSTATE_NOT_PUTAWAY = 0;

	/**
	 * 产品删除状态-默认值:未删除
	 */
	public final static Integer DEFAULTS_IS_DELETED_ = 0;

	/**
	 * 产品删除状态-默认值:未删除
	 */
	public final static String DEFAULTS_HOUSE_TYPES = "4,5,6,7,8,9,11,12,13,14,15,16";

	/**
	 * 软装(系统中已经存在数据)
	 */
	public final static Integer MODEL_TYPE_SOFT = 1;

	/**
	 * 硬装(户型绘制后需要在系统中新增产品数据)
	 */
	public final static Integer MODEL_TYPE_HARD = 0;

	/**
	 * 窗框,栏杆之类,阳台门
	 */
	public final static Integer MODEL_TYPE_PUBLIC = 2;

	/**
	 * 软装白膜产品是否拉伸过</p> 
	 * 1、是拉伸过; null或2、没有拉伸过
	 */
	public final static Integer IS_CHANGED_SOFT = 1;
	public final static Integer NOT_CHANGED_SOFT = 2;
	
}