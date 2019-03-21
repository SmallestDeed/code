package com.sandu.api.company.input;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.commons.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 经销商企业 编辑 入参
 * @Date 2018/6/4 0004 10:20
 * @Modified By
 */
@Data
public class FranchiserUpdate implements Serializable {

    @ApiModelProperty(value = "经销商企业id",required = true)
    @NotNull(message = "经销商企业id不能为空")
    private Long id;

    @ApiModelProperty(value = "经销商企业名称", required = true)
    @NotBlank(message = "经销商企业名称不能为空")
    @Size(min = 1, max = 50, message = "经销商企业名称长度度限{min}-{max}个字符")
    private String companyName;

    @ApiModelProperty(value = "经销商企业可见产品范围id",required = true)
    @NotBlank(message = "企业可见产品范围id不能为空")
    private String productVisibilityRange;

    @ApiModelProperty(value = "经销商企业省编码")
    private String provinceCode;

    @ApiModelProperty(value = "经销商企业市编码")
    private String cityCode;

    @ApiModelProperty(value = "经销商企业区编码")
    private String areaCode;

    @ApiModelProperty(value = "经销商企业街道编码")
    private String streetCode;

    @ApiModelProperty(value = "经销商企业详细地址")
    @Size(min = 0, max = 100, message = "经销商企业地址长度度限{min}-{max}个字符")
    private String companyAddress;

    @ApiModelProperty(value = "经销商企业介绍")
    @Size(min = 0, max = 500, message = "经销商企业介绍长度度限{min}-{max}个字符")
    private String companyDesc;

    @ApiModelProperty(value = "经销商企业联系人电话")
    //@Pattern(regexp = "^\\b((0[0-9]{2,3}-)([1-9][0-9]{6,7}))\\b|\\b(((13[0-9])|(14[5|7])|(15([0-9]))|(17[0-3,5-8])|(18[0-9])|166|198|199)\\d{8}\\b)$",message = "号码格式错误")
    private String phone;

    @ApiModelProperty(value = "经销商企业联系人姓名")
    @Size(min = 0, max = 50, message = "联系人姓名长度度限{min}-{max}个字符")
    private String contactName;

    @ApiModelProperty(value = "经销商企业品牌")
    @NotBlank(message = "经销商品牌不能为空")
    private String brandIds;

    @ApiModelProperty(value = "经销商企业经营范围")
    @Size(min = 0, max = 50, message = "经销商企业经营范围长度度限{min}-{max}个字符")
    private String businessScope;

    @ApiModelProperty(value = "经销商企业所属行业")
    private String companyIndustrys;


    public BaseCompany getBaseCompany() {
        BaseCompany company = new BaseCompany();

        company.setId(id);
        company.setProductVisibilityRange(this.productVisibilityRange);
        company.setCompanyName(this.companyName);
        company.setProvinceCode(this.provinceCode);
        company.setCityCode(this.cityCode);
        company.setAreaCode(this.areaCode);
        company.setStreetCode(this.streetCode);
        company.setCompanyAddress(this.companyAddress);
        company.setCompanyDesc(this.companyDesc);
        company.setPhone(this.phone);
        company.setContactName(this.contactName);
        company.setBrandId(this.brandIds);
        company.setBusinessScope(this.businessScope);

        company.setCategoryIds(company.getProductVisibilityRange());
        if(StringUtils.isNotEmpty(this.provinceCode))
            company.setLongAreaCode(this.provinceCode);
        if(StringUtils.isNotEmpty(this.cityCode))
            company.setLongAreaCode(company.getLongAreaCode()+"."+this.cityCode);
        if(StringUtils.isNotEmpty(this.areaCode))
            company.setLongAreaCode(company.getLongAreaCode()+"."+this.areaCode);
        if(StringUtils.isNotEmpty(this.streetCode))
            company.setLongAreaCode(company.getLongAreaCode()+"."+this.streetCode);

        return company;
    }

}