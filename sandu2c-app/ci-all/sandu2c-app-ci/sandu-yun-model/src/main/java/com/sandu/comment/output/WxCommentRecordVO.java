package com.sandu.comment.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class WxCommentRecordVO implements Serializable {

    @ApiModelProperty("id")
    private Long id;

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

    @ApiModelProperty("描述相符级别")
    private Byte descLevel;

    @ApiModelProperty("父级评论id")
    private Long pid;

    @ApiModelProperty("评论")
    private String comment;

    @ApiModelProperty("点赞次数")
    private Integer praiseCount;

    @ApiModelProperty("是否匿名")
    private Byte isShowName;

    @ApiModelProperty("点赞状态")
    private Integer praiseStatus;

    @ApiModelProperty("初始评论")
    private String initComment;

    @ApiModelProperty("头像路径")
    private String headPath;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("sku信息")
    private String skuInfo;

    @ApiModelProperty("图片")
    private List<String> commentPics;

    @ApiModelProperty("评论时间")
    private Date gmtCreate;

    @ApiModelProperty("初始评论id")
    private Long initUserId;

    @ApiModelProperty("初始评论昵称")
    private String initNickName;
}
