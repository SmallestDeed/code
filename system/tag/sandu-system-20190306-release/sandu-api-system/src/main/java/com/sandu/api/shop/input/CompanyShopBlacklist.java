package com.sandu.api.shop.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WangHaiLin
 * @date 2018/11/26  11:25
 */
@Data
public class CompanyShopBlacklist implements Serializable{

    @ApiModelProperty(value="店铺Id",required = true)
    private Integer shopId;

    @ApiModelProperty(value="是否拉黑",required = true)
    private Integer isBlacklist;

}
