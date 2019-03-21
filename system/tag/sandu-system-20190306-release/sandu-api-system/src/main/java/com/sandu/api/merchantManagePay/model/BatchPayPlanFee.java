package com.sandu.api.merchantManagePay.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BatchPayPlanFee implements Serializable{

    //支付方案ids
    @NotNull
    private String planIds;

    //方案类型:0.全屋,1.普通
    @NotNull
    private Integer planType;

    //购买类型:0.方案售卖,1.版权方案
    @NotNull
    private Integer buyType;

    private Long userId;

    private String platformCode;

    //5.方案售卖
    private Integer useType;
}
