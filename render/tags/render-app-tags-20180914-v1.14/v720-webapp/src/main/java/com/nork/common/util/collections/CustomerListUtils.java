package com.nork.common.util.collections;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.ListUtils;

public class CustomerListUtils extends ListUtils {

	public static boolean isEmpty(Collection<?> c) {
		return (c == null) || (c.size() == 0);
	}

	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}
	
	public static String getString(List<String> lst){
		StringBuffer sb=new StringBuffer();
		for(String str : lst){
			sb.append(str+"_");
		}
		return sb.toString();
	}

}