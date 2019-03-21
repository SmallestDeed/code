package com.sandu.api.income.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyDesignPlanIncomeAggregated implements Serializable{

    private Long id;

    //公司id
    private Long companyId;

    //企业未提现收益
    private Double currentDubi;

    //企业总收益
    private Double totalIncomeDubi;

    //转出金额
    private Double transferDubi;

    //冻结金额
    private Double frozenDubi;

    private Integer isDeleted;

    private Double withdrawDubi;
}
