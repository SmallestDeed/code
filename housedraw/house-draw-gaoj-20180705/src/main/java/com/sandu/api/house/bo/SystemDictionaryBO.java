package com.sandu.api.house.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

@Setter
@Getter
@ToString
public class SystemDictionaryBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 名字
	 */
	private String name;
	private String type;
	private String valuekey;
	private String value;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		SystemDictionaryBO other = (SystemDictionaryBO) obj;
		if (other.getValuekey().equals(valuekey)) {
			return true;
		}

		return false;
	}
}
