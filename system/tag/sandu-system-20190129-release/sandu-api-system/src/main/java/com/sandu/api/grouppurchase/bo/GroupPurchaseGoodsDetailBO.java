package com.sandu.api.grouppurchase.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class GroupPurchaseGoodsDetailBO implements Serializable {

    private Integer id;
    private String picIds;
    private Integer mainPicId;
    private String spuName;
    private String price;
    private String salePrice;
    private String activityPrice;
    private String productDesc;
    private Integer deliveryPresell;
    private Integer deliveryDay;
    private Date deliveryDate;
    private Integer limitation;
    private BigDecimal transportPay;
    private Integer totalInventory;
}
