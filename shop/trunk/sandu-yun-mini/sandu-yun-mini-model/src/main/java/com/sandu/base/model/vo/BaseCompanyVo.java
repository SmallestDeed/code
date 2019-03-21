package com.sandu.base.model.vo;

import java.util.List;
import com.sandu.resource.model.vo.ResPicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 企业信息输出实体类
 * @author Administrator
 * @param <ResPicVo>
 *
 */
@ApiModel(value = "企业信息", description = "企业信息")
public class BaseCompanyVo extends BaseVo<BaseCompanyVo>{
	private static final long serialVersionUID = 1793688983697047126L;
	@ApiModelProperty(value = "类别Id")
	private Integer businessType;
	@ApiModelProperty(value = "企业类别名称")
	private String businessTypeName;
	@ApiModelProperty(value = "企业编码")
	private String companyCode;
	@ApiModelProperty(value = "企业名称")
	private String companyName;
	@ApiModelProperty(value = "通讯地址")
	private String companyAddress;
	@ApiModelProperty(value = "企业经营产品范围")
	private String productVisibilityRange;
	@ApiModelProperty(value = "会员年限")
	private int memberYear;
	@ApiModelProperty(value = "浏览次数")
	private int visitCount;
	@ApiModelProperty(value = "好评率")
	private float praiseRate;
	@ApiModelProperty(value = "好评率比分比")
	private String praiseRatePercent;
	@ApiModelProperty(value = "认证等级")
	private int authGrade;
	@ApiModelProperty(value = "保证金")
	private float deposit;
	@ApiModelProperty(value = "企业Logo图片资源Url")
	private String logoUrl;
	@ApiModelProperty(value = "企业分类ID集合")
	private String categoryIds;
	@ApiModelProperty(value = "企业分类名称集合")
	private String categoryNames;
	@ApiModelProperty(value = "企业图片资源")
	private List<ResPicVo> lstResPic;


	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getProductVisibilityRange() {
		return productVisibilityRange;
	}

	public void setProductVisibilityRange(String productVisibilityRange) {
		this.productVisibilityRange = productVisibilityRange;
	}

	public int getMemberYear() {
		return memberYear;
	}

	public void setMemberYear(int memberYear) {
		this.memberYear = memberYear;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public float getPraiseRate() {
		return praiseRate;
	}

	public void setPraiseRate(float praiseRate) {
		this.praiseRate = praiseRate;
	}

	public String getPraiseRatePercent() {
		return praiseRatePercent;
	}

	public void setPraiseRatePercent(String praiseRatePercent) {
		this.praiseRatePercent = praiseRatePercent;
	}

	public int getAuthGrade() {
		return authGrade;
	}

	public void setAuthGrade(int authGrade) {
		this.authGrade = authGrade;
	}

	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public List<ResPicVo> getLstResPic() {
		return lstResPic;
	}

	public void setLstResPic(List<ResPicVo> lstResPic) {
		this.lstResPic = lstResPic;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}
}
