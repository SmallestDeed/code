package com.sandu.product.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BaseProductDetailsVo implements Serializable {

    @ApiModelProperty(value = "产品ID", dataType = "Integer")
    private Integer productId;
    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String productName;
    @ApiModelProperty(value = "产品品牌", dataType = "String")
    private String brandName;
    @ApiModelProperty(value = "产品型号", dataType = "String")
    private String productModelNumber;
    @ApiModelProperty(value = "产品规格", dataType = "String")
    private String productSpec;
    @ApiModelProperty(value = "产品价格", dataType = "String")
    private BigDecimal salePrice;
    @ApiModelProperty(value = "产品描述", dataType = "String")
    private String description;
    @ApiModelProperty(value = "产品图片IDS", dataType = "String")
    private String picIds;
    @ApiModelProperty(value = "产品图片路径", dataType = "List")
    private List<String> picList;

}
