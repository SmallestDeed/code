package com.sandu.api.product.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductManagerUpdate implements Serializable {
    @ApiModelProperty(value = "产品id", required = true)
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "请输入正确的数值")
    private Long id;

    @ApiModelProperty(value = "产品缩略图ID")
    private String defaultPicId = "0";

    @ApiModelProperty(value = "产品所有图片ID,以逗号分隔")
    @Pattern(regexp = "^[1-9]\\d{0,11}(,[1-9]\\d{0,11})*$", message = "请输入有效的图片集合")
    private String picIds;

    @ApiModelProperty(value = "建议售价")
    @Min(value = 0, message = "请输入正确的数值")
    @Max(value = 1000000, message = "数值超出限制,1000000")
    private Double advicePrice;

    @ApiModelProperty(value = "售价")
    @Min(value = 0, message = "请输入正确的数值")
    @Max(value = 1000000, message = "数值超出限制,1000000")
    private Double price;

    @ApiModelProperty(value = "产品风格")
    private List<Integer> baseStyleId;

    @ApiModelProperty(value = "产品描述")
    @Length(max = 10000, message = "描述长度超过长度{max}")
    private String desc;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer userId;
}
