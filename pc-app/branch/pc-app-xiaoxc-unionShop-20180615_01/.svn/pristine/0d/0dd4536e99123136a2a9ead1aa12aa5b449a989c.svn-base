package com.nork.product.model;

import com.nork.common.util.StringUtils;

import java.io.Serializable;
import java.util.List;

public class SplitTextureInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*模块key值*/
	private String key;
	/*模块name:(橱柜门,橱柜把手)*/
	private String name;
	/*材质区域名称*/
	private String textureRegionName;
	/*材质ids*/
	private String textureIds;
	/*默认材质id*/
	private Integer  defaultId;
	/**
	 * 材质价格
	 */
	private List<AffectTextures> affectTextures;

	public List<AffectTextures> getAffectTextures() {
		return affectTextures;
	}

	public void setAffectTextures(List<AffectTextures> affectTextures) {
		this.affectTextures = affectTextures;
	}


	public Integer getDefaultId() {
		return defaultId;
	}

	public void setDefaultId(Integer defaultId) {
		this.defaultId = defaultId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTextureIds() {
		return textureIds;
	}

	public void setTextureIds(String textureIds) {
		this.textureIds = textureIds;
	}

	public String getTextureRegionName() {
		return textureRegionName;
	}

	public void setTextureRegionName(String textureRegionName) {
		this.textureRegionName = StringUtils.isNotBlank(textureRegionName)?textureRegionName:"";
	}
}
