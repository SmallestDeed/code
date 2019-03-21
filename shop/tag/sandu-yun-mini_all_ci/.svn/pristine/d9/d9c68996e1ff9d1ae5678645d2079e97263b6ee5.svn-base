package com.sandu.company.model.query;

import com.sandu.base.model.query.BaseQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  企业设计师查询条件
 * @author zhangbin
 *
 */
@ApiModel(value = "企业设计师信息", description = "企业设计师信息")
@Data
public class CompanyDesignerQuery extends BaseQuery<CompanyDesignerQuery>{
	private static final long serialVersionUID = 798484153986262764L;
	@ApiModelProperty(value = "公司ID")
	private Long companyId;

	@ApiModelProperty(value = "当前平台类型（1:小程序2:选装网3:同城联盟）")
	private Integer platformValue;
}
