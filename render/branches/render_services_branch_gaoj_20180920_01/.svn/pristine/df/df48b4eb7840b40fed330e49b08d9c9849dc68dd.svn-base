package com.nork.sandu.model.dto;

import com.nork.common.util.Utils;

/***
 * 效果图
 * 
 * @author Administrator
 *
 */
public class TDesignSketch {
	private Integer id;
	private String picName;
	private String picPath;
	private String picDesc;
	private String picUrl;

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPicDesc() {
		return picDesc;
	}

	public void setPicDesc(String picDesc) {
		this.picDesc = picDesc;
	}

	public String getPicUrl() {
		picUrl=Utils.getValue("app.resources.url", "").trim() + getPicPath();
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
