package com.nork.product.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Title: CompanyShop.java 
 * @Package com.nork.product.model
 * @Description:企业商铺-企业店铺
 * @createAuthor pandajun 
 * @CreateDate 2018-05-22 20:08:57
 * @version V1.0   
 */
public class CompanyShop extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
	/** 主键ID **/
	private Integer id;
	/**  企业Id  **/
	private Integer companyId;
	/** 企业父ID(经销商-->厂商) **/
	private Integer companyPid;
	/**  用户Id  **/
	private Integer userId;
	/** 店铺编码 **/
	private String shopCode;
	/**  店铺名称  **/
	private String shopName;
	/**  店铺类型 设计师、设计公司、装修公司、工长、品牌馆、家居建材  **/
	private Integer businessType;
	/** 分类ids（设计师---擅长风格，施工单位---施工类型，家居建材---产品分类等）**/
	private String categoryIds;
	/** 一级分类ids（建材、家居、电器）**/
	private String firstCategoryIds;
	/**  省编码  **/
	private String provinceCode;
	/**  市编码  **/
	private String cityCode;
	/**  区编码  **/
	private String areaCode;
	/**  街道编码  **/
	private String streetCode;
	/**  地区长编码  **/
	private String longAreaCode;
	/**  详细地址  **/
	private String shopAddress;
	/**  联系电话  **/
	private String contactPhone;
	/**  联系人姓名  **/
	private String contactName;
	/**  logo图ID  **/
	private Integer logoPicId;
	/** 店铺封面资源Ids **/
	private String coverResIds;
	/** 店铺封面资源类型 1:图片列表,2:全景图,3:视频 **/
	private Integer coverResType;
	/**  发布平台类型 1:小程序2:选装网3:同城联盟  **/
	private String releasePlatformValues;
	/**  显示状态(1是0否)  **/
	private Integer displayStatus;
	/**  访问次数  **/
	private Integer visitCount;
	/**  好评率  **/
	private Double praiseRate;
	/**  店铺介绍  **/
	private String shopIntroduced;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  备注  **/
	private String remark;
	//富文本介绍文本Id
	private Long introducedFileId;
	//富文本介绍图片Ids
	private String introducedPicIds;
	//修改企业类型清空店铺属性分类标识
	private String cleanCategorySign;

	private String categoryNames;

	private String sysUserPhone;

	private String shopTypeName;

	private String releasePlatformName;

	private String companyName;
	/** 封面图ID **/
	private Integer coverPicId;
	// 店铺logo地址
	private String shopLogoPath;
	//查询条件:发布的平台
	private String sch_releasePlatformValues;

	public String getSch_releasePlatformValues() {
		return sch_releasePlatformValues;
	}

	public void setSch_releasePlatformValues(String sch_releasePlatformValues) {
		this.sch_releasePlatformValues = sch_releasePlatformValues;
	}

	public String getShopLogoPath() {
		return shopLogoPath;
	}

	public void setShopLogoPath(String shopLogoPath) {
		this.shopLogoPath = shopLogoPath;
	}

	public Integer getCoverPicId() {
		return coverPicId;
	}

	public void setCoverPicId(Integer coverPicId) {
		this.coverPicId = coverPicId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getSysUserPhone() {
		return sysUserPhone;
	}

	public void setSysUserPhone(String sysUserPhone) {
		this.sysUserPhone = sysUserPhone;
	}

	public String getShopTypeName() {
		return shopTypeName;
	}

	public void setShopTypeName(String shopTypeName) {
		this.shopTypeName = shopTypeName;
	}

	public String getReleasePlatformName() {
		return releasePlatformName;
	}

	public void setReleasePlatformName(String releasePlatformName) {
		this.releasePlatformName = releasePlatformName;
	}

	public String getCleanCategorySign() {
		return cleanCategorySign;
	}

	public void setCleanCategorySign(String cleanCategorySign) {
		this.cleanCategorySign = cleanCategorySign;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyPid() {
		return companyPid;
	}

	public void setCompanyPid(Integer companyPid) {
		this.companyPid = companyPid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getFirstCategoryIds() {
		return firstCategoryIds;
	}

	public void setFirstCategoryIds(String firstCategoryIds) {
		this.firstCategoryIds = firstCategoryIds;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getStreetCode() {
		return streetCode;
	}

	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
	}

	public String getLongAreaCode() {
		return longAreaCode;
	}

	public void setLongAreaCode(String longAreaCode) {
		this.longAreaCode = longAreaCode;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Integer getLogoPicId() {
		return logoPicId;
	}

	public void setLogoPicId(Integer logoPicId) {
		this.logoPicId = logoPicId;
	}

	public String getCoverResIds() {
		return coverResIds;
	}

	public void setCoverResIds(String coverResIds) {
		this.coverResIds = coverResIds;
	}

	public Integer getCoverResType() {
		return coverResType;
	}

	public void setCoverResType(Integer coverResType) {
		this.coverResType = coverResType;
	}

	public String getReleasePlatformValues() {
		return releasePlatformValues;
	}

	public void setReleasePlatformValues(String releasePlatformValues) {
		this.releasePlatformValues = releasePlatformValues;
	}

	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(Integer displayStatus) {
		this.displayStatus = displayStatus;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Double getPraiseRate() {
		return praiseRate;
	}

	public void setPraiseRate(Double praiseRate) {
		this.praiseRate = praiseRate;
	}

	public String getShopIntroduced() {
		return shopIntroduced;
	}

	public void setShopIntroduced(String shopIntroduced) {
		this.shopIntroduced = shopIntroduced;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getIntroducedFileId() {
		return introducedFileId;
	}

	public void setIntroducedFileId(Long introducedFileId) {
		this.introducedFileId = introducedFileId;
	}

	public String getIntroducedPicIds() {
		return introducedPicIds;
	}

	public void setIntroducedPicIds(String introducedPicIds) {
		this.introducedPicIds = introducedPicIds;
	}

	@Override
	public String toString() {
		return "CompanyShop{" +
				"id=" + id +
				", companyId=" + companyId +
				", companyPid=" + companyPid +
				", userId=" + userId +
				", shopCode='" + shopCode + '\'' +
				", shopName='" + shopName + '\'' +
				", businessType=" + businessType +
				", categoryIds='" + categoryIds + '\'' +
				", firstCategoryIds='" + firstCategoryIds + '\'' +
				", provinceCode='" + provinceCode + '\'' +
				", cityCode='" + cityCode + '\'' +
				", areaCode='" + areaCode + '\'' +
				", streetCode='" + streetCode + '\'' +
				", longAreaCode='" + longAreaCode + '\'' +
				", shopAddress='" + shopAddress + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				", contactName='" + contactName + '\'' +
				", logoPicId=" + logoPicId +
				", coverResIds='" + coverResIds + '\'' +
				", coverResType=" + coverResType +
				", releasePlatformValues='" + releasePlatformValues + '\'' +
				", displayStatus=" + displayStatus +
				", visitCount=" + visitCount +
				", praiseRate=" + praiseRate +
				", shopIntroduced='" + shopIntroduced + '\'' +
				", sysCode='" + sysCode + '\'' +
				", creator='" + creator + '\'' +
				", gmtCreate=" + gmtCreate +
				", modifier='" + modifier + '\'' +
				", gmtModified=" + gmtModified +
				", isDeleted=" + isDeleted +
				", remark='" + remark + '\'' +
				", introducedFileId=" + introducedFileId +
				", introducedPicIds='" + introducedPicIds + '\'' +
				", cleanCategorySign='" + cleanCategorySign + '\'' +
				", categoryNames='" + categoryNames + '\'' +
				", sysUserPhone='" + sysUserPhone + '\'' +
				", shopTypeName='" + shopTypeName + '\'' +
				", releasePlatformName='" + releasePlatformName + '\'' +
				", companyName='" + companyName + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CompanyShop that = (CompanyShop) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
		if (companyPid != null ? !companyPid.equals(that.companyPid) : that.companyPid != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (shopCode != null ? !shopCode.equals(that.shopCode) : that.shopCode != null) return false;
		if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
		if (businessType != null ? !businessType.equals(that.businessType) : that.businessType != null) return false;
		if (categoryIds != null ? !categoryIds.equals(that.categoryIds) : that.categoryIds != null) return false;
		if (firstCategoryIds != null ? !firstCategoryIds.equals(that.firstCategoryIds) : that.firstCategoryIds != null)
			return false;
		if (provinceCode != null ? !provinceCode.equals(that.provinceCode) : that.provinceCode != null) return false;
		if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
		if (areaCode != null ? !areaCode.equals(that.areaCode) : that.areaCode != null) return false;
		if (streetCode != null ? !streetCode.equals(that.streetCode) : that.streetCode != null) return false;
		if (longAreaCode != null ? !longAreaCode.equals(that.longAreaCode) : that.longAreaCode != null) return false;
		if (shopAddress != null ? !shopAddress.equals(that.shopAddress) : that.shopAddress != null) return false;
		if (contactPhone != null ? !contactPhone.equals(that.contactPhone) : that.contactPhone != null) return false;
		if (contactName != null ? !contactName.equals(that.contactName) : that.contactName != null) return false;
		if (logoPicId != null ? !logoPicId.equals(that.logoPicId) : that.logoPicId != null) return false;
		if (coverResIds != null ? !coverResIds.equals(that.coverResIds) : that.coverResIds != null) return false;
		if (coverResType != null ? !coverResType.equals(that.coverResType) : that.coverResType != null) return false;
		if (releasePlatformValues != null ? !releasePlatformValues.equals(that.releasePlatformValues) : that.releasePlatformValues != null)
			return false;
		if (displayStatus != null ? !displayStatus.equals(that.displayStatus) : that.displayStatus != null)
			return false;
		if (visitCount != null ? !visitCount.equals(that.visitCount) : that.visitCount != null) return false;
		if (praiseRate != null ? !praiseRate.equals(that.praiseRate) : that.praiseRate != null) return false;
		if (shopIntroduced != null ? !shopIntroduced.equals(that.shopIntroduced) : that.shopIntroduced != null)
			return false;
		if (sysCode != null ? !sysCode.equals(that.sysCode) : that.sysCode != null) return false;
		if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
		if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
		if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
		if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null) return false;
		if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
		if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
		if (introducedFileId != null ? !introducedFileId.equals(that.introducedFileId) : that.introducedFileId != null)
			return false;
		if (introducedPicIds != null ? !introducedPicIds.equals(that.introducedPicIds) : that.introducedPicIds != null)
			return false;
		if (cleanCategorySign != null ? !cleanCategorySign.equals(that.cleanCategorySign) : that.cleanCategorySign != null)
			return false;
		if (categoryNames != null ? !categoryNames.equals(that.categoryNames) : that.categoryNames != null)
			return false;
		if (sysUserPhone != null ? !sysUserPhone.equals(that.sysUserPhone) : that.sysUserPhone != null) return false;
		if (shopTypeName != null ? !shopTypeName.equals(that.shopTypeName) : that.shopTypeName != null) return false;
		if (releasePlatformName != null ? !releasePlatformName.equals(that.releasePlatformName) : that.releasePlatformName != null)
			return false;
		return companyName != null ? companyName.equals(that.companyName) : that.companyName == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
		result = 31 * result + (companyPid != null ? companyPid.hashCode() : 0);
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (shopCode != null ? shopCode.hashCode() : 0);
		result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
		result = 31 * result + (businessType != null ? businessType.hashCode() : 0);
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + (firstCategoryIds != null ? firstCategoryIds.hashCode() : 0);
		result = 31 * result + (provinceCode != null ? provinceCode.hashCode() : 0);
		result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
		result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
		result = 31 * result + (streetCode != null ? streetCode.hashCode() : 0);
		result = 31 * result + (longAreaCode != null ? longAreaCode.hashCode() : 0);
		result = 31 * result + (shopAddress != null ? shopAddress.hashCode() : 0);
		result = 31 * result + (contactPhone != null ? contactPhone.hashCode() : 0);
		result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
		result = 31 * result + (logoPicId != null ? logoPicId.hashCode() : 0);
		result = 31 * result + (coverResIds != null ? coverResIds.hashCode() : 0);
		result = 31 * result + (coverResType != null ? coverResType.hashCode() : 0);
		result = 31 * result + (releasePlatformValues != null ? releasePlatformValues.hashCode() : 0);
		result = 31 * result + (displayStatus != null ? displayStatus.hashCode() : 0);
		result = 31 * result + (visitCount != null ? visitCount.hashCode() : 0);
		result = 31 * result + (praiseRate != null ? praiseRate.hashCode() : 0);
		result = 31 * result + (shopIntroduced != null ? shopIntroduced.hashCode() : 0);
		result = 31 * result + (sysCode != null ? sysCode.hashCode() : 0);
		result = 31 * result + (creator != null ? creator.hashCode() : 0);
		result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
		result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
		result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (introducedFileId != null ? introducedFileId.hashCode() : 0);
		result = 31 * result + (introducedPicIds != null ? introducedPicIds.hashCode() : 0);
		result = 31 * result + (cleanCategorySign != null ? cleanCategorySign.hashCode() : 0);
		result = 31 * result + (categoryNames != null ? categoryNames.hashCode() : 0);
		result = 31 * result + (sysUserPhone != null ? sysUserPhone.hashCode() : 0);
		result = 31 * result + (shopTypeName != null ? shopTypeName.hashCode() : 0);
		result = 31 * result + (releasePlatformName != null ? releasePlatformName.hashCode() : 0);
		result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
		return result;
	}
}
