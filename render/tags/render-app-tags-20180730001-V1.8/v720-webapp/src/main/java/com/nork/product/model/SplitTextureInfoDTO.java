package com.nork.product.model;

public class SplitTextureInfoDTO {

	/*模块key值*/
	private String key;
	/*模块name:(橱柜门,橱柜把手)*/
	private String name;
	/*材质ids*/
	private String textureIds;
	/*默认材质id*/
	private Integer  defaultId;
	
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
	
}
