package com.sandu.api.product.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductControlUpdate implements Serializable {

    @ApiModelProperty(value = "产品id集合", required = true)
    @NotNull(message = "产品id不能为空")
    @Size(min = 1, message = "产品ID最少为一个")
    private List<Integer> productIds;

    @ApiModelProperty(value = "产品公开状态:0非公开,1公开", required = true)
    @NotNull(message = "产品公开状态不能为空")
    @Range(max = 1, min = 0, message = "请输入正确的公开状态参数")
    private Integer productSecrecyStatus;
}
