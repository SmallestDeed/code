package com.sandu.api.house.bo;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class UserLoginBO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String msgId = null;
	private Long id;
	private Integer userType;
	/**
	 * 字符主键
	 **/
	private String loginId;
	/**
	 * 登录名称
	 **/
	private String loginName;//
	private String loginPhone;// 登录手机号
	private String loginEmail;// 登录手机号
	private String name;// 登录中文名
	private String appKey;
	private String userKey;
	private String token;
	private String deviceId;
	private String mediaType;
	private Integer groupId;// 组织ID
	private Integer sex;// 性别
	private String picPath;// 头像
	private String brandIds;// 品牌(多个用逗号隔开)
	private String serverUrl;
	private String resourcesUrl;

	private String loginRedisUserRandomKey;
	private String loginRedisUserIdKey;
	private Integer sessionTimeout;
	private Set<String> roles;
	private Set<String> permissions;
	private Map<String, Set<String>> queryFields;
}
