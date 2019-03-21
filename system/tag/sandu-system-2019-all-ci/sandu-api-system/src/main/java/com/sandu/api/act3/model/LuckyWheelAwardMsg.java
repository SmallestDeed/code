package com.sandu.api.act3.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LuckyWheelAwardMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String id;

    private String actId;

    private String regId;

    private String openId;

    private String headPic;

    private String message;

    private String appId;

    private Long companyId;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

   
}