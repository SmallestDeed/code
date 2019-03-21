package com.sandu.api.product.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductListVO implements Serializable{

    @ApiModelProperty(value = "产品id")
    private Integer id;
    @ApiModelProperty(value = "产品编码")
    private String code;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品缩略图路径")
    private String picPath;
    @ApiModelProperty(value = "产品品牌名称")
    private String brandName;
    @ApiModelProperty(value = "产品分类")
    private String categoryNames;
    @ApiModelProperty(value = "产品模型编码")
    private String modelCode;
    @ApiModelProperty(value = "产品渠道分配状态，0：未分配、1：已分配")
    private Integer status2b;
    @ApiModelProperty(value = "渠道产品录入时间")
    private Date gmtCreate2b;
    @ApiModelProperty(value = "产品线上分配状态，0：未分配、1：已分配")
    private Integer status2c;
    @ApiModelProperty(value = "线上产品录入时间")
    private Date gmtCreate2c;
    @ApiModelProperty(value = "产品录入时间")
    private Date gtmCreat;
    @ApiModelProperty(value = "产品公开状态：0不公开，1公开")
    private Integer secrecy;
    @ApiModelProperty(value = "产品型号")
    private String modelNumber;
    @ApiModelProperty(value = "产品所属公司")
    private String companyName;
    @ApiModelProperty(value = "渠道产品上架状态:1上架/0未上架")
    private Integer putAwayStatus2b;
    @ApiModelProperty(value = "线上产品已上架平台名称")
    private List<String> putAwayPlatformNames;
    private List<Integer> putAwayPlatformIds;

    @ApiModelProperty(value = "产品类型(2:定制/1:软装)")
    private Integer productBatchType;
    @ApiModelProperty(value = "是否单独创建")
    private Integer isCreatedTexture;
}
