package com.nork.render.model;

/**
 * 
 * @ClassName: RenderHostCodes 
 * @Description: 主机状态code
 * @author qinyl http://dwtedx.com
 * @date 2017年3月12日 下午1:35:14
 */
public class RenderHostCodes {
	/**
	 * 不可以使用
	 */
	public final static int CANT_USE = 0;
	/**
	 * 可以使用
	 */
	public final static int CAN_USE = 1;
	/**
	 * 已下架
	 */
	public final static int OFF_SHELF = 2;
	
	
	
	/**
	 * 租用主机
	 */
	public final static int HIRE_HOST_TYPE = 0;
	
	/**
	 * 云渲染服务器
	 */
	public final static int CLOUD_HOST_TYPE = 1;
	
	/**
	 *没有可以使用的主机
	 */
	public final static int NONE_HOST_CAN_USE = 2;
}
