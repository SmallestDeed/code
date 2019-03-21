package com.sandu.api.solution.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DesignPlanProductDisplayPO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanProductDisplayPO implements Serializable{

    @ApiModelProperty(value = "产品id",required = true)
    @NotNull(message = "产品id不能为空")
    @Min(value = 1,message = "产品id不合法")
    private Integer productId;

    @ApiModelProperty(value = "方案id",required = true)
    @NotNull(message = "方案id不能为空")
    @Min(value = 1,message = "方案id不合法")
    private Integer planId;

    @NotNull(message = "状态  0 显示  1 不显示")
    @Range(min = 0,max = 1,message = "状态  0 显示  1 不显示")
    @ApiModelProperty(value = "状态  0 显示  1 不显示",required = true)
    private Integer status;


}
