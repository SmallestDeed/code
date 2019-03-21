package com.sandu.api.company.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@ToString
public class BaseFranchiserNew implements Serializable {
    Integer id;

    @NotNull(message = "厂商ID不能为空")
    @ApiModelProperty("厂商ID")
    Integer pid;

    @NotEmpty(message = "经销商名称不能为空")
    @Length(max = 50, message = "经销商名称最多{max}个字符")
    @ApiModelProperty("经销商名称")
    String companyName;

    @NotEmpty(message = "所属行业不能为空")
    @ApiModelProperty("所属行业ID，用,分割")
    String companyIndustrys;

    @NotEmpty(message = "企业可见产品范围不能为空")
    @ApiModelProperty("企业可见产品范围ID，用,分割")
    String productVisibilityRange;

    @ApiModelProperty("省")
    String provinceCode;

    @ApiModelProperty("市")
    String cityCode;

    @ApiModelProperty("区")
    String areaCode;

    @ApiModelProperty("街道")
    String streetCode;

    @ApiModelProperty("企业详情地址")
    String companyAddress;

    @ApiModelProperty("企业可开通PC端数量")
    Integer pcCount;

    @ApiModelProperty("企业可开通移动端数量")
    Integer mobileCount;

    @NotEmpty(message = "所属品牌不能为空")
    @ApiModelProperty("所属品牌ID，用,分割")
    String brandIds;

    @ApiModelProperty("联系人姓名")
    String contactName;

    @ApiModelProperty("联系人电话")
    String phone;

    @Length(max = 50, message = "经营范围最多{max}个字符")
    @ApiModelProperty("经营范围")
    String businessScope;

    @ApiModelProperty("邮箱")
    String email;

    @ApiModelProperty("方案是否审核：0、需要；1、不需要")
    Integer isExamine;

    @ApiModelProperty("企业介绍")
    String companyDesc;

    @ApiModelProperty("备注")
    String remark;
}
