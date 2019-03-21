package com.sandu.api.system.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysUserLastLoginLog implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long id;
	/**  与sys_user主键关联  **/
	private Long userId;
	/**  最后登录时间  **/
	private Date lastLoginTime;
	/**  客户端IP  **/
	private String clientIp;
	/**  服务器IP  **/
	private String serverIp;
	/**  登录设备  **/
	private String loginDevice;
	/**  操作系统型号  **/
	private String systemModel;
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
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;
	
	/** 用户登录总次数  add by chenm 2017.11.11 **/
	private Integer loginCount;

}
