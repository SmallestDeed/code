package com.sandu.api.shop.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改发布状态
 * @author WangHaiLin
 * @date 2018/8/10  16:59
 */
@Data
public class CompanyShopArticleReleaseUpdate implements Serializable {

    @ApiModelProperty(value = "博文ID", required = true)
    @NotNull(message = "博文Id不能为空")
    private Long articleId;

    @ApiModelProperty(value = "发布状态", required = true)
    @NotNull(message = "发布状态不能为空")
    private Integer releaseStatus;

    @ApiModelProperty(value = "店铺类型")
    private Integer shopType;
}
