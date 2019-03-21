package com.nork.design.model;

import com.alipay.api.domain.Data;

import java.util.Date;

public class CompanyDesignPlanIncomeAggregated {

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

    public Double getWithdrawDubi() {
        return withdrawDubi;
    }

    public void setWithdrawDubi(Double withdrawDubi) {
        this.withdrawDubi = withdrawDubi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getCurrentDubi() {
        return currentDubi;
    }

    public void setCurrentDubi(Double currentDubi) {
        this.currentDubi = currentDubi;
    }

    public Double getTotalIncomeDubi() {
        return totalIncomeDubi;
    }

    public void setTotalIncomeDubi(Double totalIncomeDubi) {
        this.totalIncomeDubi = totalIncomeDubi;
    }

    public Double getTransferDubi() {
        return transferDubi;
    }

    public void setTransferDubi(Double transferDubi) {
        this.transferDubi = transferDubi;
    }

    public Double getFrozenDubi() {
        return frozenDubi;
    }

    public void setFrozenDubi(Double frozenDubi) {
        this.frozenDubi = frozenDubi;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
