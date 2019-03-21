package com.sandu.goods.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityVO implements Serializable {

    @ApiModelProperty("SPU_ID")
    Integer id;

    @ApiModelProperty("活动ID")
    Integer activityId;

    @ApiModelProperty("活动状态(0-草稿;1-未开始;2-进行中;3-已结束;4-已失效)")
    Integer activityStatus;

    @ApiModelProperty("成团人数")
    Integer totalNumber;
}
