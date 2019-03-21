package com.sandu.api.shop.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 店铺博文--列表查询入参
 * @author WangHaiLin
 * @date 2018/8/9  10:53
 */
@Data
public class CompanyShopArticleQuery implements Serializable {

    @ApiModelProperty(value = "店铺ID", required = true)
    @NotNull(message = "店铺Id不能为空")
    private Long shopId;

    @ApiModelProperty(value="每页数量:默认10")
    private Integer limit;

    @ApiModelProperty(value="当前页:默认第一页")
    private Integer page;

    @ApiModelProperty(value="开始位置")
    private Integer start;

    @ApiModelProperty(value = "店铺类型：1.企业店铺，2.个人店铺")
    private Integer shopType;
}
