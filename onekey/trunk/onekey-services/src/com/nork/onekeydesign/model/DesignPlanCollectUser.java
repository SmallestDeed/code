package com.nork.onekeydesign.model;

import java.io.Serializable;

import com.nork.common.model.Mapper;

public class DesignPlanCollectUser extends Mapper implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**  用户ID  **/
	private Integer userId;
	/**  用户名称  **/
	private String userName;
	/**  来源类型  **/
	private String srcType;
	/**  来源编号  **/
	private String srcCode;
	
	/**  来源ID  **/
	private Integer designId;
	/**  来源名称  **/
	private String designName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSrcType() {
		return srcType;
	}
	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}
	public String getSrcCode() {
		return srcCode;
	}
	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	public String getDesignName() {
		return designName;
	}
	public void setDesignName(String designName) {
		this.designName = designName;
	}
	

}
