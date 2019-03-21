package com.sandu.api.act2.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RedPacketInviteRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    private String id;

    private String regId;

    private String openId;

    private String nickname;

    private String headPic;

    private String appId;

    private Long companyId;

    private Date gmtCreate;

    private Integer isDeleted;

    
}