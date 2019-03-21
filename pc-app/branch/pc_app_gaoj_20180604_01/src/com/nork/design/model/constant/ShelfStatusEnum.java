package com.nork.design.model.constant;

/**
 * 
 * @author huangsongbo
 * @date 2018.3.30
 *
 */
public enum ShelfStatusEnum {

	ONEKEY("ONEKEY"),
	
	OPEN("OPEN"),
	
	DEFAULT("DEFAULT"),
	
	;
	
	private String code;
	
	private ShelfStatusEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
}
