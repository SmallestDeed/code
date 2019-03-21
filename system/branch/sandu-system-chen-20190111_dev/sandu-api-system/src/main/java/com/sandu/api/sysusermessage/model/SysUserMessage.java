package com.sandu.api.sysusermessage.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysUserMessage implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1764057875523200623L;

	private Long id;

	private String title;

	private String content;

	private Byte messageType;

	private Integer taskId;

	private Integer userId;

	private Byte isRead;

	private Byte status;

	private String failingReason;

	private String sysCode;

	private String creator;

	private Date gmtCreate;

	private String modifier;

	private Date gmtModified;

	private Integer isDeleted;

	private String remark;

	private Long platformId;

	private Long picId;
}