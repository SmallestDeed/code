package com.sandu.api.product.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class EditorProductListVO implements Serializable {
    @ApiModelProperty(value = "产品id")
    private Integer id;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品模型编码")
    private String modelCode;

    @ApiModelProperty(value = "产品型号")
    private String modelNumber;

    @ApiModelProperty(value = "产品图片")
    private String picPath;

    @ApiModelProperty(value = "产品模型id")
    private Integer modelId;

    @ApiModelProperty(value = "模型分类")
    private String categoryNames;
}
