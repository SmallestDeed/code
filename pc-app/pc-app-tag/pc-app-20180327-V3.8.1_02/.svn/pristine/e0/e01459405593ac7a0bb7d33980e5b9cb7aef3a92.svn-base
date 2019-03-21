package com.nork.design.model.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * platformType枚举
 * 
 * @author huangsongbo
 * @date 2018.3.29
 */
public enum PlatformTypeEnum {
	
	/**
	 * 全部
	 */
	all("all"),
	
	/**
	 * 2b平台推荐方案
	 */
	toB("2b"),
	
	/**
	 * 2c平台推荐方案
	 */
	toC("2c"),
	
	/**
	 * 2b+2c平台推荐方案
	 */
	toBAndToC("2b,2c"),
	
	/**
	 * 未分配
	 */
	undistributed("default"),
	
	/**
	 * null
	 */
	isNull("isNull")
	
	;
	
	private String code;
	
	PlatformTypeEnum(String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static PlatformTypeEnum getEnumByCode(String code) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(code)) {
			return isNull;
		}
		// 参数验证 ->end
		for(PlatformTypeEnum platformTypeEnum : PlatformTypeEnum.values()) {
			if(StringUtils.equals(code, platformTypeEnum.getCode())) {
				return platformTypeEnum;
			}
		}
		return isNull;
	}
	
}
