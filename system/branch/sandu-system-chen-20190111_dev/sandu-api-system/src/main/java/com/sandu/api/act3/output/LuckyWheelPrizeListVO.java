package com.sandu.api.act3.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 奖品查询输出
 * @author WangHaiLin
 * @date 2019/1/16  19:33
 */
@Data
public class LuckyWheelPrizeListVO implements Serializable {

    @ApiModelProperty(value = "活动Id")
    private String actId;

    @ApiModelProperty(value = "活动名称")
    private String actName;

    @ApiModelProperty(value = "活动状态(未开始:0;进行中:10;已结束:20)")
    private Integer statusCode;

    @ApiModelProperty(value = "活动奖品")
    private List<LuckyWheelPrizeListBO> prizeList;

}
