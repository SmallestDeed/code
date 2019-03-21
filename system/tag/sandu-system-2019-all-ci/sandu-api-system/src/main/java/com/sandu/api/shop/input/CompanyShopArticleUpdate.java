package com.sandu.api.shop.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 店铺博文修改入参
 * @author WangHaiLin
 * @date 2018/8/9  10:25
 */
@Data
public class CompanyShopArticleUpdate implements Serializable{

    @ApiModelProperty(value = "博文ID", required = true)
    @NotNull(message = "博文Id不能为空")
    private Long articleId;

    @ApiModelProperty(value = "店铺ID", required = true)
    @NotNull(message = "店铺Id不能为空")
    private Long shopId;

    @ApiModelProperty(value = "博文标题", required = true)
    @NotBlank(message = "博文标题不能为空")
    @Size(min = 1, max = 30, message = "博文标题长度度限{min}-{max}个字符")
    private String title;

    @ApiModelProperty(value = "博文内容")
    private String content;

    @ApiModelProperty(value = "图片IDs")
    private String picIds;

    @ApiModelProperty(value = "店铺类型")
    private Integer shopType;
}
