package com.sandu.api.company.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 企业信息 出参
 * @Date 2018/6/2 0002 14:12
 * @Modified By
 */
@Data
public class BaseCompanyDetailsVO implements Serializable{

    @ApiModelProperty(value = "企业id")
    private Integer id;

    @ApiModelProperty(value = "企业编码")
    private String  companycCode;

    @ApiModelProperty(value = "企业名称")
    private String  companyName;

    @ApiModelProperty(value = "企业可见产品范围id")
    private String  productVisibilityRange;

    @ApiModelProperty(value = "企业可见产品范围名称")
    private String  productVisibilityRangeName;

    @ApiModelProperty(value = "企业省编码")
    private String provinceCode;

    @ApiModelProperty(value = "企业市编码")
    private String cityCode;

    @ApiModelProperty(value = "企业区编码")
    private String areaCode;

    @ApiModelProperty(value = "企业街道编码")
    private String streetCode;

    @ApiModelProperty(value = "企业省名称")
    private String provinceCodeName;

    @ApiModelProperty(value = "企业市名称")
    private String cityCodeName;

    @ApiModelProperty(value = "企业区名称")
    private String areaCodeName;

    @ApiModelProperty(value = "企业街道名称")
    private String streetCodeName;

    @ApiModelProperty(value = "企业地址")
    private String  companyAddress;

    @ApiModelProperty(value = "企业logo")
    private Integer companyLogo;

    @ApiModelProperty(value = "企业logo路径")
    private String companyLogoPath;

    @ApiModelProperty(value = "企业介绍")
    private String  companyDesc;

    @ApiModelProperty(value = "企业联系人电话")
    private String phone;

    @ApiModelProperty(value = "企业联系人姓名")
    private String  contactName;

    @ApiModelProperty(value = "企业网站")
    private String  companyUrl;

    @ApiModelProperty(value = "企业经营范围")
    private String  businessScope;

    @ApiModelProperty(value = "企业品牌")
    private String brandName;

    @ApiModelProperty(value = "企业类型")
    private String businessType;

    //add by wanghl start
    @ApiModelProperty(value = "企业所属行业(以逗号分隔)")
    private String companyIndustrys;

    @ApiModelProperty(value = "企业所属行业名称(以逗号分隔)")
    private String companyIndustryNames;
    //add by wanghl end
}
