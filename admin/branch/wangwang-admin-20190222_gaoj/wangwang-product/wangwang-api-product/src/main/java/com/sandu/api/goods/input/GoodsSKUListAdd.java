package com.sandu.api.goods.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class GoodsSKUListAdd implements Serializable
{
    @ApiModelProperty(value = "sku集合")
    @NotEmpty(message = "传入的SKU为空")
    private List<GoodsSKUAdd> goodsSKUAddList;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID为空")
    @Min(value = 1, message = "用户ID错误")
    private Integer userId;

    @ApiModelProperty(value = "SpuId")
    @NotNull(message = "SpuId为空")
    @Min(value = 1, message = "SpuId错误")
    private Integer spuId;

    @ApiModelProperty("企业小程序的appId")
    private String appId;

    @ApiModelProperty("企业ID")
    private Integer companyId;
}
