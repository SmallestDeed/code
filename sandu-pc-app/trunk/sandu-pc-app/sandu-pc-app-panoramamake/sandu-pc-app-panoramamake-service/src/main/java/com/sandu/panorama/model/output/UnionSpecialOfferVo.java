package com.sandu.panorama.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenm on 2018/8/2.
 */
@Data
public class UnionSpecialOfferVo implements Serializable{
    @ApiModelProperty(value="优惠活动Id",dataType = "int")
    private Integer id;
    @ApiModelProperty(value="活动名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "活动内容",dataType = "String")
    private String content;
    @ApiModelProperty(value="活动开始时间")
    private String effectiveBegin;
    @ApiModelProperty(value="活动结束时间")
    private String effectiveEnd;
}

