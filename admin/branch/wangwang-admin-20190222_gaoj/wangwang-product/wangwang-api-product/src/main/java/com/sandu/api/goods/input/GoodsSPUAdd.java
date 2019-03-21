package com.sandu.api.goods.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsSPUAdd implements Serializable
{
    @ApiModelProperty(value = "商品ID", required = true)
    @Min(value = 1, message = "ID必须大于0")
    private Integer spuId;

    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称最大为100个字")
    private String name;

    @ApiModelProperty(value = "商品描述")
    private String describe;

    @ApiModelProperty(value = "主缩略图ID")
    private Integer mainPicId;

    @ApiModelProperty(value = "所有图片ID，逗号分隔")
    private String picIds;

    @ApiModelProperty(value = "限购信息(0不限购，>0为限购数量，<0只限会员购买)")
    @NotNull(message = "限购信息不能为空")
    private Integer limitation;

    @ApiModelProperty(value = "固定运费")
    @Min(value = 0, message = "运费必须大于等于0")
    private BigDecimal fixTransCost;

    @ApiModelProperty(value = "是否预售")
    @NotNull
    private Integer presell;

    @ApiModelProperty(value = "预售结束时间")
    @Future(message = "截止时间不能早于当前时间")
    private Date presellDDL;

    @ApiModelProperty(value = "支付尾款开始时间")
    @Future(message = "支付尾款开始时间不能早于当前时间")
    private Date payTailStarttime;

    @ApiModelProperty(value = "支付尾款结束时间")
    @Future(message = "支付尾款结束时间不能早于当前时间")
    private Date payTailEndtime;

    @ApiModelProperty(value = "定金")
    @Min(value = 0, message = "定金必须大于等于0")
    private BigDecimal earnest;

    @ApiModelProperty(value = "预售商品发货时间")
    @Min(value = 0, message = "预售商品发货时间必须大于等于0")
    private Integer shipmentsPresell;

    @ApiModelProperty(value = "总库存")
    @Min(value = 0, message = "总库存必须大于等于0")
    private Integer totalRepertory;

    @ApiModelProperty(value = "是否特卖")
    @NotNull
    private Integer isSpeSell;

    @ApiModelProperty(value = "最早发货时间（天数）")
    @Min(value = 0, message = "最早发货时间（天数）必须大于等于0")
    private Integer shipmentsDay;

    @ApiModelProperty(value = "最早发货时间（日期）")
    @Future(message = "最早发货时间（日期）不能早于当前时间")
    private Date shipmentsDate;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String appId;

    private Integer companyId;
}
