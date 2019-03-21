package com.sandu.api.act3.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LuckyWheel implements Serializable {
	
	public static final Integer CONFIG_ITEM_PER_DAY = 1;
	public static final Integer CONFIG_ITEM_PER_ACT = 2;
	
	private static final long serialVersionUID = 1L;
    
	private String id;

    private String name;

    private String rule;
    
    private Date beginTime;

    private Date endTime;

    /**
     * 配置项:1:每天配置,2:活动整体配置
     */
    private Integer configItem;
    
    private Integer lotteryNumPerDayDefalut;
    
    private Integer lotteryNumPerDayMax;
    
    private Integer lotteryNumDefalut;
    
    private Integer lotteryNumMax;

    private Integer lotteryTotalCount;

    private Integer regCount;

    private Integer awardsTotalCount;
    
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

    //列表查询参数
    private Integer statusCode;

}