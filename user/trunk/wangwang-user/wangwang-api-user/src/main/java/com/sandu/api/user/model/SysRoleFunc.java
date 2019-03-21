package com.sandu.api.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class SysRoleFunc implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 主键id  **/
	private Long id;
	/**  角色id  **/
	private Long roleId;
	/**  菜单id  **/
	private Long funcId;
	/**	字段权限	**/
	private String queryFields;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  字符备用3  **/
	private String att3;
	/**  字符备用4  **/
	private String att4;
	/**  字符备用5  **/
	private String att5;
	/**  字符备用6  **/
	private String att6;
	/**  时间备用1  **/
	private Date dateAtt1;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  整数备用1  **/
	private Integer numAtt1;
	/**  整数备用2  **/
	private Integer numAtt2;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysRoleFunc other = (SysRoleFunc) obj;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (funcId.longValue()!=other.funcId.longValue())
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcId == null) ? 0 : funcId.intValue());
		return result;
	}
	
	
}
