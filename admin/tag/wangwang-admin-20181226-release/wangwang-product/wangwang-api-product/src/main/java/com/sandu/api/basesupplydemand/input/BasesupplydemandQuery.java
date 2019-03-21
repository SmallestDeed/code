package com.sandu.api.basesupplydemand.input;

import com.sandu.common.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-20 10:46
 */
@Data
public class BasesupplydemandQuery extends BaseQuery implements Serializable {

    @ApiModelProperty(value = "信息分类")

    private String supplyDemandCategoryId;


    @ApiModelProperty(value="查询参数")
    private String queryParam;
}
