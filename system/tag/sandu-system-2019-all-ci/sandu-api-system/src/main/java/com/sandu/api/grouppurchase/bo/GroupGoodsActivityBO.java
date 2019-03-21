package com.sandu.api.grouppurchase.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/7 12:01
 * @since 1.8
 */

@Setter
@Getter
@ToString
public class GroupGoodsActivityBO {

    Long activityId;

    String activityName;

    /**
     * 商品spu_id(关联base_goods_spu)
     */
    private Long spuId;

    /**
     * 活动结束时间
     */
    private Date activityStartTime;

    /**
     * 活动结束时间
     */
    private Date activityEndTime;

    /**
     * 拼团有效期(天)
     */
    private Integer groupValidDay;

    /**
     * 拼团有效期(小时)
     */
    private Integer groupValidHour;

    /**
     * 拼团有效期(分钟)
     */
    private Integer groupValidMinute;

    /**
     * 成团人数
     */
    private Integer totalNumber;

    /**
     * 限购数量
     */
    private Integer purchaseLimitAmount;

    /**
     * 开启凑团标识(0-不开启;1-开启)
     */
    private Byte gatherFlag;

    /**
     * 虚拟成团标识(0-不虚拟成团;1-虚拟成团)
     */
    private Byte virtualFlag;

    /**
     * 优惠叠加标识(0-不可使用;1-可使用)
     */
    private Byte couponFlag;

    /**
     * 活动状态(0-草稿;1-未开始;2-进行中;3-已结束;4-已失效)
     */
    private Byte activityStatus;

    /**
     * 是否删除
     */
    private Integer isDeleted;
}
