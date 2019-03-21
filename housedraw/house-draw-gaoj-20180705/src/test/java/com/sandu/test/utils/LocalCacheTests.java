package com.sandu.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.common.constant.DictionaryTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年3月21日
 */

public class LocalCacheTests {
	
	static class LocalCache {
		
		private static final Map<String, Map<String, Dictionary>> DICT_CACHE = new HashMap<>();
		
		static {
			putCache();
		}
		
		static void putCache() {
			Map<String, Dictionary> map = new HashMap<>();
			List<Dictionary> dicts = new ArrayList<Dictionary>();
			for (Dictionary first : dicts) {
				for (Dictionary sec : first.getDicts()) {
					map.put(first.getValue() + "@" + sec.getValue(), first);
				}
			}
			
			DICT_CACHE.put(DictionaryTypeEnum.PRODUCT_TYPE.getType(), map);
		}
	}
	
	@Setter
	@Getter
	@ToString
	public
	class Dictionary {
		private String type;
		private String name;
		private String value;
		private String valuekey;
		private String ordering;
		private List<Dictionary> dicts;
	}
}
