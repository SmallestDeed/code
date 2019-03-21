package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/12/24
 * @since : sandu_yun_1.0
 */
@Data
public class GroupPurchaseActivity implements Serializable{

    private static final long serialVersionUID = -1467826640720237556L;
    private Long id;

    private String activityName;

    private Date startTime;

    private Date endTime;

    private Long spuId;

    private Integer groupValidDay;

    private Integer groupValidHour;

    private Integer groupValidMinute;

    private Integer totalNumber;

    private Integer purchaseLimitAmount;

    private Integer gatherFlag;

    private Integer virtualFlag;

    private Integer couponFlag;

    private Integer activityStatus;

    private String productName;

    private String productImage;

    private Double activityPrice;

    private Long companyId;

    private Long skuId;

    private Integer sellQty;

    private String spuName;

    private String spuPicPath;

    private Double spuPrice;

    private String uniqueKey;

    public String getUniqueKey() {
        return id + "_" + spuId;
    }
}
