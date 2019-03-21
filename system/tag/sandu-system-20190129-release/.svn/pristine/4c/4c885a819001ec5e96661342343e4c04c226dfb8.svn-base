package com.sandu.api.act2.output;

import java.io.Serializable;
import java.util.List;

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
public class RedPacketActAndRegDetailsVO implements Serializable {

	  
	@ApiModelProperty(value = "活动名称")
    private String actName;
	
    @ApiModelProperty(value = "活动规则")
    private String actRule;
    
    private String regId;
    /**
     * 当前邀请人数
     */
    private Integer currentInviteNum;
    
    /**
     * 红包列表详情
     */
    private List<RedPacketAwardDetailsVO> awardList;
    
    private String actStatus;
    
   /* @ApiModelProperty(value = "活动开始时间")
    private String beginTime;
    
    @ApiModelProperty(value = "活动结束时间 ")
    private String endTime;
    
    @ApiModelProperty(value = "活动倒计时")
    private Long actRemainTime;*/
    
    
    
}
