package com.sandu.api.company.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/6/4 0004 10:51
 * @Modified By
 */
@Data
public class AdminFranchiserDetailsVO implements Serializable {

    @ApiModelProperty(value = "经销商企业id")
    private Integer id;

    @ApiModelProperty(value = "经销商企业编码")
    private String companyCode;

    @ApiModelProperty(value = "经销商企业名称")
    private String companyName;

    @ApiModelProperty(value = "经销商所属行业ID")
    @JsonProperty("companyIndustrys")
    private List<Integer> industryIds;

    @ApiModelProperty(value = "经销商所属行业名称(以逗号分隔)")
    private String industryNames;

    @ApiModelProperty(value = "经销商企业可见产品范围ID")
    @JsonProperty("productVisibilityRange")
    private List<Integer> productVisibilityRangeIds;

    @ApiModelProperty(value = "经销商企业可见产品范围")
    private String productVisibilityRangeNames;

    @ApiModelProperty("省份")
    private String provinceCode;

    @ApiModelProperty("市")
    private String cityCode;

    @ApiModelProperty("区")
    private String areaCode;

    @ApiModelProperty("街道")
    private String streetCode;

    @ApiModelProperty(value = "区域地址")
    private String companyArea;

    @ApiModelProperty(value = "经销商企业地址")
    private String companyAddress;

    @ApiModelProperty(value = "企业可开通PC端数量")
    private Integer pcCount;

    @ApiModelProperty(value = "企业可开通PC端数量")
    private Integer mobileCount;

    @ApiModelProperty(value = "经销商企业品牌ID")
    @JsonProperty("brandIds")
    private List<Integer> brandIds;

    @ApiModelProperty(value = "经销商企业品牌")
    private String brandNames;

    @ApiModelProperty(value = "经销商企业联系人电话")
    private String phone;

    @ApiModelProperty(value = "经销商企业联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "经销商企业经营范围")
    private String businessScope;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "企业类型")
    private Integer businessType;

    @ApiModelProperty(value = "经销商企业介绍")
    private String companyDesc;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "方案是否审核：0、需要；1、不需要")
    private Integer isExamine;
}
