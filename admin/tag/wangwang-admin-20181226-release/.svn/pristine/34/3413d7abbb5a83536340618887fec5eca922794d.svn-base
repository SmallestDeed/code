package com.sandu.api.solution.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DecoratePriceInfo implements Serializable {
    @ApiModelProperty("报价价格")
    @NotNull(message = "报价价格不能为空")
    private Integer decoratePrice;

    @ApiModelProperty("报价类型")
    @NotNull(message = "报价类型不能为空")
    private Integer decoratePriceTypeValue;

    @ApiModelProperty("报价类型名称")
    @NotNull(message = "报价类型名称不能为空")
    private String decoratePriceTypeName;

    @ApiModelProperty(hidden = true)
    private Integer decoratePriceRangeValue;

}
