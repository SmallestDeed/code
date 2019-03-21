package com.nork.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**
 * @Title: UserGuidePic.java
 * @Package com.nork.user.model
 * @Description:用户指南-用户指南图片列表
 * @createAuthor pandajun
 * @CreateDate 2016-12-07 14:42:28
 * @version V1.0
 */
public class UserGuidePic extends Mapper implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	/** 图片地址 **/
	private String picUrl;
	/** 创建时间 */
	private Date gmtCreate;
	/** 创建人 */
	private String creator;
	/** 系统编码 */
	private String sysCode;
	/** 修改时间 */
	private Date gmtModified;
	/** 修改人 */
	private String modifier;
	/** 是否删除 */
	private Integer isDeleted;
	/** 排序 */
	private Integer sort;

	private Integer ugId;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getUgId() {
		return ugId;
	}

	public void setUgId(Integer ugId) {
		this.ugId = ugId;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		UserGuidePic other = (UserGuidePic) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getPicUrl() == null ? other.getPicUrl() == null : this.getPicUrl().equals(other.getPicUrl()))
				&& (this.getGmtCreate() == null ? other.getGmtCreate() == null
						: this.getGmtCreate().equals(other.getGmtCreate()))
				&& (this.getCreator() == null ? other.getCreator() == null
						: this.getCreator().equals(other.getCreator()))
				&& (this.getGmtModified() == null ? other.getGmtModified() == null
						: this.getGmtModified().equals(other.getGmtModified()))
				&& (this.getModifier() == null ? other.getModifier() == null
						: this.getModifier().equals(other.getModifier()))
				&& (this.getIsDeleted() == null ? other.getIsDeleted() == null
						: this.getIsDeleted().equals(other.getIsDeleted()))
				&& (this.getSysCode() == null ? other.getSysCode() == null
						: this.getSysCode().equals(other.getSysCode()))
				&& (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getPicUrl() == null) ? 0 : getPicUrl().hashCode());
		result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
		result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
		result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
		result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
		result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
		result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
		;
		return result;
	}

	/** 获取对象的copy **/
	public UserGuidePic copy() {
		UserGuidePic obj = new UserGuidePic();
		obj.setPicUrl(this.picUrl);
		obj.setGmtCreate(this.gmtCreate);
		obj.setCreator(this.creator);
		obj.setGmtModified(this.gmtModified);
		obj.setModifier(this.modifier);
		obj.setIsDeleted(this.isDeleted);
		obj.setSysCode(this.sysCode);
		obj.setSort(this.sort);
		return obj;
	}

	/** 获取对象的map **/
	public Map toMap() {
		Map map = new HashMap();
		map.put("picUrl", this.picUrl);
		map.put("gmtCreate", this.gmtCreate);
		map.put("creator", this.creator);
		map.put("gmtModified", gmtModified);
		map.put("modifier", modifier);
		map.put("isDeleted", isDeleted);
		map.put("sysCode", sysCode);
		map.put("sort", sort);
		return map;
	}
}
