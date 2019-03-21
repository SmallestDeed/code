package com.sandu.company.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.sandu.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 企业/设计师认证信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "认证信息", description = "企业/设计师认证信息")
public class CompanyAuth extends DataEntity<CompanyAuth> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "证书类型")
	private int credentialType;
	@ApiModelProperty(value = "证书文件路径")
	private String credentialFileUrl;
	@ApiModelProperty(value = "审核状态")
	private int authState;
	@ApiModelProperty(value = "审核日期")
	private Date authDate;
	@ApiModelProperty(value = "审核备注")
	private String authRemark;
	@ApiModelProperty(value = "审核人ID")
	private long authId;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(int credentialType) {
		this.credentialType = credentialType;
	}

	@NotNull(message = "证件文件路径不能为空")
	@Length(min = 1, max = 200, message = "证件文件路径必须介于 1 和 200 之间")
	public String getCredentialFileUrl() {
		return credentialFileUrl;
	}

	public void setCredentialFileUrl(String credentialFileUrl) {
		this.credentialFileUrl = credentialFileUrl;
	}

	public int getAuthState() {
		return authState;
	}

	public void setAuthState(int authState) {
		this.authState = authState;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	@Length(min = 0, max = 100, message = "审核备注的长度必须介于 0 和 200 之间")
	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public long getAuthId() {
		return authId;
	}

	public void setAuthId(long authId) {
		this.authId = authId;
	}
}
