package com.sandu.comment.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxCommentRecordAdd implements Serializable{

    @ApiModelProperty("方案id")
    private Long planId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商品spuId")
    private Long spuId;

    @ApiModelProperty("商品skuId")
    private Long skuId;

    @ApiModelProperty("描述相符等级")
    private Byte descLevel;

    @ApiModelProperty("父评论id")
    private Long pid;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论图片资源ids")
    private String picIds;

    @ApiModelProperty("评论类型（0：商品、1：方案）")
    private Byte type;

    @ApiModelProperty("是否匿名（0：否、1：是）")
    private Byte isShowName;

    @ApiModelProperty("产品ID")
    private Integer productId;

    @ApiModelProperty("创建的用户ID")
    private Integer creator;
}