package com.sandu.api.act2.model.search;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RedPacketSearch implements Serializable {
	
	private String id;

    private String actName;

    private String actRule;

    private Date beginTime;

    private Date endTime;
    
    private Double totalAmount;

    private Integer regCount;

    private Integer redPacketConfigCount;

    private Integer isEnable;

    private Long userId;
    
    private String appId;

    private String appName;

    private Long companyId;

    private String companyName;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;
    
    private Integer statusCode;


   
}