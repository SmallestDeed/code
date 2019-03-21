package com.sandu.util;

import org.apache.commons.lang3.StringUtils;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月15日
 */

public enum Regex {

	IMAGE(".+\\.(?i)(gif|png|jpg|jpeg|bmp)$", "图片"), 
	NUMBER("(-|+)?\\d+"), 
	FILE_SUFFIX("\\.{1}(?i).+$", "文件后缀"), 
	SPACE_LAYOUT_B_TYPE("^B.+", "卫生间布局类型"),
	MOBILE_PHONE("^1[3-9]\\d{9}$", "手机号"),
	FLOAT_NUMBER("^(0|[1-9])\\d*(\\.[0-9]{1,2})?$", "##?.##"),
	DOUBLE_NUMBER("^(0|[1-9])\\d*(\\.[0-9]+)?$", "##?.##?"),
	LF_FILE(".+\\.(?i)lf$", "量房lf文件"),
	PPNG_FILE(".+\\.(?i)ppng$", "量房ppng户型图文件"),
	ASSETBUNDLE(".+\\.(?i)assetbundle$", "assetbundle文件"),
	OBJ(".+\\.(?i)obj$"),
	SDKJ(".+\\.(?i)sdkj$"),
	SUFFIX(".+\\.(?i)#{suffix}$", "匹配suffix"),
	ZIP_FILE(".+\\.(?i)(zip|tar|gz|rar|7z|gzip|gzi)$", "匹配压缩文件");

	private String value;
	private String remark;

	public String getValue() {
		return value;
	}

	public String getValue(String suffix) {
		return value == null ? "" : value.replace("#{suffix}", suffix);
	}

	public String getValue(FileType fileType) {
		return getValue(fileType.getType());
	}

	public String getRemark() {
		return remark;
	}
	
	Regex(String value) {
		this.value = value;
	}

	Regex(String value, String remark) {
		this.value = value;
		this.remark = remark;
	}
}
