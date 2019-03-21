package com.nork.system.model;

import java.io.Serializable;
import java.util.Date;

import com.nork.common.model.Mapper;
/**
 * @Title: SysRoleGroup.java
 * @Package com.nork.system.model
 * @Description:角色组
 * @createAuthor wutehua
 * @CreateDate 2018-01-03 10:10:08
 * @version V1.0
 */
public class SysRoleGroup extends Mapper implements Serializable{
 private Long id;//主键id
 private String code;//角色组编码
 private String name;//角色名称
 private String creator;//创建者
 private Date gmtCreate;//创建时间
 private String modifier;//修改人
 private Date gmtModified;//修改时间
 private Integer isDeleted;//是否删除
 private String remark;//备注
 private Integer userId;//用户id
 private String roleName;//角色组配置角色的名称
 private Integer types;//角色组类型
 private String typeName;//角色组类型

 private String roleNameStr;//展示浮动效果

 private Long userRoleGroupId;//用户角色组中间表id

 private String strId;//角色组 主键id

 private int isChecked;

 private Long platformId;//平台id 该字段只用来数据的传递

 public Long getId() {
  return id;
 }
 public void setId(Long id) {
  this.id = id;
 }
 public String getCode() {
  return code;
 }
 public void setCode(String code) {
  this.code = code;
 }
 public String getName() {
  return name;
 }
 public void setName(String name) {
  this.name = name;
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
 public String getRemark() {
  return remark;
 }
 public void setRemark(String remark) {
  this.remark = remark;
 }
 public Long getPlatformId() {
  return platformId;
 }
 public void setPlatformId(Long platformId) {
  this.platformId = platformId;
 }
 public int getIsChecked() {
  return isChecked;
 }
 public void setIsChecked(int isChecked) {
  this.isChecked = isChecked;
 }
 public Long getUserRoleGroupId() {
  return userRoleGroupId;
 }
 public void setUserRoleGroupId(Long userRoleGroupId) {
  this.userRoleGroupId = userRoleGroupId;
 }
 public Integer getUserId() {
  return userId;
 }
 public void setUserId(Integer userId) {
  this.userId = userId;
 }
 public String getStrId() {
  return strId;
 }
 public void setStrId(String strId) {
  this.strId = strId;
 }

 public String getRoleNameStr() {
  if(null != roleName){
   roleNameStr = roleName.length()>20?roleName.substring(0, 20).concat("..."):roleName;
  }
  return roleNameStr;
 }
 public void setRoleNameStr(String roleNameStr) {
  this.roleNameStr = roleNameStr;
 }

 public String getRoleName() {
  return roleName;
 }
 public void setRoleName(String roleName) {
  this.roleName = roleName;
 }
 public Integer getTypes() {
  return types;
 }
 public void setTypes(Integer types) {
  this.types = types;
 }
 public String getTypeName() {
  return typeName;
 }
 public void setTypeName(String typeName) {
  this.typeName = typeName;
 }

}
