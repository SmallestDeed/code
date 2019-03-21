package com.sandu.api.company.output;

import com.sandu.api.brand.output.FranchiserBrandVO;
import com.sandu.api.category.output.CategoryListVO;
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
public class FranchiserDetailsVO implements Serializable{

    @ApiModelProperty(value = "经销商企业id")
    private Integer id;

    @ApiModelProperty(value = "经销商企业编码")
    private String  companycCode;

    @ApiModelProperty(value = "经销商企业名称")
    private String  companyName;

    @ApiModelProperty(value = "经销商企业可见产品范围id")
    private String  productVisibilityRange;

    @ApiModelProperty(value = "经销商企业可见产品范围集合")
    private List<CategoryListVO>  productVisibilityRangeNameList;

    @ApiModelProperty(value = "经销商企业可见产品范围父级集合")
    private List<CategoryListVO>  productVisibilityRangeNameListP;

    @ApiModelProperty(value = "经销商企业省编码")
    private String provinceCode;

    @ApiModelProperty(value = "经销商企业市编码")
    private String cityCode;

    @ApiModelProperty(value = "经销商企业区编码")
    private String areaCode;

    @ApiModelProperty(value = "经销商企业街道编码")
    private String streetCode;

    @ApiModelProperty(value = "经销商企业省名称")
    private String provinceCodeName;

    @ApiModelProperty(value = "经销商企业市名称")
    private String cityCodeName;

    @ApiModelProperty(value = "经销商企业区名称")
    private String areaCodeName;

    @ApiModelProperty(value = "经销商企业街道名称")
    private String streetCodeName;

    @ApiModelProperty(value = "经销商企业地址")
    private String  companyAddress;

    @ApiModelProperty(value = "经销商企业介绍")
    private String  companyDesc;

    @ApiModelProperty(value = "经销商企业联系人电话")
    private String phone;

    @ApiModelProperty(value = "经销商企业联系人姓名")
    private String  contactName;

    @ApiModelProperty(value = "经销商企业经营范围")
    private String  businessScope;

    @ApiModelProperty(value = "经销商企业品牌ids")
    private String brandIds;

    @ApiModelProperty(value = "经销商品牌集合")
    private List<FranchiserBrandVO> brandList;

    //add by wanghl start
    @ApiModelProperty(value = "经销商所属行业(以逗号分隔)")
    private String companyIndustrys;

    @ApiModelProperty(value = "经销商所属行业名称(以逗号分隔)")
    private String companyIndustryNames;
    //add by wanghl end

   @ApiModelProperty(value = "企业类型")
   private Integer businessType;

}
