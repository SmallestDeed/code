package com.sandu.product.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺
 * Created by chenm on 2018/11/22.
 */
@Data
public class CompanyShopVo implements Serializable{
    @ApiModelProperty(value="店铺id" , dataType = "int")
    private Integer id;
    @ApiModelProperty(value="店铺名称" , dataType = "String")
    private String shopName;
    @ApiModelProperty(value="店铺联系电话", dataType = "String")
    private String contactPhone;
    @ApiModelProperty(value="店铺地址", dataType = "String")
    private String shopAddress;
}
