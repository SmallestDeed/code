package com.sandu.api.product.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPropBO implements Serializable {
    @ApiModelProperty(value = "属性ID")
    @Min(value = 1,message = "请输入正确的ID")
    private Integer id;
    @ApiModelProperty(value = "属性名称")
    @Length(max = 20,message = "请输入{max}个长度以内个字符")
    private String name;
    @ApiModelProperty(value = "属性编码")
    @Length(max = 20,message = "请输入{max}个长度以内个字符")
    private String code;
    @ApiModelProperty(value = "父级属性")
    private ProductPropBO parent;
}
