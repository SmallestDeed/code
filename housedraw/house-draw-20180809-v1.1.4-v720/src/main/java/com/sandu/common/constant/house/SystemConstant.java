package com.sandu.common.constant.house;

import com.sandu.common.constant.SystemConfigEnum;
import com.sandu.util.Utils;

/**
 * 系统模块常量
 * 
 * @author huangsongbo
 *
 */
public class SystemConstant {
	public static final String UPLOAD_ROOT = Utils.getValue(SystemConfigEnum.UPLOAD_ROOT_DIR.getKey());
}