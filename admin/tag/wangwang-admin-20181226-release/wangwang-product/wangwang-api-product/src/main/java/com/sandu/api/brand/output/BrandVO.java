package com.sandu.api.brand.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 */
@Data
public class BrandVO implements Serializable{

    private static final long serialVersionUID = -2780020217751499763L;
    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "品牌ID")
    private Integer id;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    @ApiModelProperty(value = "公司名称")
    private Integer companyName;

    @ApiModelProperty(value = "品牌logo")
    private String brandLogo;

    @ApiModelProperty(value = "品牌介绍")
    private String brandDesc;

    @ApiModelProperty(value = "品牌编码")
    private String brandCode;

    @ApiModelProperty(value = "品牌类型")
    private Integer brandStyleId;

    @ApiModelProperty(value = "品牌英文简称")
    private String brandReferred;

}
