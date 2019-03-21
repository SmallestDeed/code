package com.sandu.api.company.model.bo;

import com.sandu.api.company.input.SysDictionaryInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 * @ClassName InnerCompanyBO
 * @date 2018/11/6
 */
@Data
public class InnerCompanyBO implements Serializable {

	private Integer id;

	@ApiModelProperty("公司名称")
	private String companyName;

	@ApiModelProperty("公司联系人手机")
	private String phone;

	@ApiModelProperty("公司名称")
	private String companyCode;

	@ApiModelProperty("街道编码")
	private String streetCode;

	@ApiModelProperty("区编码")
	private String areaCode;

	@ApiModelProperty("市编码")
	private String cityCode;

	@ApiModelProperty("省编码")
	private String provinceCode;

	private String longAreaCode;

	@ApiModelProperty("经营范围分类ID")
	private List<Integer> categoryList;

	private String productVisibilityRange;

	@ApiModelProperty("企业详细地址")
	private String companyAddress;

	private List<SysDictionaryInfo> industryInfo;


	private Integer businessType;

	@ApiModelProperty("企业类型 ")
	private String companyType;

}
