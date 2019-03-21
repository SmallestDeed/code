package com.nork.design.model.dto;

import java.io.Serializable;

/**
 * 记录水刀材质的bean,用于传输数据
 * 
 * @author huangsongbo
 * @date 2018.6.5
 *
 */
public class WaterJetTextureInfoDTO implements Serializable {

	private static final long serialVersionUID = 2352950107842565319L;

	/**
	 * 拆分模块key
	 */
	private String key;
	
	/**
	 * 拆分模块对应的材质id
	 */
	private Integer textureId;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getTextureId() {
		return textureId;
	}

	public void setTextureId(Integer textureId) {
		this.textureId = textureId;
	}
	
}
