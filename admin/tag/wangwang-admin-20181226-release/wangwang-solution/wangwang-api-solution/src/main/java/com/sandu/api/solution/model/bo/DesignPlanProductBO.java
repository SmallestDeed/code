package com.sandu.api.solution.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bvvy
 */
@Data
public class DesignPlanProductBO implements Serializable {

    @ApiModelProperty("产品")
    private Integer productId;

    @ApiModelProperty("显示状态")
    private Integer displayStatus;

    @ApiModelProperty("方案")
    private String planId;

    @ApiModelProperty("使用次数")
    private Integer usedCount;
}
