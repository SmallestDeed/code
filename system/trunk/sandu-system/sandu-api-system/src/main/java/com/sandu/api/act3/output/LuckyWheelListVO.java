package com.sandu.api.act3.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 转盘活动列表输出实体
 * @author WangHaiLin
 * @date 2019/1/15  10:34
 */
@Data
public class LuckyWheelListVO implements Serializable {

    @ApiModelProperty(value = "活动Id")
    private String id;
    @ApiModelProperty(value = "活动名称")
    private String name;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "活动状态(未开始:0;进行中:10;已结束:20)")
    private Integer statusCode;
    @ApiModelProperty(value = "实际结束时间")
    private String realEndTime;

}
