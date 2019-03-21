package com.sandu.api.user.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 主键id  **/
    private Long id;
    /**  角色编码  **/
	private String code;
	/**  角色名称  **/
	private String name;
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
	/**  角色等级  **/
	private String level;
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
	
	private Long platformId;
}
