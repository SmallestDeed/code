package com.sandu.api.grouppurchase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:group_activity_open表的实体类
 * @version
 * @author:  Sandu
 * @创建时间: 2018-12-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupPurchaseOpen implements Serializable {
    /**
     * 活动ID
     */
    private Long id;

    /**
     * 开团用户ID
     */
    private Long userId;

    /**
     * 活动ID(关联group_purchase_activity表)
     */
    private Long purchaseActivityId;


    /**
     * 商品spu_id(关联base_goods_spu)
     */
    private Long spuId;

    /**
     * 团长手机号码
     */
    private String telephone;

    /**
     * 成团人数
     */
    private Integer totalNumber;

    /**
     * 已参团人数
     */
    private Integer joinNumber;

    /**
     * 待参团人数
     */
    private Integer unjoinNumber;

    /**
     * 成团状态(0-等待成团;1-拼团成功;2-拼团失败)
     */
    private Byte openStatus;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 到期时间
     */
    private Date expireDate;
    
    /**
     * 开团时间
     */
    private Date openDate;

    private Long purchaseGoodsId;
}