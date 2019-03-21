/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ErrorCodeEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.sandu.common.enums;


/**
 * The class Error code enum.
 *
 * @author
 */
public enum ErrorCodeEnum {
	/**
	 * Gl 99990100 error code enum.
	 */
	GL9999001(9999001, "参数异常"),
	/**
	 * Gl 99990401 error code enum.
	 */
	GL9999002(9999002, "无访问权限"),

	/**
	 * Gl 000500 error code enum.
	 */
	GL9999500(500, "未知异常"),

	/**
	 * Gl 99990100 error code enum.
	 */
	GL9999003(9999003, "数据异常"),;


	private int code;
	private String msg;

	/**
	 * Msg string.
	 *
	 * @return the string
	 */
	public String msg() {
		return msg;
	}

	/**
	 * Code int.
	 *
	 * @return the int
	 */
	public int code() {
		return code;
	}

	ErrorCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * Gets enum.
	 *
	 * @param code the code
	 *
	 * @return the enum
	 */
	public static ErrorCodeEnum getEnum(int code) {
		for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
			if (ele.code() == code) {
				return ele;
			}
		}
		return null;
	}
}
