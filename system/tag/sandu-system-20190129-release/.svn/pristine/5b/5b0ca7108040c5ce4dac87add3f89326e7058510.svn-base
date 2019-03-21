package com.sandu.api.grouppurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 17:45 2018/12/6
 */

@Data
public class GroupPurchaseActivityVO implements Serializable {

    @ApiModelProperty("活动ID")
    private Long activityId;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("商品id")
    private Long spuId;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("活动开始时间")
    private Date activityStartTime;

    @ApiModelProperty("距离活动开始还有多久")
    private Long howLongStartTime;

    @ApiModelProperty("活动结束时间")
    private Date activityEndTime;

    @ApiModelProperty("距离活动结束还有多久")
    private Long howLongEndTime;

    @ApiModelProperty("拼团有效期（天）")
    private Integer groupValidDay;

    @ApiModelProperty("拼团有效期（小时）")
    private Integer groupValidHour;

    @ApiModelProperty("拼团有效期（分）")
    private Integer groupValidMinute;

    @ApiModelProperty("成团人数")
    private Integer totalNumber;

    @ApiModelProperty("限购数量")
    private Integer purchaseLimitAmount;

    @ApiModelProperty("是否开启凑团")
    private Byte gatherFlag;

    @ApiModelProperty("是否虚拟成团")
    private Byte virtualFlag;

    @ApiModelProperty("是否优惠叠加")
    private Byte couponFlag;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("活动状态(0-草稿;1-未开始;2-进行中;3-已结束;4-已失效)")
    private Integer activityStatus;

    @ApiModelProperty("总订单金额")
    private Double totalOrderAmount;

    @ApiModelProperty("总订单数量")
    private Integer totalOrderCount;

    @ApiModelProperty("总订单人数")
    private Integer totalOrderPerson;

    @ApiModelProperty("商品名称")
    private String productText;

    @ApiModelProperty("商品路径")
    private String productPath;

    @ApiModelProperty("活动链接")
    private String activityPath;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否满团：true->满团，false->未满团")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    Boolean isGroupOverflow;
}
