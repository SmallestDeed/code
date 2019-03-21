package com.sandu.api.act2.input;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ActRedPacketAdd implements Serializable {

    @NotNull(message = "活动名称不能为空")
    @ApiModelProperty(value = "砍价活动名称")
    private String actName;
    
    @NotNull(message = "活动金额")
    @ApiModelProperty(value = "活动金额")
    private Double actAmount;        
 
        
    @NotNull(message = "活动开始时间不能为空")
    @ApiModelProperty(value = "活动开始时间")
    private String beginTime;

    @NotNull(message = "活动结束时间不能为空")
    @ApiModelProperty(value = " 活动结束时间")
    private String endTime;

   

}
