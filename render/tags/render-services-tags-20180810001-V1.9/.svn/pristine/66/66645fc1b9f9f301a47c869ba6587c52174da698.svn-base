package com.nork.user.model.small;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: UserGuide.java
 * @Package com.nork.user.model.small
 * @Description:用户指南-用户指南
 * @createAuthor pandajun
 * @CreateDate 2016-11-21 20:22:33
 * @version V1.0
 */
public class UserGuideSmall implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主健ID */
	private Integer id;
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
	/** 名称 */
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserGuideSmall() {
		super();
	}

	public UserGuideSmall(Integer id, Date gmtCreate, String creator, String sysCode, Date gmtModified, String modifier,
			Integer isDeleted, String name) {
		super();
		this.id = id;
		this.gmtCreate = gmtCreate;
		this.creator = creator;
		this.sysCode = sysCode;
		this.gmtModified = gmtModified;
		this.modifier = modifier;
		this.isDeleted = isDeleted;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sysCode == null) ? 0 : sysCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGuideSmall other = (UserGuideSmall) obj;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (modifier == null) {
			if (other.modifier != null)
				return false;
		} else if (!modifier.equals(other.modifier))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sysCode == null) {
			if (other.sysCode != null)
				return false;
		} else if (!sysCode.equals(other.sysCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserGuideSmall [id=" + id + ", gmtCreate=" + gmtCreate + ", creator=" + creator + ", sysCode=" + sysCode
				+ ", gmtModified=" + gmtModified + ", modifier=" + modifier + ", isDeleted=" + isDeleted + ", name="
				+ name + "]";
	}

}
