package com.sandu.api.merchantManagePay.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class FullHouseDesignBo implements Serializable{

    private Integer planId;

    private Double salePrice;

    private Integer salePriceChargeType;

    private Integer companyId;

    private String planCode;

    private Integer userId;
}
