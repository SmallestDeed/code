package com.sandu.util;

import org.apache.commons.lang3.StringUtils;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年3月26日
 */

public class ExceptionUtils {

	private static final int DEFAULT_STACK_TRACE_LENGTH = 512;

	public static String getStackTrace(Exception e) {
		String stackTrace = org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e);
		if (!StringUtils.isEmpty(stackTrace)) {
			return stackTrace.substring(0, DEFAULT_STACK_TRACE_LENGTH);
		}
		return Utils.VOID_VALUE;
	}
}
