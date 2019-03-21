package com.sandu.api.company.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.api.category.output.CategoryListVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Sandu
 * @ClassName InnerCompanyDetails
 * @date 2018/11/6
 */
@Data
public class InnerCompanyDetails implements Serializable {

	@ApiModelProperty(value = "企业名称")
	private String companyName;

	@ApiModelProperty(value = "企业code", hidden = true)
	private String companyCode;

	@ApiModelProperty(value = "	  所属行业")
	private String companyIndustrys;

	private List<String> companyIndustryNames;

	@ApiModelProperty(value = "	  企业可见产品范围")
	private String productVisibilityRange;

	@ApiModelProperty(value = "	  省编码")
	private String provinceCode;
	private String provinceCodeName;

	@ApiModelProperty(value = "	  市编码")
	private String cityCode;
	private String cityCodeName;

	@ApiModelProperty(value = "	  区编码")
	private String areaCode;
	private String areaCodeName;

	@ApiModelProperty(value = "	  街道编码")
	private String streetCode;
	private String streetCodeName;

	@ApiModelProperty(value = "	  客服QQ")
	private String companyCustomerQq;

	@ApiModelProperty(value = "	  合同号")
	private String contractNumber;

	@ApiModelProperty(value = "	  合同生效时间 ")
	@JsonFormat(pattern = "yyyy-MM-dd:HH")
	private Date contractEffectiveTime;

	@ApiModelProperty(value = "	  企业网站")
	private String companyUrl;

	@ApiModelProperty(value = "	  企业LOGO")
	private Integer companyLogo;
	private String companyLogoUrl;

	@ApiModelProperty(value = "	  联系人姓名")
	private String contactName;

	@ApiModelProperty(value = "	  联系电话")
	private String phone;

	@ApiModelProperty(value = "	  经营范围")
	private String businessScope;

	@ApiModelProperty(value = "	  企业标识")
	private String companyIdentify;


	@ApiModelProperty(value = "	  企业地址")
	private String companyAddress;


	@ApiModelProperty(value = "	  公司品牌网站域名")
	private String companyDomainName;


	@ApiModelProperty(value = "	  方案提成率:0-1之间的小数，小数位后最多5位")
	private Double withdrawRate;

	@ApiModelProperty(value = "	  是否开通小程序管理 ")
	private Integer isManage;

	@ApiModelProperty(value = "	  方案是否审核")
	private Integer isExamine;


	@ApiModelProperty(value = "	  企业介绍")
	private String companyDesc;

	@ApiModelProperty(value = "	  备注")
	private String remark;

	@ApiModelProperty(value = "公司类型:1：厂商、2：经销商、3：门店、4：设计公司、5：装修公司")
	private Integer businessType;

	private List<CategoryListVO> categoryListVOList;
}
