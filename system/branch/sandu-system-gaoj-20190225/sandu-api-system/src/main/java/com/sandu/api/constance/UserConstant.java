package com.sandu.api.constance;

public class UserConstant {
	
	/**  pc jwtToken 超时时间     **/
	public static final int PC_2B_JWT_TOKEN_TIMEOUT = 12*60*60; //12小时
	public static final int PC_HOURSE_DRAW_JWT_TOKEN_TIMEOUT = 12*60*60; //12小时
	/**  移动端 jwtToken 超时时间     **/
	public static final int MOBILE_2B_JWT_TOKEN_TIMEOUT = 72*60*60; //3天
	/**  网站jwtToken 超时时间     **/
	public static final int BRAND_2C_JWT_TOKEN_TIMEOUT = 3*60*60; //3小时
	
	/**  pc登录超时时间     **/
	public static final int PC_2B_USER_SESSION_TIMEOUT = 72*60*60; //3天
	public static final int PC_HOURSE_DRAW_USER_SESSION_TIMEOUT = 72*60*60; //3天
	/**  移动端登录超时时间     **/
	public static final int MOBILE_2B_USER_SESSION_TIMEOUT = 72*60*60; //3天
	/**  网站jwtToken 超时时间     **/
	public static final int BRAND_2C_USER_SESSION_TIMEOUT = 24*60*60; //24小时
	
	public static final String PC_2B_LOGIN_PREFIX = "pc_2b_user_token:";
	public static final String PC_HOURSE_DRAW_LOGIN_PREFIX = "pc_hourse_draw_user_token:";
	public static final String MOBILE_2B_LOGIN_PREFIX = "mobile_2b_user_token:";
	public static final String BRAND_2C_LOGIN_PREFIX = "brand_2c_user_token:";
	
	/**	用户前缀 **/
	public static final String RBAC_USER_PREFIX = "user:";
	/**	用户角色组前缀 **/
	public static final String RBAC_USER_ROLE_GROUP_PREFIX = "user_role_group:";
	/**	角色角色组前缀 **/
	public static final String RBAC_ROLE_GROUP_REF_PREFIX = "role_group_ref:";
	/**	用户角色前缀 **/
	public static final String RBAC_USER_ROLE_PREFIX = "user_role:";
	/**	角色前缀 **/
	public static final String RBAC_ROLE_PREFIX = "role:";
	/**	角色权限前缀 **/
	public static final String RBAC_ROLE_FUNC_PREFIX = "role_func:";
	/**	权限前缀 **/
	public static final String RBAC_FUNC_PREFIX = "func:";
	/**	已开通移动端  **/
	public static final Integer HAS_EXISTS_MOBILE = 1;
	
	/** 用户是否更新密码标识 (为1时需要提示修改密码)**/
	public static final Integer PASSWORD_NEED_UPDATE = 1;
	public static final Integer PASSWORD_NEED_NOT_UPDATE = 0;
	
	/** 用户登录日志 --- 登录(记录)类型  --- 登入**/
	public static final Integer SYS_USER_LOGIN_LOG_LOGIN_TYPE_LOGIN = 1;
	
	/** 用户登录日志 --- 登录(记录)类型  --- 退出**/
	public static final Integer SYS_USER_LOGIN_LOG_LOGIN_TYPE_EXIT = 2;

}
