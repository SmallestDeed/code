package com.sandu.api.act3.model;

import lombok.Data;

@Data
public class LuckyWheelLotteryRecordActAggregated {
	
    private String id;

    private String actId;

    private String openId;

    private Integer lotteryCount;
    
    private Integer remainLotteryCount;

    private Integer lotteryNumMax;

    private String appId;

    
}