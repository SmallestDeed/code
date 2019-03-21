package com.sandu.api.system.input;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysDictionarySearch implements Serializable {

	private static final long serialVersionUID = 14903749634612618L;

	/**
	 * 过滤条件: sys_dictionary.is_deleted
	 */
	private Integer isDeleted;
	
	/**
	 * 过滤条件: sys_dictionary.type
	 */
	private String type;
	
}
