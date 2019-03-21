package com.sandu.system.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysUserRolePO implements Serializable {

	private static final long serialVersionUID = -8882446514204985722L;

	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 角色id
	 */
	private Integer roleId;
	
	/**
	 * 创建者
	 */
	private String creator;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 修改人
	 */
	private String modifier;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 系统编码
	 */
	private String sysCode;
	
}
