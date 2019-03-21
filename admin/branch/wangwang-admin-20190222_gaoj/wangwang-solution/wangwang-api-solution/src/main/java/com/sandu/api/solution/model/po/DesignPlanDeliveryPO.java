package com.sandu.api.solution.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DesignPlanDeliveryPO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanDeliveryPO implements Serializable {

    /**
     * 设计方案
     */
    @NotNull(message = "方案id不能为空")
    @Min(value = 1, message = "方案id不合法")
    @ApiModelProperty("交付方案")
    private Integer designPlanId;

    /**
     * 交付人ID
     */
    @NotNull(message = "交付人不能为空")
    @Min(value = 1, message = "交付人id不合法")
    @ApiModelProperty("交付人")
    private Integer delivererId;

    /**
     * 交付品牌id集合
     */
    @ApiModelProperty("交付品牌id集")
    private List<Integer> receiveBrandIds;

    /**
     * 交付公司ID
     */
    @NotNull(message = "交付公司不能为空")
    @Min(value = 1, message = "交付公司id不合法")
    @ApiModelProperty("交付公司")
    private Integer deliveryCompanyId;

    /**
     * 接收公司ID
     */
    @ApiModelProperty("接收公司")
    private List<Integer> receiveCompanyIds;

}
