package com.nork.onekeydesign.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecommendationSpaceResult implements Serializable {

	private static final long serialVersionUID = 1L;
	/*设计推荐Id*/
	private Integer templetId;
	private Integer picId;
	public Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	/*名称*/
	private String designName;
	/*编码*/
	private String designCode;
	/*图片路径*/
	private String picPath;
	/*空间面积*/
	private String spaceArea;
	/*空间描述*/
	private String description;
	/*渲染图*/
	private String renderPicIds;
	/*渲染图集合*/
	private List renderPicList = new ArrayList();
	public Integer getTempletId() {
		return templetId;
	}
	public void setTempletId(Integer templetId) {
		this.templetId = templetId;
	}
	public String getDesignName() {
		return designName;
	}
	public void setDesignName(String designName) {
		this.designName = designName;
	}
	public String getDesignCode() {
		return designCode;
	}
	public void setDesignCode(String designCode) {
		this.designCode = designCode;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(String spaceArea) {
		this.spaceArea = spaceArea;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRenderPicIds() {
		return renderPicIds;
	}
	public void setRenderPicIds(String renderPicIds) {
		this.renderPicIds = renderPicIds;
	}
	public List getRenderPicList() {
		return renderPicList;
	}
	public void setRenderPicList(List renderPicList) {
		this.renderPicList = renderPicList;
	}
}
