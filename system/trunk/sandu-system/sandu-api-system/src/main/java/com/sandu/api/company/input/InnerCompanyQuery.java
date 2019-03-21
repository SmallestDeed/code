package com.sandu.api.company.input;

import com.sandu.systemutil.BaseQueryModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 * @ClassName InnerCompanyQuery
 * @date 2018/11/6
 */

@Data
public class InnerCompanyQuery extends BaseQueryModel implements Serializable {
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

	@ApiModelProperty(value = "经营范围分类ID", hidden = true)
	private List<Integer> categoryIdList;

	@ApiModelProperty(value = "经营范围分类ID", hidden = true)
	private List<String> categoryCodeList;

	@ApiModelProperty(value = "经营范围分类ID", hidden = true)
	private List<Integer> industryValue;

	private String industryValueStr;

	private Integer companyType;


	@ApiModelProperty(value = "排除类型 ", hidden = true)
	private List<Integer> excludeCompanyType;

	@ApiModelProperty(value = "列表类型 1:厂商/2:企业")
	@NotNull
	@Min(1)
	private Integer listType;

}
