package com.sandu.common.constant.house;

import com.sandu.service.system.impl.SystemDictionaryServiceImpl;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月24日
 */

public enum ProductType {

	BED("床"), BD("bd", "床架"), BD2("bd2", "床垫"), BD3("bd3", "床品"), 
	PE("pe", "饰品"), BK("bk", "摆件"),
	BA("ba", "卫浴"), BP("bp", "浴室配件"),
	FORCER("柜类"), CA("ca", "柜子"), CUKI("cuki", "厨房地柜"), DGKI("dgki", "厨房吊柜"),
	KITCHEN("厨房"), KI("ki", "厨房（旧）"), KP("kp", "厨房配件"),
	EL("el", "电器"), SE("se", "小家电"),  KIEL("kiel", "厨房电器"),
	
	_DOOR(SystemDictionaryServiceImpl._DOORFRAME_TYPE, "门框");

	private String name;
	private String valueKey;

	public String getName() {
		return name;
	}
	
	public String getValuekey() {
		return valueKey;
	}
	
	ProductType(String name) {
		this.name = name;
	}

	ProductType(String valueKey, String name) {
		this.name = name;
		this.valueKey = valueKey;
	}
}
