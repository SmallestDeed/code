package com.sandu.api.company.input;

import com.sandu.commons.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 经销商企业 列表 入参
 * @Date 2018/6/2 0002 17:36
 * @Modified By
 */
@Data
public class FranchiserListQuery extends Mapper implements Serializable{

    @ApiModelProperty(value = "企业id",required = true)
    @NotBlank(message = "企业id不能为空")
    private String companyId;

    @ApiModelProperty(value = "经销商企业名称")
    private String franchiserName;

    @ApiModelProperty(value = "企业联系人电话")
    private String phone;

    @ApiModelProperty(value = "企业联系人姓名")
    private String  contactName;

    @ApiModelProperty(value = "企业省编码")
    private String provinceCode;

    @ApiModelProperty(value = "企业市编码")
    private String cityCode;

    @ApiModelProperty(value = "企业区编码")
    private String areaCode;

    @ApiModelProperty(value = "企业街道编码")
    private String streetCode;

}
