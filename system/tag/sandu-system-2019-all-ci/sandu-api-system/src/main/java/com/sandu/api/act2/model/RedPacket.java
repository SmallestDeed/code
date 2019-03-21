package com.sandu.api.act2.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RedPacket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 未开始
	 */
	public static Integer STATUS_UNBEGIN = 0;
    /**
     * 进行中
     */
	public static Integer STATUS_ONGOING = 10;
    /**
     * 已结束
     */
	public static Integer STATUS_ENDED = 20;
	
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

   
}