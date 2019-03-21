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
	 * 区域材质默认高度
	 */
	private Integer height;
	/**
	 * 区域材质默认宽度
	 */
	private Integer width;

	/**
	 * 贴图价格
	 */
	private List<AffectTextures> affectTextures;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

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
