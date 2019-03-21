package com.sandu.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxCommentRecord implements Serializable{
    private Long id;

    /**
     * 方案id
     */
    private Long planId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品spuId
     */
    private Long spuId;

    /**
     * 商品skuId
     */
    private Long skuId;

    /**
     * 描述相符级别
     */
    private Byte descLevel;

    /**
     * 父级评论id
     */
    private Long pid;

    /**
     * 评论
     */
    private String comment;

    /**
     * 点赞次数
     */
    private Integer praiseCount;

    /**
     * 评论图片ids
     */
    private String picIds;

    /**
     * 评论类型
     */
    private Byte type;

    /**
     * 是否匿名
     */
    private Byte isShowName;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    /**
     * 点赞状态
     */
    private Integer praiseStatus;

    /**
     * 初始评论
     */
    private String initComment;

    /**
     * 初始评论id
     */
    private Long initUserId;

    /**
     * 初始评论昵称
     */
    private String initNickName;

    /**
     * 头像路径
     */
    private String headPath;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * sku信息
     */
    private String skuInfo;

    /**
     * 图片
     */
    private List<String> commentPics;

}