package com.nork.design.common;

import com.nork.common.properties.AppProperties;
import com.nork.common.util.Utils;

public class DesignPlanConstants {
	
	public static final Integer AUTO_RENDER = 0;
	
	public static final Integer USER_RENDER = 1;
 
	
	/**
	 * 未渲染 0 
	 */
	public static final int  NO_RENDER  = 0;
	/**
	 * 1 渲染中 
	 */
	public static final int  RENDERING  = 1;
	/**
	 * 2 渲染成功 
	 */
	public static final int  RENDER_SUCCESS  = 2;
	/**
	 *  3渲染失败
	 */
	public static final int  RENDER_FAIL  = 3;


	/**
	 * 方案推荐 公开类型
	 * 改为 普通方案
	 */
	public static final int    RECOMMENDED_TYPE_SHARE = 1;
	/**
	 * 方案推荐 一件装修类型
	 * 改为 智能方案
	 */
	public static final int    RECOMMENDED_TYPE_DECORATE = 2; 
	
	
	/** 用于正式发布，改造，复制  校验方法 begin>>*/
	/**
	 * 复制 
	 */
	public static final String  FUNCTION_TYPE_COPY = "copy";
	/**
	 * 改造
	 */
	public static final String  FUNCTION_TYPE_TRANSFORM = "transform";
	/**
	 * 发布
	 */
	public static final String  FUNCTION_TYPE_FRLEASE = "release";
	/**
	 * 测试发布
	 */
	public static final String  FUNCTION_TYPE_TEST_FRLEASE = "testRelease";
	/**end <<*/
	
	/**
	 * 方案公开管理员 角色编码
	 */
	public static final String   RECOMMENDEDRELEASE = "RECOMMENDEDRELEASE";
	
	/**
	 * 方案一件装修管理员 角色编码
	 */
	public static final String   RECOMMENDEDDECORATE = "RECOMMENDEDDECORATE";

	public static enum TASKSTATE{

		FAILUE(0),SUCCESS(1),PLAN_IS_DELETED(2);

	    private int num;

	    TASKSTATE(int num){
	        this.num = num;
	    }

	    public int getValue(){
	        return num;
	    }

	};
	
	public static enum FAILTYPE{
		SYSTEM(0),USERCANCEL(1),OTHER(2);
	    private int num;

	    FAILTYPE(int num){
	        this.num = num;
	    }

	    public int getValue(){
	        return num;
	    }
	};


	/**
	 * 获取设计空间布局类型分配value
	 */
	public static final String DESIGN_SPACE_LAYOUT_TYPE = Utils.getValueByFileKey(AppProperties.APP, AppProperties.DESIGN_SPACE_LAYOUT_TYPE,"[{\"productSmallType\":\"basic_edba,basic_baba\",\"layoutType\":\"A\"},{\"productSmallType\":\"basic_ahba,basic_dzas,basic_asba\",\"layoutType\":\"B\"}]");

	/**
	 * 枚举设计类型 （样板房，方案）
	 */
	public static enum EnumDesignType{
		TEMPLET, PLAN;
	}
	
	public static final Integer ORIGIN_DRAW = 1;

	/************** sync推荐方案 ***********/
	//操作-新增
	public static final int ADD_ACTION = 1;
	//操作-删除
	public static final int DELETE_ACTION = 2;
	//操作-更新
	public static final int UPDATE_ACTION = 3;

	/**
	 * 查询效果图方案表类型
	 */
	public static final String  SELECT_PLAN_RENDER_SCENE_TABLE_TYPE = "design";

	/**
	 * 查询推荐方案表类型
	 */
	public static final String  SELECT_PLAN_RECOMMENDED_TABLE_TYPE = "recommended";

	/**
	 * 查询一键生成方案表类型
	 */
	public static final String  SELECT_ONEKEY_PLAN_TABLE_TYPE = "oneKey";
}
