package com.sandu.api.solution.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 11:59 2018/5/16
 */

@Data
public class DesignPlanDeliveryBatchPO implements Serializable {

    /**
     * 设计方案集合
     */
    @NotNull(message = "方案id不能为空")
    @ApiModelProperty("交付方案")
    private List<Integer> designPlanId;

    /**
     * 交付人ID
     */
    @NotNull(message = "交付人不能为空")
    @Min(value = 1, message = "交付人id不合法")
    @ApiModelProperty("交付人")
    private Integer delivererId;

    /**
     * 交付公司ID
     */
    @NotNull(message = "交付公司不能为空")
    @Min(value = 1, message = "交付公司id不合法")
    @ApiModelProperty("交付公司")
    private Integer deliveryCompanyId;


    /**
     * 交付品牌id集合
     */
    @ApiModelProperty("交付品牌id集")
    private List<Integer> receiveBrandIds;

    /**
     * 接收公司ID
     */
    @ApiModelProperty("接收公司")
    private List<Integer> receiveCompanyIds;
}
