package com.sandu.designplan.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanDecoratePriceBO implements Serializable {

    //具体装修报价
    private Integer decoratePrice;
    //装修报价类型名称
    private String decorateTypeName;

}
