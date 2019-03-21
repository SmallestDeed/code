package com.sandu.api.merchantManagePay.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DesignPlanBO implements Serializable {

    private Long planId;

    private Double salePrice;

    private Integer salePriceChargeType;

    private Long companyId;

    private String planCode;

    private Integer userId;
}
