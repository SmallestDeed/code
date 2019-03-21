package com.sandu.api.solution.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CompanyDesignPlanIncomeVO implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 方案编码
     */
    @ApiModelProperty("方案编码")
    private String planCode;

    /**
     * 购买用户id
     */
    @ApiModelProperty("购买人id")
    private Long buyId;

    /**
     * 企业id
     */
    @ApiModelProperty("企业id")
    private Long companyId;

    /**
     * 方案使用类型
     */
    @ApiModelProperty("方案使用类型")
    private Integer useType;

    /**
     * 方案购买时间
     */
    @ApiModelProperty("方案购买时间")
    private Date useTime;

    /**
     * 方案价格
     */
    @ApiModelProperty("方案价格")
    private Double planPrice;

    /**
     * 方案可提现价格
     */
    @ApiModelProperty("方案可提现价格")
    private Double withdrawAmount;

    /**
     * 方案提现状态
     */
    @ApiModelProperty("方案提现状态:0.未提取,1.已提取")
    private Integer withdrawStatus;

    /**
     * 提现操作人
     */
    @ApiModelProperty("提现操作人")
    @JsonIgnore
    private Long withdrawUser;

    /**
     * 提现时间
     */
    @ApiModelProperty("提现时间")
    private Date withdrawTime;

    /**
     * 购买用户电话
     */
    @ApiModelProperty("购买用户电话")
    @JsonIgnore
    private String BuyUserMobile;

    /**
     *提现操作人电话
     */
    @ApiModelProperty("提现操作人电话")
    private String withdrawUserMobile;

    @ApiModelProperty("方案使用方式")
    private String designPlanUseType;
}
