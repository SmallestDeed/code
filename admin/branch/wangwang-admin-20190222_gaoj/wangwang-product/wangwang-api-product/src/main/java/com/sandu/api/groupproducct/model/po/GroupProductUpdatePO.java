package com.sandu.api.groupproducct.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
 * @datetime 2018/3/28 17:31
 */
@Data
public class GroupProductUpdatePO implements Serializable {
    @NotNull(message = "产品ID不能为空")
    private Integer id;

    @ApiModelProperty("组合名称")
    private String name;

    @ApiModelProperty("组合型号")
    private String groupNumber;

    @ApiModelProperty("组合描述")
    private String desc;

    @ApiModelProperty("使用房间id")
    @Min(value = 1, message = "使用房间输入有误")
    private Integer houseValue;

    @ApiModelProperty("使用区域ID")
    @Min(value = 1, message = "区域ID输入有误")
    private Integer areaValue;

    @ApiModelProperty("组合类型ID")
    @Min(value = 1, message = "组合类型ID输入有误")
    private Integer groupValue;

    @ApiModelProperty("品牌ID")
    @Min(value = 1, message = "品牌ID输入有误")
    private Integer brandId;

    @ApiModelProperty("价格")
    private double price;

    @ApiModelProperty("建议售价")
    private double advicePrice;

    @ApiModelProperty("编辑组合所在的平台,channel/online")
    @Pattern(regexp = "^(channel|online)$", message = "渠道信息不能为空")
    private String platformType;

    @ApiModelProperty("缩略图")
    @Min(value = 1, message = "区域ID输入有误")
    private Integer picId;

    @ApiModelProperty("图片集合")
    private String picIds;

    @ApiModelProperty("主产品ID")
    @Min(value = 1, message = "主产品ID输入有误")
    private Integer mainProductId;

    @ApiModelProperty("风格ID")
    @Min(value = 1, message = "风格ID输入有误")
    private Integer styleId;

    @ApiModelProperty("用户ID")
    @Range(min = 1, message = "用户ID有误")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
}
