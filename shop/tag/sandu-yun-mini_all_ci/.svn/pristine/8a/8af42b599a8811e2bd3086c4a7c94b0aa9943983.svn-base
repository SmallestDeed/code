package com.sandu.company.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WangHaiLin
 * @date 2018/8/15  10:58
 */
@Data
public class ShopArticleQuery implements Serializable {

    @ApiModelProperty(value = "店铺ID",required = true)
    private Long shopId;

    @ApiModelProperty(value = "每页数量")
    private Integer limit;

    @ApiModelProperty(value = "当前页")
    private Integer page;

    @ApiModelProperty(value = "开始位置")
    private Integer start;
}
