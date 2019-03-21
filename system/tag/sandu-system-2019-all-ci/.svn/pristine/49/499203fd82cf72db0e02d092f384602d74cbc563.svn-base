package com.sandu.api.act.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:31
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class WxActBargainRegistrationAnalyseResultVO implements Serializable {

        
    @ApiModelProperty(value = "报名id")
    private String regId;
        
    @ApiModelProperty(value = "活动参与人头像")
    private String headPic;
    
    @ApiModelProperty(value = "活动参与人open_id")
    private String openId;
        
        
    @ApiModelProperty(value = "活动参与人昵称")
    private String nickname;
        
    @ApiModelProperty(value = "参与时间")        
    private Date gmtCreate;
    
    @ApiModelProperty(value = "砍价人数")        
    private Integer inviteCutCount;
    
    @ApiModelProperty(value = "好友砍价金额")        
    private Integer inviteCutPriceSum;
        
    @ApiModelProperty(value = "装修状态")
    private Integer decorateStatus;
    
    @ApiModelProperty(value = "发货状态")
    private Integer shipmentStatus;
        
    @ApiModelProperty(value = "状态")        
    private Integer status;
    
    @ApiModelProperty(value = "收货人") 
    private String receiver;
    
    @ApiModelProperty(value = "联系电话") 
    private String mobile;
    
    @ApiModelProperty(value = "收货地址")        
    private String address;
        
    
}
