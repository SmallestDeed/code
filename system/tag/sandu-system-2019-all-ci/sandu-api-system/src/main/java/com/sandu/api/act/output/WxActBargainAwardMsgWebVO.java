package com.sandu.api.act.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 砍价成功领取消息前端展示输出实体
 * @author WangHaiLin
 * @date 2018/11/22  14:05
 */
@Data
public class WxActBargainAwardMsgWebVO implements Serializable {

    @ApiModelProperty(value = "领取消息Id")
    private String awardmsgId;

    @ApiModelProperty(value = "活动id")
    private String actId;


    @ApiModelProperty(value = "消息内容")
    private String message;

}
