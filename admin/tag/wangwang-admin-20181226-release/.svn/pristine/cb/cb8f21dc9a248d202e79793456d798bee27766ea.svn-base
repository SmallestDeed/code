package com.sandu.api.product.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductAllotUpdate implements Serializable {
    @ApiModelProperty(value = "产品id集合", required = true)
    @NotEmpty(message = "产品id不能为空")
    @Size(min = 1, message = "产品ID最少为一个")
    private List<Integer> productIds;

    @ApiModelProperty(value = "平台类型:渠道:2b,线上:2c; 可同时勾选,以逗号分隔", required = true)
    @Pattern(regexp = "^([2][bc])?(,[2][bc])?$", message = "平台类型有错误")
    private String platformType;
}