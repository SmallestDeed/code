package com.sandu.company.model;

import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  企业设计师
 * @author zhangbin
 *
 */
@ApiModel(value = "企业设计师信息", description = "企业设计师信息")
public class CompanyDesigner extends DataEntity<CompanyDesigner>{
	
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "设计师名称")
	private String designer_name;
	@ApiModelProperty(value = "风格ID")
	private String style_ids;
	@ApiModelProperty(value = "设计师职称")
	private String job_title;
	@ApiModelProperty(value = "公司下的设计师数量")
	private Integer designerCount;
	
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getDesigner_name() {
		return designer_name;
	}
	public void setDesigner_name(String designer_name) {
		this.designer_name = designer_name;
	}
	public String getStyle_ids() {
		return style_ids;
	}
	public void setStyle_ids(String style_ids) {
		this.style_ids = style_ids;
	}

	public Integer getDesignerCount() {
		return designerCount;
	}

	public void setDesignerCount(Integer designerCount) {
		this.designerCount = designerCount;
	}
}
