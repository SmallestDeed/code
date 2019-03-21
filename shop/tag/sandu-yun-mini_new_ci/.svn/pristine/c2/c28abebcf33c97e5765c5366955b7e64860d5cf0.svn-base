package com.sandu.company.model.query;

import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通过店铺风格查询店铺列表
 * @author WangHaiLin
 * @date 2018/9/3  20:30
 */
@Data
public class ShopListQuery extends BaseQuery<ShopListQuery> implements Serializable {

    @ApiModelProperty(value = "企业Id",required = true)
    private Long companyId;

    @ApiModelProperty(value = "风格Id",required = true)
    private Long styleId;

}
