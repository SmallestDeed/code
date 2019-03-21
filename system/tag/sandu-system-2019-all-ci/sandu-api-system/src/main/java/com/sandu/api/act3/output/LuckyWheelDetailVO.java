package com.sandu.api.act3.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 幸运大转盘活动详情输出实体
 * @author WangHaiLin
 * @date 2019/1/15  14:46
 */
@Data
public class LuckyWheelDetailVO implements Serializable {
    @ApiModelProperty(value = "活动Id")
    private String id;
    @ApiModelProperty(value = "活动名称")
    private String name;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "活动规则")
    private String rule;
    @ApiModelProperty(value = "实际结束时间")
    private String realEndTime;
    @ApiModelProperty(value = "活动状态(未开始:0;进行中:10;已结束:20)")
    private Integer statusCode;

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
