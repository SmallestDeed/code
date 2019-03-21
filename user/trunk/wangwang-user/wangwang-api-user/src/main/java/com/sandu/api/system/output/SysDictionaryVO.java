package com.sandu.api.system.output;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysDictionaryVO implements Serializable {

	private static final long serialVersionUID = 2311788311298823771L;

	/**
	 * sys_dictionary.name
	 */
	private String name;
	
	/**
	 * sys_dictionary.value
	 */
	private Integer value;
	
}
