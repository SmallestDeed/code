package com.sandu.common.constant;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年3月29日
 */

@AllArgsConstructor
public enum SpaceLayoutType {

	A("basic_edba,basic_baba", ""), 
	B("basic_ahba,basic_dzas,basic_asba", ""), 
	N("", "啥都没有");

	private final String value;
	private final String remark;

	public String getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	public static SpaceLayoutType contains(String val) {
		if (StringUtils.isEmpty(val)) {
			return N;
		}

		SpaceLayoutType[] values = values();
		for (SpaceLayoutType layout : values) {
			if (layout.getValue().contains(val)) {
				return layout;
			}
		}

		return N;
	}
}
