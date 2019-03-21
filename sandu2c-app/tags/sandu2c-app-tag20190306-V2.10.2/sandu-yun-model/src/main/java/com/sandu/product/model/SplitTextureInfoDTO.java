package com.sandu.product.model;

import com.sandu.system.model.ResTexture;

import java.io.Serializable;
import java.util.List;

public class SplitTextureInfoDTO implements Serializable{


	private static final long serialVersionUID = -4995147472083520669L;
	/*模块key值*/
	private String key;
	/*模块name:(橱柜门,橱柜把手)*/
	private String name;
	/*材质ids*/
	private String textureIds;
	/*默认材质id*/
	private Integer  defaultId;
	
	private List<ResTexture> textureList;

	private List<TextureInfo> affectTextures;

	public List<TextureInfo> getAffectTextures() {
		return affectTextures;
	}

	public void setAffectTextures(List<TextureInfo> affectTextures) {
		this.affectTextures = affectTextures;
	}

	public List<ResTexture> getTextureList() {
		return textureList;
	}

	public void setTextureList(List<ResTexture> textureList) {
		this.textureList = textureList;
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


	
}
