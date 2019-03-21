package com.sandu.api.product.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductPutUpdate implements Serializable {
    @ApiModelProperty(value = "产品id集合")
    @Size(min = 1, message = "产品ID最少为一个")
    private List<Integer> productIds;

    @ApiModelProperty(value = "渠道类型:渠道上下架:2b,线上上下架:2c", required = true)
    @Pattern(regexp = "^(2b|2c)$", message = "渠道信息不能为空")
    @NotEmpty(message = "上架操作的平台类型不能为空")
    private String platformType;

    @ApiModelProperty(value = "线上产品上下架操作时，需上架的平台ID,多选以逗号分隔,未传的平台ID视为下架操作")
    @Pattern(regexp = "^([1-9]\\d{0,11})?(,[1-9]\\d{0,11})*$", message = "请输入有效的ID集合")
    private String platformIds;

    @ApiModelProperty(value = "渠道产品上下架操作时，上下架状态:1上架/0下架")
    @Range(max = 1, min = 0, message = "请输入正确的上下架状态")
    private Integer putStatus;
}