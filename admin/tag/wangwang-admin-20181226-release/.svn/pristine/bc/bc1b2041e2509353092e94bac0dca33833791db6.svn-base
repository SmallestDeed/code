package com.sandu.api.solution.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DesignPlanConfig implements Serializable {
    @ApiModelProperty("方案ID")
    @Min(value = 1,message = "方案ID错误")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty("方案设计描述")
    private String content;
}
