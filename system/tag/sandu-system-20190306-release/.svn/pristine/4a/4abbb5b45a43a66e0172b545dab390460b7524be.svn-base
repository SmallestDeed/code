package com.sandu.api.act3.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 幸运大转盘修改入参
 * @author WangHaiLin
 * @date 2019/1/14  21:45
 */
@Data
public class LuckyWheelUpdate  implements Serializable{

    @ApiModelProperty(value = "活动名称")
    @NotNull(message = "活动Id不能为空")
    private String id;

    @ApiModelProperty(value = "活动名称")
    @Size(max = 20, message = "活动名称不能超过{max}个字符")
    private String actName;

    @ApiModelProperty(value = "活动开始时间")
    private String beginTime;

    @ApiModelProperty(value = " 活动结束时间")
    private String endTime;

    @ApiModelProperty(value = " 活动规则")
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
