package com.sandu.api.act3.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LuckyWheelPrize implements Serializable {
	
	
	public static final Integer TYPE_NONE_AWARD = 0;
	public static final Integer TYPE_DUBI = 1;
	public static final Integer TYPE_CASH = 2;
	public static final Integer TYPE_PHONE_RECHARGE = 3;
	public static final Integer TYPE_EDATA = 4;
	public static final Integer TYPE_PRODUCT = 5;

	
	
	private static final long serialVersionUID = 1L;
	
    private String id;

    private String actId;

    private String name;

    private String img;
    
    private Integer num;

    private Integer remainNum;

    private Double probability;

    private Integer orderSeq;

    private Integer numPerDay;

    private Integer todayRemainNum;
    
    /**
     * 0:未中奖,1:度币,2:现金,3:话费,4:资料,5:物品(需要邮寄
     */
    private Integer type;
    
    private Double value;
    

    private String appId;

    private Long companyId;

    private Date gmtCreate;

    private Integer isDeleted;

    
}