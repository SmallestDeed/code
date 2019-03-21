package com.sandu.api.solution.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DesignPlanDefaultDiagramPO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanDefaultDiagramPO implements Serializable{

    @NotNull(message = "方案id不能为空")
    @Min(value = 1,message = "方案不合法")
    @ApiModelProperty("方案id")
    private Integer planId;


}
