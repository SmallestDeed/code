package com.sandu.api.goods.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class GoodsListQuery implements Serializable
{
    @ApiModelProperty(value = "商品类别code")
    private String typeCode;

    @ApiModelProperty(value = "商品小类code")
    private String childTypeCode;

    @ApiModelProperty(value = "上架情况：1表示已上架，0表示未上架")
    private Integer putaway;

    @ApiModelProperty(value = "是否预售：1表示预售商品，0表示非预售商品")
    private Integer presell;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品编号")
    private String code;

    @ApiModelProperty(value = "企业ID", required = true)
    @Min(value = 1, message = "公司ID必须为大于0的整数")
    private Integer companyId;

    @ApiModelProperty(value = "当前页数")
    @Min(value = 1, message = "当前页数最小为1")
    private Integer page;

    @ApiModelProperty(value = "每页显示商品数")
    @Min(value = 1, message = "每页商品数必须大于1，默认20")
    private Integer limit = 20;

    @ApiModelProperty(value = "产品型号")
    private String productModelNumber;
}
