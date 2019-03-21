package com.sandu.api.groupproducct.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu
 * @datetime 2018/3/27 10:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupProductManagerUpdate implements Serializable{
    @NotNull(message = "产品ID不能为空")
    private Integer id;
    @ApiModelProperty("组合描述")
    private String desc;
    @ApiModelProperty("价格")
    private double price;
    @ApiModelProperty("建议售价")
    private double advicePrice;
    @ApiModelProperty("编辑组合所在的平台,channel/online")
    @Pattern(regexp = "^(channel|online)$", message = "渠道信息不能为空")
    private String platformType;
    @ApiModelProperty("缩略图")
    @Min(value = 1, message = "缩略图ID输入有误")
    private Integer picId;
    @ApiModelProperty("图片集合")
    private String picIds;

    @ApiModelProperty("用户ID")
    @Range(min = 1, message = "用户ID有误")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
}
