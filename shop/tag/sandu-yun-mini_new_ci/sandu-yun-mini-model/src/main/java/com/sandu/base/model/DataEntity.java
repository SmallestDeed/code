package com.sandu.base.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * 数据Entity类
 * @author Administrator
 *
 */
public abstract class DataEntity <T> extends BaseEntity<T>{
	private static final long serialVersionUID = 1L;
	/***
	 * 创建者账号
	 */
	private String creator;
	/***
	 * 创建日期
	 */
	private Date gmtCreate;
	/***
	 * 修改这账号
	 */
	private String modifier;
	/***
	 * 修改日期
	 */
	private Date gmtModified;
	/**
	 * 是否删除 0:正常 1:已删除
	 */
	private int isDeleted;
	/***
	 * 系统唯一编码
	 */
	private String sysCode;

	public DataEntity() {
		super();
		this.setIsDeleted(DEL_FLAG_NORMAL);
	}
	
	public DataEntity(long id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		this.gmtModified = new Date();
		this.gmtCreate = this.gmtModified;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		this.gmtModified = new Date();
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@JsonIgnore
	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
}

