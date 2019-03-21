package com.sandu.goods.model.BO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GoodsDetailBO implements Serializable
{
    private Integer id;

    private String picIds;

    private Integer mainPicId;

    private String spuName;

    private String prices;

    private String salePrices;

    private String productDesc;

    private Integer deliveryPresell;

    private Integer deliveryDay;

    private Date deliveryDate;

    private Integer limitation;

    private BigDecimal transportPay;
}
