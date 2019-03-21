package com.nork.home.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nork.common.model.Mapper;






public class DesignProgramDiy extends Mapper implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	
	/**设计推荐code**/
	private String recommendCode;
	/**设计推荐风格**/
	private String recommendStyle;
	/**  方案编码  **/
	private String planCode;
	/**  方案名称  **/
	private String planName;
	private String planSysCode;
	private String designSysCode;
	private String description;
	/**  样板间编码  **/
	private String designCode;
	private String picPath;
	/**  样板间名称  **/
	private String designName;
	/**  方案来源类型  **/
	private String designSourceType;
	/**设计风格id**/
	private Integer designStyleId;
	private Integer houseTypeId;
	/**  通用面积  **/
	private String spaceAreas;
	/*渲染图集合*/
	private List renderPicList = new ArrayList();
	public List getRenderPicList() {
		return renderPicList;
	}
	public void setRenderPicList(List renderPicList) {
		this.renderPicList = renderPicList;
	}
	public Integer getHouseTypeId() {
		return houseTypeId;
	}
	public void setHouseTypeId(Integer houseTypeId) {
		this.houseTypeId = houseTypeId;
	}
	/**  通用id  **/
	private Integer spaceId;
	private String picId;

	
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlanSysCode() {
		return planSysCode;
	}
	public void setPlanSysCode(String planSysCode) {
		this.planSysCode = planSysCode;
	}
	public String getDesignSysCode() {
		return designSysCode;
	}
	public void setDesignSysCode(String designSysCode) {
		this.designSysCode = designSysCode;
	}
	
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getSpaceAreas() {
		return spaceAreas;
	}
	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}
	public Integer getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRecommendCode() {
		return recommendCode;
	}
	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}
	public String getRecommendStyle() {
		return recommendStyle;
	}
	public void setRecommendStyle(String recommendStyle) {
		this.recommendStyle = recommendStyle;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getDesignCode() {
		return designCode;
	}
	public void setDesignCode(String designCode) {
		this.designCode = designCode;
	}
	public String getDesignName() {
		return designName;
	}
	public void setDesignName(String designName) {
		this.designName = designName;
	}
	public String getDesignSourceType() {
		return designSourceType;
	}
	public void setDesignSourceType(String designSourceType) {
		this.designSourceType = designSourceType;
	}
	public Integer getDesignStyleId() {
		return designStyleId;
	}
	public void setDesignStyleId(Integer designStyleId) {
		this.designStyleId = designStyleId;
	}
	


}
