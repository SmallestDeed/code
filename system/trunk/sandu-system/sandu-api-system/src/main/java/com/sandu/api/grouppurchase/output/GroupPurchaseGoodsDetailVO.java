package com.sandu.api.grouppurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/7 10:51
 * @since 1.8
 */

@Setter
@Getter
@ToString
public class GroupPurchaseGoodsDetailVO implements Serializable {

    @ApiModelProperty("SPU_ID")
    private Integer spuId;

    @ApiModelProperty("banner图列表")
    private List<String> smallPiclist;

    @ApiModelProperty("商品名称（spu名称，为了减少前端改动所以原字段名没改）")
    private String productName;

    @ApiModelProperty("原价")
    private String salePrice;

    @ApiModelProperty("优惠价")
    private String price;

    @ApiModelProperty("活动价")
    private String activityPrice;

    @ApiModelProperty("是否收藏")
    private Integer isFavorite;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @ApiModelProperty("商品描述")
    private String productDesc;

    @ApiModelProperty("限购信息")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer limitation;

    @ApiModelProperty("预售新品发货时间")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer sendOutPresell;

    @ApiModelProperty("商品最早发货时间（天数）")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer sendOutDay;

    @ApiModelProperty("商品最早发货时间（日期）")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Date sendOutDate;

    @ApiModelProperty("运费")
    private String transportPay;

    @ApiModelProperty("商品封面图")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer picId;

    @ApiModelProperty("封面图路径")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String picPath;

    @ApiModelProperty("库存")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer totalInventory;

    @ApiModelProperty("拼团活动信息")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    GroupPurchaseActivityVO activity;
}
