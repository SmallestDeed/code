package com.sandu.api.company.input;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class CompanyUpdate implements Serializable {
    @ApiModelProperty(value = "企业ID",required = true)
    @NotNull(message = "企业ID不能为空")
    @Min(value = 1,message = "请输入有效的企业ID")
    private Long id;

    @ApiModelProperty(value = "品牌ID")
    @Min(value = 1,message = "请输入有效的品牌ID")
    private Long brandId;

    @ApiModelProperty(value = "品牌logo")
    private Integer brandLogo;

    @ApiModelProperty(value = "企业logo")
    private Integer logo;

    @ApiModelProperty(value = "企业二级域名")
    private String domain;

    @ApiModelProperty(value = "企业QQ")
    @Pattern(regexp = "^[1-9]\\d{4,12}$", message = "请传入有效QQ号码")
    private String qq;
}
