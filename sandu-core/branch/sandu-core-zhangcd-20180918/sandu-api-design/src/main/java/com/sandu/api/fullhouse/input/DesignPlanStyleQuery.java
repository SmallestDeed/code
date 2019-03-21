package com.sandu.api.fullhouse.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 空间方案风格查询参数模型
 */
@Data
public class DesignPlanStyleQuery implements Serializable {
    /**
     * 空间类型(1:客厅,2:餐厅,3:客餐厅,4:卧室,5:厨房,6:卫生间,7:书房,8:衣帽间)
     */
    @ApiModelProperty("空间类型(0:全屋,1:客厅,2:餐厅,3:客餐厅,4:卧室,5:厨房,6:卫生间,7:书房,8:衣帽间)")
    @NotNull(message = "空间类型为空")
    private Integer spaceType;
}
