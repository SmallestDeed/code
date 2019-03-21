package com.sandu.api.grouppurchase.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:18 2018/12/6
 */

@Data
public class GroupPurchaseActivityUpdate implements Serializable {

    @ApiModelProperty("活动ID")
    private Long activityId;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("商品id")
    private Long spuId;

    @ApiModelProperty("活动开始时间")
    private Date activityStartTime;

    @ApiModelProperty("活动结束时间")
    private Date activityEndTime;

    @ApiModelProperty("拼团有效期（天）")
    private Integer groupValidDay;

    @ApiModelProperty("拼团有效期（小时）")
    private Integer groupValidHour;

    @ApiModelProperty("拼团有效期（分）")
    private Integer groupValidMinute;

    @ApiModelProperty("成团人数")
    private Integer totalNumber;

    @ApiModelProperty("活动状态")
    private String activityStatus;

    @ApiModelProperty("限购数量")
    private Integer purchaseLimitAmount;

    @ApiModelProperty("是否开启凑团")
    private Byte gatherFlag;

    @ApiModelProperty("是否虚拟成团")
    private Byte virtualFlag;

    @ApiModelProperty("是否优惠叠加")
    private Byte couponFlag;

    @ApiModelProperty("创建人")
    private String modifier;

}
