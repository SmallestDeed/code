package com.nork.common.properties;

import java.util.ResourceBundle;

public class ResProperties {
	public static final ResourceBundle RES=ResourceBundle.getBundle("config/res");
	/**
	 * 渲染原图
	 */
	public static final String DESIGNPLAN_RENDER_PIC_FILEKEY = "onekeydesign.designPlan.render.pic";
	/**
	 * 渲染原图
	 */
	public static final String DESIGNPLAN_RENDER_FILEKEY = "onekeydesign.designPlan.render";
	/**
	 * 渲染缩略图
	 */
	public static final String DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY = "onekeydesign.designPlan.render.small.pic";

	// product.structureProduct.configFile.upload.path
	public static final String STRUCTUREPRODUCT_CONFIGFILE_FILEKEY = "product.structureProduct.configFile";

	/**
	 * 720多点漫游渲染原图  这里不要加 upload.path  啊
	 */
	public static final String DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY = "onekeydesign.designPlan.render.roam.pic.upload.path";

	/**
	 * 720多点漫游渲染原图
	 */
	public static final String DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY_NEW = "onekeydesign.designPlan.render.roam.pic";
	
	
	/**
	 * 720多点漫游渲染截图(与720普通渲染原图相同)
	 */
	public static final String DESIGNPLAN_RENDER_ROAM_SCREEN_PIC_FILEKEY = "design.designPlan.render.pic.upload.path";
	
	/**
	 * 720渲染视频封面
	 */
	public static final String  DESIGNPLAN_RENDER_VIDEO_COVER = "onekeydesign.designPlan.render.video.cover";
	
	/**
	 * 720渲染视频
	 */
	public static final String  DESIGNPLAN_RENDER_VIDEO = "onekeydesign.designPlan.render.video";
	
	/**
	 * 已使用列表
	 * onekeydesign.designPlan.usedConfig
	 */
	public static final String  DESIGNPLAN_USEDCONFIG_FILEKEY = "onekeydesign.designPlan.usedConfig";
	
	/**
	 * 方案推荐  渲染原图
	 */
	public static final String DESIGNPLANRECOMMENDED_RENDER_PIC_FILEKEY = "onekeydesign.designPlanRecommended.render.pic";
	/**
	 * 方案推荐  渲染原图
	 */
	public static final String DESIGNPLANRECOMMENDED_RENDER_FILEKEY = "onekeydesign.designPlanRecommended.render";
	/**
	 * 方案推荐 渲染缩略图
	 */
	public static final String DESIGNPLANRECOMMENDED_PIC_SMALL_FILEKEY = "onekeydesign.designPlanRecommended.render.small.pic";
	/**
	 * 方案推荐 录像封面
	 */
	public static final String DESIGNPLANRECOMMENDED_VIDEO_FILEKEY = "onekeydesign.designPlanRecommended.render.video.cover";
	/**
	 * 方案推荐 多点漫游
	 */
	public static final String DESIGNPLANRECOMMENDED_ROAM_FILEKEY = "onekeydesign.designPlanRecommended.render.roam";
	/**
	 * 推荐720渲染视频
	 */
	public static final String  DESIGNPLANRECOMMENDED_RENDER_VIDEO = "onekeydesign.designPlanRecommended.render.video";
	
	/*onekeydesign.designPlan.u3dconfig.upload.path*/
	public static final String DESIGNPLAN_U3DCONFIG_FILEKEY = "design.designPlan.u3dconfig.upload.path";


	/**
	 * 自动渲染720多点漫游渲染原图  这里不要加 upload.path
	 */
	public static final String AUTO_DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY = "auto.onekeydesign.designPlan.render.roam.pic.upload.path";

	/**
	 * 自动渲染720多点漫游渲染截图(与720普通渲染原图相同)
	 */
	public static final String AUTO_DESIGNPLAN_RENDER_ROAM_SCREEN_PIC_FILEKEY = "auto.design.designPlan.render.pic.upload.path";

	/**
	 * 自动渲染原图
	 */
	public static final String AUTO_DESIGNPLAN_RENDER_PIC_FILEKEY = "auto.onekeydesign.designPlan.render.pic";

	/**
	 * 自动渲染缩略图
	 */
	public static final String AUTO_DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY = "auto.onekeydesign.designPlan.render.small.pic";

	/*
	* 自动渲染配置文件
	*/
	public static final String AUTO_DESIGNPLAN_U3DCONFIG_FILEKEY = "auto.design.designPlan.u3dconfig.upload.path";

	/*
	* 系统版本类型
	*/
	public static final String SYS_VERSION_TYPE = "sys.version.type";
	
	public static final String DESIGN_DESIGNPLANTEMPLATE_U3DCONFIG_FILEKEY = "design.designPlanTemplate.u3dconfig.upload.path";
}
