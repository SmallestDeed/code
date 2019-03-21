package com.sandu.api.act3.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 转盘活动列表查询
 * @author WangHaiLin
 * @date 2019/1/15  10:28
 */
@Data
public class LuckyWheelQuery implements Serializable {

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "活动状态(未开始:0;进行中:10;已结束:20)")
    private Integer statusCode;

    @ApiModelProperty(value = "页数")
    private Integer pageNum=1;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize=20;

    private String appId="wx42e6b214e6cdaed3";
}
