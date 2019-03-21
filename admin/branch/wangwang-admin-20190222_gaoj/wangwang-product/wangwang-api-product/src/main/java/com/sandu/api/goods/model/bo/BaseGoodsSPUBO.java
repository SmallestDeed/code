package com.sandu.api.goods.model.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BaseGoodsSPUBO
{
    private Integer id;

    private String spuCode;

    private Integer companyId;

    private String productName;

    private String productModelNumber;

    private String smallTypeMark;

    private String productDesc;

    private String spuName;

    private Integer purhaseLimitation;

    private Integer transportId;

    private BigDecimal fixTransportExpense;

    private Integer integral;

    private Integer isPresell;

    private Date presellDeadline;

    private Date balancePaymentStarttime;

    private Date balancePaymentEndtime;

    private BigDecimal deposit;

    private Integer deliveryTime;

    private Integer totalInventory;

    private String spuIntroduce;
}
