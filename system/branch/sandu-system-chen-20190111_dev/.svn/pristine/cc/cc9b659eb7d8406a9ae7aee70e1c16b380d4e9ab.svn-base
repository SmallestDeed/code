package com.sandu.api.act3.input;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 幸运转盘新增入参
 */

@Data
public class LuckyWheelAdd implements Serializable {

    @ApiModelProperty(value = "活动名称")
    @NotNull(message = "活动名称不能为空")
    @Size(max = 20, message = "活动名称不能超过{max}个字符")
    private String actName;

    @ApiModelProperty(value = "活动开始时间")
    @NotNull(message = "活动开始时间不能为空")
    private String beginTime;

    @ApiModelProperty(value = " 活动结束时间")
    @NotNull(message = "活动结束时间不能为空")
    private String endTime;

    @ApiModelProperty(value = " 活动规则")
    @NotNull(message = "活动规则不能为空")
    private String actRule;

    @ApiModelProperty(value = "配置项(1:每天配置,2:整体配置)")
    private Integer configItem;

    @ApiModelProperty(value = " 每人每天默认抽奖次数")
    private Integer lotteryNumPerDayDefalut;

    @ApiModelProperty(value = "每人每天最大抽奖次数")
    private Integer lotteryNumPerDayMax;

    @ApiModelProperty(value = "每人默认抽奖次数")
    private Integer lotteryNumDefalut;

    @ApiModelProperty(value = "每人最大抽奖次数")
    private Integer lotteryNumMax;

}
