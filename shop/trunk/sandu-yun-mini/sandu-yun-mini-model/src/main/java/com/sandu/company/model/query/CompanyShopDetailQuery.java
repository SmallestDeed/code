package com.sandu.company.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author WangHaiLin
 * @date 2018/11/7  19:37
 */
@Data
public class CompanyShopDetailQuery implements Serializable {

    @ApiModelProperty(value = "店铺ID")
    @NotNull(message = "店铺Id不能为空")
    private Long shopId;

    @ApiModelProperty(value = "当前平台类型（1:小程序2:选装网3:同城联盟）")
    @NotNull(message = "当前平台类型不能为空")
    private Integer platformValue;
}
