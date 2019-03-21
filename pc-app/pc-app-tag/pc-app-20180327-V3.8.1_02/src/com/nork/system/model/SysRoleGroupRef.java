package com.nork.system.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: SysRoleGroupRef.java 
 * @Package com.nork.system.model
 * @Description:角色组—角色中间表
 * @createAuthor wutehua 
 * @CreateDate 2018-01-03 10:10:08
 * @version V1.0   
 */
public class SysRoleGroupRef extends Mapper implements Serializable{
	private Long id;//主键id
	private Long roleId;//角色表id
	private Long roleGroupId;//角色组表id
	private String creator;//创建者
	private Date gmtCreate;//创建时间
	private String modifier;//修改人
	private Date gmtModified;//修改时间
	private Integer isDeleted;//是否删除

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getRoleGroupId() {
		return roleGroupId;
	}
	public void setRoleGroupId(Long roleGroupId) {
		this.roleGroupId = roleGroupId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
