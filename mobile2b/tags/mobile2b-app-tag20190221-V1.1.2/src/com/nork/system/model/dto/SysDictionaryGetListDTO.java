package com.nork.system.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysDictionaryGetListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据字典value值
	 */
	private Integer value;
	
	/**
	 * 数据字典名称
	 */
	private String name;
	
}
