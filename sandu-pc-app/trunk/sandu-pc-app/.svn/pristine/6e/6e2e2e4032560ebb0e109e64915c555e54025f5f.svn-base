package com.sandu.product.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺查询类
 * Created by chenm on 2018/11/23.
 */
@Data
public class CompanyShopSearch implements Serializable {
    private static final long serialVersionUID = -526015776952446143L;

    @ApiModelProperty(value = "起始行", dataType = "int")
    private Integer start = -1;
    @ApiModelProperty(value = "每页数据数", dataType = "int")
    private Integer limit = -1;
    @ApiModelProperty(value = "店铺名称", dataType = "string")
    private String shopName;
    @ApiModelProperty(value="是否被删除",hidden = true)
    private Integer isDeleted;
    @ApiModelProperty(value="排序字段",hidden = true)
    private String order;
    @ApiModelProperty(value="排序顺序(desc/asc)",hidden = true)
    private String orderNum;

}
