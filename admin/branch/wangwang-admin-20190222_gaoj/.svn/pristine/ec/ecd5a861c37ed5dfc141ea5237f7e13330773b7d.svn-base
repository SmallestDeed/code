package com.sandu.api.goods.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsSKUAdd implements Serializable
{
    @ApiModelProperty(value = "产品ID", required = true)
    @Min(value = 1, message = "ID必须大于0")
    private Integer productId;

    @ApiModelProperty(value = "库存")
    @Min(value = 0, message = "库存必须大于等于0")
    private Integer repertory;

    @ApiModelProperty(value = "优惠价")
    @Min(value = 0, message = "优惠价必须大于等于0")
    private BigDecimal cost;

    @ApiModelProperty(value = "规格图片")
    private Integer spePictureId;

    @ApiModelProperty(value = "属性值ID")
    @NotBlank(message = "属性ID为空！！！")
    private String attrValueIds;
}
