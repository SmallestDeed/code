package com.sandu.api.activity.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WxSpringActivityAdd implements Serializable{

    @ApiModelProperty(value = "转盘id")
    private String wxActLuckyWheelId;

    @ApiModelProperty(value = "拼图电影票总数量")
    private Integer filmToalNum;

    @ApiModelProperty(value = "每日签到红包金额")
    private BigDecimal redPacketDayNum;

    @ApiModelProperty(value = "签到红包总金额")
    private BigDecimal redPacketNum;

    @ApiModelProperty(value = "创建人")
    private String creator;

}