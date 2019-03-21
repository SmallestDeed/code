package com.sandu.api.act.output;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:24
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class WxActBargainAndRedDetailsVO implements Serializable {

	  
	@ApiModelProperty(value = "活动名称")
    private String actName;
	
    @ApiModelProperty(value = "活动规则")
    private String actRule;
        
        
    @ApiModelProperty(value = "产品名称")
    private String productName;
    
    @ApiModelProperty(value = "产品图片")
    private String productImg;
    
    @ApiModelProperty(value = "转发图片")
    private String shareImg;
        
    
    @ApiModelProperty(value = "原价")
    private Double productOriginalPrice;
        
        
    @ApiModelProperty(value = "优惠价")
    private Double productDiscountPrice;
        
        
    @ApiModelProperty(value = "底价")
    private Double productMinPrice;
        
        
    @ApiModelProperty(value = "产品剩余数量")
    private Integer productRemainCount;
        
    
    @ApiModelProperty(value = "参与人数")
    private Integer registrationCount;
        
    @ApiModelProperty(value = "剩余价格")
    private Double productRemainPrice;
    
    @ApiModelProperty(value = "活动开始时间")
    private String begainTime;
    
    @ApiModelProperty(value = "活动结束时间 ")
    private String endTime;
    
    @ApiModelProperty(value = "活动倒计时")
    private Long actRemainTime;
    
    
    
}
