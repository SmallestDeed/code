package com.nork.design.common;

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
	 */
	public static final int    RECOMMENDED_TYPE_SHARE = 1;
	/**
	 * 方案推荐 一件装修类型
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

	public enum TASKSTATE{

		FAILUE(0),SUCCESS(1),PLAN_IS_DELETED(2);

	    private int num;

	    TASKSTATE(int num){
	        this.num = num;
	    }

		public int getValue(){
			return num;
		}

	};
	
	public enum FAILTYPE{
		SYSTEM(0),USERCANCEL(1),OTHER(2);
	    private int num;

	    FAILTYPE(int num){
	        this.num = num;
	    }

	    public int getValue(){
	        return num;
	    }
	};
	
	
	

}
