package com.sandu.comment.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxCommentRecordQuery implements Serializable{

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("排序方式(0:最新、1：最热)")
    private Long sortType;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @ApiModelProperty("每页显示条数")
    private Integer pageSize;

    @ApiModelProperty("是否图片评论(0:否、1:是)")
    private Integer isPicComment;

    @ApiModelProperty("0:订单，1：方案")
    private Integer type;

    @ApiModelProperty("SPUID")
    private Integer spuId;

    @ApiModelProperty("方案ID")
    private Integer planId;
}