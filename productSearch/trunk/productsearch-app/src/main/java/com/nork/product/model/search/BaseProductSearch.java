package com.nork.product.model.search;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nork.product.model.BaseProduct;

/**   
 * @Title: BaseProductSearch.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品库查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
public class BaseProductSearch extends  BaseProduct implements Serializable{
private static final long serialVersionUID = 1L;
	/**  系统编码-模糊查询  **/
	private String sch_SysCode_;
	/**  系统编码-左模糊查询  **/
	private String sch_SysCode;
	/**  系统编码-右模糊查询  **/
	private String schSysCode_;
	/**  系统编码-区间查询-开始字符串  **/
	private String sysCodeStart;
	/**  系统编码-区间查询-结束字符串  **/
	private String sysCodeEnd;
	/**  创建者-模糊查询  **/
	private String sch_Creator_;
	/**  创建者-左模糊查询  **/
	private String sch_Creator;
	/**  创建者-右模糊查询  **/
	private String schCreator_;
	/**  创建者-区间查询-开始字符串  **/
	private String creatorStart;
	/**  创建者-区间查询-结束字符串  **/
	private String creatorEnd;
	/**  创建时间-区间查询-开始时间  **/
	private Date gmtCreateStart;
	/**  创建时间-区间查询-结束时间  **/
	private Date gmtCreateEnd;
	/**  修改人-模糊查询  **/
	private String sch_Modifier_;
	/**  修改人-左模糊查询  **/
	private String sch_Modifier;
	/**  修改人-右模糊查询  **/
	private String schModifier_;
	/**  修改人-区间查询-开始字符串  **/
	private String modifierStart;
	/**  修改人-区间查询-结束字符串  **/
	private String modifierEnd;
	/**  修改时间-区间查询-开始时间  **/
	private Date gmtModifiedStart;
	/**  修改时间-区间查询-结束时间  **/
	private Date gmtModifiedEnd;
	/**  产品编码-模糊查询  **/
	private String sch_ProductCode_;
	/**  产品编码-左模糊查询  **/
	private String sch_ProductCode;
	/**  产品编码-右模糊查询  **/
	private String schProductCode_;
	/**  产品编码-区间查询-开始字符串  **/
	private String productCodeStart;
	/**  产品编码-区间查询-结束字符串  **/
	private String productCodeEnd;
	/**  产品名称-模糊查询  **/
	private String sch_ProductName_;
	/**  产品名称-左模糊查询  **/
	private String sch_ProductName;
	/**  产品名称-右模糊查询  **/
	private String schProductName_;
	/**  产品名称-区间查询-开始字符串  **/
	private String productNameStart;
	/**  产品名称-区间查询-结束字符串  **/
	private String productNameEnd;
	/**  产品规格-模糊查询  **/
	private String sch_ProductSpec_;
	/**  产品规格-左模糊查询  **/
	private String sch_ProductSpec;
	/**  产品规格-右模糊查询  **/
	private String schProductSpec_;
	/**  产品规格-区间查询-开始字符串  **/
	private String productSpecStart;
	/**  产品规格-区间查询-结束字符串  **/
	private String productSpecEnd;
	/**  产品长度-模糊查询  **/
	private String sch_ProductLength_;
	/**  产品长度-左模糊查询  **/
	private String sch_ProductLength;
	/**  产品长度-右模糊查询  **/
	private String schProductLength_;
	/**  产品长度-区间查询-开始字符串  **/
	private String productLengthStart;
	/**  产品长度-区间查询-结束字符串  **/
	private String productLengthEnd;
	/**  产品宽度-模糊查询  **/
	private String sch_ProductWidth_;
	/**  产品宽度-左模糊查询  **/
	private String sch_ProductWidth;
	/**  产品宽度-右模糊查询  **/
	private String schProductWidth_;
	/**  产品宽度-区间查询-开始字符串  **/
	private String productWidthStart;
	/**  产品宽度-区间查询-结束字符串  **/
	private String productWidthEnd;
	/**  产品高度-模糊查询  **/
	private String sch_ProductHeight_;
	/**  产品高度-左模糊查询  **/
	private String sch_ProductHeight;
	/**  产品高度-右模糊查询  **/
	private String schProductHeight_;
	/**  产品高度-区间查询-开始字符串  **/
	private String productHeightStart;
	/**  产品高度-区间查询-结束字符串  **/
	private String productHeightEnd;
	/**  产品描述-模糊查询  **/
	private String sch_ProductDesc_;
	/**  产品描述-左模糊查询  **/
	private String sch_ProductDesc;
	/**  产品描述-右模糊查询  **/
	private String schProductDesc_;
	/**  产品描述-区间查询-开始字符串  **/
	private String productDescStart;
	/**  产品描述-区间查询-结束字符串  **/
	private String productDescEnd;
	/**  字符备用1-模糊查询  **/
	private String sch_PicIds_;
	/**  字符备用1-左模糊查询  **/
	private String sch_PicIds;
	/**  字符备用1-右模糊查询  **/
	private String schPicIds_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String picIdsStart;
	/**  字符备用1-区间查询-结束字符串  **/
	private String picIdsEnd;
	/**  字符备用2-模糊查询  **/
	private String sch_MaterialPicIds_;
	/**  字符备用2-左模糊查询  **/
	private String sch_MaterialPicIds;
	/**  字符备用2-右模糊查询  **/
	private String schMaterialPicIds_;
	/**  字符备用2-区间查询-开始字符串  **/
	private String materialPicIdsStart;
	/**  字符备用2-区间查询-结束字符串  **/
	private String materialPicIdsEnd;
	/**  字符备用3-模糊查询  **/
	private String sch_HouseTypeValues_;
	/**  字符备用3-左模糊查询  **/
	private String sch_HouseTypeValues;
	/**  字符备用3-右模糊查询  **/
	private String schHouseTypeValues_;
	/**  字符备用3-区间查询-开始字符串  **/
	private String houseTypeValuesStart;
	/**  字符备用3-区间查询-结束字符串  **/
	private String houseTypeValuesEnd;
	/**  字符备用4-模糊查询  **/
	private String sch_ProductTypeValue_;
	/**  字符备用4-左模糊查询  **/
	private String sch_ProductTypeValue;
	/**  字符备用4-右模糊查询  **/
	private String schProductTypeValue_;
	/**  字符备用4-区间查询-开始字符串  **/
	private String productTypeValueStart;
	/**  字符备用4-区间查询-结束字符串  **/
	private String productTypeValueEnd;
	
	/**  产品大分类标示-模糊查询  **/
	private String sch_ProductTypeMark_;
	/**  产品大分类标示-左模糊查询  **/
	private String sch_ProductTypeMark;
	/**  产品大分类标示-右模糊查询  **/
	private String schProductTypeMark_;
	/**  产品大分类标示-区间查询-开始字符串  **/
	private String productTypeMarkStart;
	/**  产品大分类标示-区间查询-结束字符串  **/
	private String productTypeMarkEnd;
	
	/**  产品小分类标示-模糊查询  **/
	private String sch_ProductSmallTypeMark_;
	/**  产品小分类标示-左模糊查询  **/
	private String sch_ProductSmallTypeMark;
	/**  产品小分类标示-右模糊查询  **/
	private String schProductSmallTypeMark_;
	/**  产品小分类标示-区间查询-开始字符串  **/
	private String productSmallTypeMarkStart;
	/**  产品小分类标示-区间查询-结束字符串  **/
	private String productSmallTypeMarkEnd;
	
	/**  产品大分类标示-模糊查询  **/
	private String sch_ProductShortCode_;
	/**  产品大分类标示-左模糊查询  **/
	private String sch_ProductShortCode;
	/**  产品大分类标示-右模糊查询  **/
	private String schProductShortCode_;
	/**  产品大分类标示-区间查询-开始字符串  **/
	private String productShortCodeStart;
	/**  产品大分类标示-区间查询-结束字符串  **/
	private String productShortCodeEnd;
	
	/**  产品大分类标示-模糊查询  **/
	private String sch_OriginalProductCode_;
	/**  产品大分类标示-左模糊查询  **/
	private String sch_OriginalProductCode;
	/**  产品大分类标示-右模糊查询  **/
	private String schOriginalProductCode_;
	/**  产品大分类标示-区间查询-开始字符串  **/
	private String originalProductCodeStart;
	/**  产品大分类标示-区间查询-结束字符串  **/
	private String originalProductCodeEnd;
	
	/**  字符备用5-模糊查询  **/
	private String sch_U3dModelId_;
	/**  字符备用5-左模糊查询  **/
	private String sch_U3dModelId;
	/**  字符备用5-右模糊查询  **/
	private String schU3dModelId_;
	/**  字符备用5-区间查询-开始字符串  **/
	private String u3dModelIdStart;
	/**  字符备用5-区间查询-结束字符串  **/
	private String u3dModelIdEnd;
	/**  字符备用6-模糊查询  **/
	private String sch_Att6_;
	/**  字符备用6-左模糊查询  **/
	private String sch_Att6;
	/**  字符备用6-右模糊查询  **/
	private String schAtt6_;
	/**  字符备用6-区间查询-开始字符串  **/
	private String att6Start;
	/**  字符备用6-区间查询-结束字符串  **/
	private String att6End;
	/**  时间备用1-区间查询-开始时间  **/
	private Date putawayModifiedStart;
	/**  时间备用1-区间查询-结束时间  **/
	private Date putawayModifiedEnd;
	/**  时间备用2-区间查询-开始时间  **/
	private Date dateAtt2Start;
	/**  时间备用2-区间查询-结束时间  **/
	private Date dateAtt2End;
	/**  备注-模糊查询  **/
	private String sch_Remark_;
	/**  备注-左模糊查询  **/
	private String sch_Remark;
	/**  备注-右模糊查询  **/
	private String schRemark_;
	/**  备注-区间查询-开始字符串  **/
	private String remarkStart;
	/**  备注-区间查询-结束字符串  **/
	private String remarkEnd;
	/**  产品类目CODE  **/
	private String sch_categoryCode_;
	/**  产品类目名称  **/
	private String sch_categoryName_;
	/** 开始时间字符串 **/
	private String gmtCreateStartStr;
	/** 结束时间字符串 **/
	private String gmtCreateEndStr;
	private Date tomorrow;
	//开始时间字符串
	private String startDate;
	//结束时间字符串
	private String endDate;
	//开始时间date
	private Date startDateD;
	//结束时间date
	private Date endDateD;
	/*userIds*/
	private List<Integer> userIds;
	/*userName*/
	private List<String> usernames;
	
	private Integer ProductLengthStartInteger;
	
	private Integer ProductLengthEndInteger;
	/**
	 * 小类value排序(靠前)
	 */
	private Integer orderSmallTypeValue;
	/**
	 * 款式排序
	 */
	private Integer orderStyleId;
	
	/**
	 * 品牌排序,本身品牌>无品牌>其他品牌
	 */
	private Integer orderBrandId;
	
	/**
	 * 型号排序,本身型号>其他型号
	 */
	private String orderProductModelNumber;
	
	public Integer getOrderBrandId() {
		return orderBrandId;
	}
	public void setOrderBrandId(Integer orderBrandId) {
		this.orderBrandId = orderBrandId;
	}
	public String getOrderProductModelNumber() {
		return orderProductModelNumber;
	}
	public void setOrderProductModelNumber(String orderProductModelNumber) {
		this.orderProductModelNumber = orderProductModelNumber;
	}
	public Integer getOrderStyleId() {
		return orderStyleId;
	}
	public void setOrderStyleId(Integer orderStyleId) {
		this.orderStyleId = orderStyleId;
	}
	public Integer getOrderSmallTypeValue() {
		return orderSmallTypeValue;
	}
	public void setOrderSmallTypeValue(Integer orderSmallTypeValue) {
		this.orderSmallTypeValue = orderSmallTypeValue;
	}
	public Integer getProductLengthStartInteger() {
		return ProductLengthStartInteger;
	}
	public void setProductLengthStartInteger(Integer productLengthStartInteger) {
		ProductLengthStartInteger = productLengthStartInteger;
	}
	public Integer getProductLengthEndInteger() {
		return ProductLengthEndInteger;
	}
	public void setProductLengthEndInteger(Integer productLengthEndInteger) {
		ProductLengthEndInteger = productLengthEndInteger;
	}
	public List<String> getUsernames() {
		return usernames;
	}
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	public List<Integer> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
	public Date getStartDateD() {
		return startDateD;
	}
	public void setStartDateD(Date startDateD) {
		this.startDateD = startDateD;
	}
	public Date getEndDateD() {
		return endDateD;
	}
	public void setEndDateD(Date endDateD) {
		this.endDateD = endDateD;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Date getTomorrow() {
		return tomorrow;
	}
	public void setTomorrow(Date tomorrow) {
		this.tomorrow = tomorrow;
	}
	public  String getSch_SysCode_() {
		return sch_SysCode_;
	}
	public void setSch_SysCode_(String sch_SysCode_){
		this.sch_SysCode_ = sch_SysCode_;
	}
	public  String getSch_SysCode() {
		return sch_SysCode;
	}
	public void setSch_SysCode(String sch_SysCode){
		this.sch_SysCode = sch_SysCode;
	}
	public  String getSchSysCode_() {
		return schSysCode_;
	}
	public void setSchSysCode_(String schSysCode_){
		this.schSysCode_ = schSysCode_;
	}
	public  String getSysCodeStart() {
		return sysCodeStart;
	}
	public void setSysCodeStart(String sysCodeStart){
		this.sysCodeStart = sysCodeStart;
	}
	public  String getSysCodeEnd() {
		return sysCodeEnd;
	}
	public void setSysCodeEnd(String sysCodeEnd){
		this.sysCodeEnd = sysCodeEnd;
	}
	public  String getSch_Creator_() {
		return sch_Creator_;
	}
	public void setSch_Creator_(String sch_Creator_){
		this.sch_Creator_ = sch_Creator_;
	}
	public  String getSch_Creator() {
		return sch_Creator;
	}
	public void setSch_Creator(String sch_Creator){
		this.sch_Creator = sch_Creator;
	}
	public  String getSchCreator_() {
		return schCreator_;
	}
	public void setSchCreator_(String schCreator_){
		this.schCreator_ = schCreator_;
	}
	public  String getCreatorStart() {
		return creatorStart;
	}
	public void setCreatorStart(String creatorStart){
		this.creatorStart = creatorStart;
	}
	public  String getCreatorEnd() {
		return creatorEnd;
	}
	public void setCreatorEnd(String creatorEnd){
		this.creatorEnd = creatorEnd;
	}
	public  Date getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(Date gmtCreateStart){
		this.gmtCreateStart = gmtCreateStart;
	}
	public  Date getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(Date gmtCreateEnd){
		this.gmtCreateEnd = gmtCreateEnd;
	}
	public  String getSch_Modifier_() {
		return sch_Modifier_;
	}
	public void setSch_Modifier_(String sch_Modifier_){
		this.sch_Modifier_ = sch_Modifier_;
	}
	public  String getSch_Modifier() {
		return sch_Modifier;
	}
	public void setSch_Modifier(String sch_Modifier){
		this.sch_Modifier = sch_Modifier;
	}
	public  String getSchModifier_() {
		return schModifier_;
	}
	public void setSchModifier_(String schModifier_){
		this.schModifier_ = schModifier_;
	}
	public  String getModifierStart() {
		return modifierStart;
	}
	public void setModifierStart(String modifierStart){
		this.modifierStart = modifierStart;
	}
	public  String getModifierEnd() {
		return modifierEnd;
	}
	public void setModifierEnd(String modifierEnd){
		this.modifierEnd = modifierEnd;
	}
	public  Date getGmtModifiedStart() {
		return gmtModifiedStart;
	}
	public void setGmtModifiedStart(Date gmtModifiedStart){
		this.gmtModifiedStart = gmtModifiedStart;
	}
	public  Date getGmtModifiedEnd() {
		return gmtModifiedEnd;
	}
	public void setGmtModifiedEnd(Date gmtModifiedEnd){
		this.gmtModifiedEnd = gmtModifiedEnd;
	}
	public  String getSch_ProductCode_() {
		return sch_ProductCode_;
	}
	public void setSch_ProductCode_(String sch_ProductCode_){
		this.sch_ProductCode_ = sch_ProductCode_;
	}
	public  String getSch_ProductCode() {
		return sch_ProductCode;
	}
	public void setSch_ProductCode(String sch_ProductCode){
		this.sch_ProductCode = sch_ProductCode;
	}
	public  String getSchProductCode_() {
		return schProductCode_;
	}
	public void setSchProductCode_(String schProductCode_){
		this.schProductCode_ = schProductCode_;
	}
	public  String getProductCodeStart() {
		return productCodeStart;
	}
	public void setProductCodeStart(String productCodeStart){
		this.productCodeStart = productCodeStart;
	}
	public  String getProductCodeEnd() {
		return productCodeEnd;
	}
	public void setProductCodeEnd(String productCodeEnd){
		this.productCodeEnd = productCodeEnd;
	}
	public  String getSch_ProductName_() {
		return sch_ProductName_;
	}
	public void setSch_ProductName_(String sch_ProductName_){
		this.sch_ProductName_ = sch_ProductName_;
	}
	public  String getSch_ProductName() {
		return sch_ProductName;
	}
	public void setSch_ProductName(String sch_ProductName){
		this.sch_ProductName = sch_ProductName;
	}
	public  String getSchProductName_() {
		return schProductName_;
	}
	public void setSchProductName_(String schProductName_){
		this.schProductName_ = schProductName_;
	}
	public  String getProductNameStart() {
		return productNameStart;
	}
	public void setProductNameStart(String productNameStart){
		this.productNameStart = productNameStart;
	}
	public  String getProductNameEnd() {
		return productNameEnd;
	}
	public void setProductNameEnd(String productNameEnd){
		this.productNameEnd = productNameEnd;
	}
	public  String getSch_ProductSpec_() {
		return sch_ProductSpec_;
	}
	public void setSch_ProductSpec_(String sch_ProductSpec_){
		this.sch_ProductSpec_ = sch_ProductSpec_;
	}
	public  String getSch_ProductSpec() {
		return sch_ProductSpec;
	}
	public void setSch_ProductSpec(String sch_ProductSpec){
		this.sch_ProductSpec = sch_ProductSpec;
	}
	public  String getSchProductSpec_() {
		return schProductSpec_;
	}
	public void setSchProductSpec_(String schProductSpec_){
		this.schProductSpec_ = schProductSpec_;
	}
	public  String getProductSpecStart() {
		return productSpecStart;
	}
	public void setProductSpecStart(String productSpecStart){
		this.productSpecStart = productSpecStart;
	}
	public  String getProductSpecEnd() {
		return productSpecEnd;
	}
	public void setProductSpecEnd(String productSpecEnd){
		this.productSpecEnd = productSpecEnd;
	}
	public  String getSch_ProductLength_() {
		return sch_ProductLength_;
	}
	public void setSch_ProductLength_(String sch_ProductLength_){
		this.sch_ProductLength_ = sch_ProductLength_;
	}
	public  String getSch_ProductLength() {
		return sch_ProductLength;
	}
	public void setSch_ProductLength(String sch_ProductLength){
		this.sch_ProductLength = sch_ProductLength;
	}
	public  String getSchProductLength_() {
		return schProductLength_;
	}
	public void setSchProductLength_(String schProductLength_){
		this.schProductLength_ = schProductLength_;
	}
	public  String getProductLengthStart() {
		return productLengthStart;
	}
	public void setProductLengthStart(String productLengthStart){
		this.productLengthStart = productLengthStart;
	}
	public  String getProductLengthEnd() {
		return productLengthEnd;
	}
	public void setProductLengthEnd(String productLengthEnd){
		this.productLengthEnd = productLengthEnd;
	}
	public  String getSch_ProductWidth_() {
		return sch_ProductWidth_;
	}
	public void setSch_ProductWidth_(String sch_ProductWidth_){
		this.sch_ProductWidth_ = sch_ProductWidth_;
	}
	public  String getSch_ProductWidth() {
		return sch_ProductWidth;
	}
	public void setSch_ProductWidth(String sch_ProductWidth){
		this.sch_ProductWidth = sch_ProductWidth;
	}
	public  String getSchProductWidth_() {
		return schProductWidth_;
	}
	public void setSchProductWidth_(String schProductWidth_){
		this.schProductWidth_ = schProductWidth_;
	}
	public  String getProductWidthStart() {
		return productWidthStart;
	}
	public void setProductWidthStart(String productWidthStart){
		this.productWidthStart = productWidthStart;
	}
	public  String getProductWidthEnd() {
		return productWidthEnd;
	}
	public void setProductWidthEnd(String productWidthEnd){
		this.productWidthEnd = productWidthEnd;
	}
	public  String getSch_ProductHeight_() {
		return sch_ProductHeight_;
	}
	public void setSch_ProductHeight_(String sch_ProductHeight_){
		this.sch_ProductHeight_ = sch_ProductHeight_;
	}
	public  String getSch_ProductHeight() {
		return sch_ProductHeight;
	}
	public void setSch_ProductHeight(String sch_ProductHeight){
		this.sch_ProductHeight = sch_ProductHeight;
	}
	public  String getSchProductHeight_() {
		return schProductHeight_;
	}
	public void setSchProductHeight_(String schProductHeight_){
		this.schProductHeight_ = schProductHeight_;
	}
	public  String getProductHeightStart() {
		return productHeightStart;
	}
	public void setProductHeightStart(String productHeightStart){
		this.productHeightStart = productHeightStart;
	}
	public  String getProductHeightEnd() {
		return productHeightEnd;
	}
	public void setProductHeightEnd(String productHeightEnd){
		this.productHeightEnd = productHeightEnd;
	}
	public  String getSch_ProductDesc_() {
		return sch_ProductDesc_;
	}
	public void setSch_ProductDesc_(String sch_ProductDesc_){
		this.sch_ProductDesc_ = sch_ProductDesc_;
	}
	public  String getSch_ProductDesc() {
		return sch_ProductDesc;
	}
	public void setSch_ProductDesc(String sch_ProductDesc){
		this.sch_ProductDesc = sch_ProductDesc;
	}
	public  String getSchProductDesc_() {
		return schProductDesc_;
	}
	public void setSchProductDesc_(String schProductDesc_){
		this.schProductDesc_ = schProductDesc_;
	}
	public  String getProductDescStart() {
		return productDescStart;
	}
	public void setProductDescStart(String productDescStart){
		this.productDescStart = productDescStart;
	}
	public  String getProductDescEnd() {
		return productDescEnd;
	}
	public void setProductDescEnd(String productDescEnd){
		this.productDescEnd = productDescEnd;
	}
	public String getSch_PicIds_() {
		return sch_PicIds_;
	}
	public void setSch_PicIds_(String sch_PicIds_) {
		this.sch_PicIds_ = sch_PicIds_;
	}
	public String getSch_PicIds() {
		return sch_PicIds;
	}
	public void setSch_PicIds(String sch_PicIds) {
		this.sch_PicIds = sch_PicIds;
	}
	public String getSchPicIds_() {
		return schPicIds_;
	}
	public void setSchPicIds_(String schPicIds_) {
		this.schPicIds_ = schPicIds_;
	}
	public String getPicIdsStart() {
		return picIdsStart;
	}
	public void setPicIdsStart(String picIdsStart) {
		this.picIdsStart = picIdsStart;
	}
	public String getPicIdsEnd() {
		return picIdsEnd;
	}
	public void setPicIdsEnd(String picIdsEnd) {
		this.picIdsEnd = picIdsEnd;
	}
	public String getSch_MaterialPicIds_() {
		return sch_MaterialPicIds_;
	}
	public void setSch_MaterialPicIds_(String sch_MaterialPicIds_) {
		this.sch_MaterialPicIds_ = sch_MaterialPicIds_;
	}
	public String getSch_MaterialPicIds() {
		return sch_MaterialPicIds;
	}
	public void setSch_MaterialPicIds(String sch_MaterialPicIds) {
		this.sch_MaterialPicIds = sch_MaterialPicIds;
	}
	public String getSchMaterialPicIds_() {
		return schMaterialPicIds_;
	}
	public void setSchMaterialPicIds_(String schMaterialPicIds_) {
		this.schMaterialPicIds_ = schMaterialPicIds_;
	}
	public String getMaterialPicIdsStart() {
		return materialPicIdsStart;
	}
	public void setMaterialPicIdsStart(String materialPicIdsStart) {
		this.materialPicIdsStart = materialPicIdsStart;
	}
	public String getMaterialPicIdsEnd() {
		return materialPicIdsEnd;
	}
	public void setMaterialPicIdsEnd(String materialPicIdsEnd) {
		this.materialPicIdsEnd = materialPicIdsEnd;
	}
	public String getSch_HouseTypeValues_() {
		return sch_HouseTypeValues_;
	}
	public String getSch_ProductTypeMark_() {
		return sch_ProductTypeMark_;
	}
	public void setSch_ProductTypeMark_(String sch_ProductTypeMark_) {
		this.sch_ProductTypeMark_ = sch_ProductTypeMark_;
	}
	public String getSch_ProductTypeMark() {
		return sch_ProductTypeMark;
	}
	public void setSch_ProductTypeMark(String sch_ProductTypeMark) {
		this.sch_ProductTypeMark = sch_ProductTypeMark;
	}
	public String getSchProductTypeMark_() {
		return schProductTypeMark_;
	}
	public void setSchProductTypeMark_(String schProductTypeMark_) {
		this.schProductTypeMark_ = schProductTypeMark_;
	}
	public String getProductTypeMarkStart() {
		return productTypeMarkStart;
	}
	public void setProductTypeMarkStart(String productTypeMarkStart) {
		this.productTypeMarkStart = productTypeMarkStart;
	}
	public String getProductTypeMarkEnd() {
		return productTypeMarkEnd;
	}
	public void setProductTypeMarkEnd(String productTypeMarkEnd) {
		this.productTypeMarkEnd = productTypeMarkEnd;
	}
	public String getSch_ProductSmallTypeMark_() {
		return sch_ProductSmallTypeMark_;
	}
	public void setSch_ProductSmallTypeMark_(String sch_ProductSmallTypeMark_) {
		this.sch_ProductSmallTypeMark_ = sch_ProductSmallTypeMark_;
	}
	public String getSch_ProductSmallTypeMark() {
		return sch_ProductSmallTypeMark;
	}
	public void setSch_ProductSmallTypeMark(String sch_ProductSmallTypeMark) {
		this.sch_ProductSmallTypeMark = sch_ProductSmallTypeMark;
	}
	public String getSchProductSmallTypeMark_() {
		return schProductSmallTypeMark_;
	}
	public void setSchProductSmallTypeMark_(String schProductSmallTypeMark_) {
		this.schProductSmallTypeMark_ = schProductSmallTypeMark_;
	}
	public String getProductSmallTypeMarkStart() {
		return productSmallTypeMarkStart;
	}
	public void setProductSmallTypeMarkStart(String productSmallTypeMarkStart) {
		this.productSmallTypeMarkStart = productSmallTypeMarkStart;
	}
	public String getProductSmallTypeMarkEnd() {
		return productSmallTypeMarkEnd;
	}
	public void setProductSmallTypeMarkEnd(String productSmallTypeMarkEnd) {
		this.productSmallTypeMarkEnd = productSmallTypeMarkEnd;
	}
	public String getSch_ProductShortCode_() {
		return sch_ProductShortCode_;
	}
	public void setSch_ProductShortCode_(String sch_ProductShortCode_) {
		this.sch_ProductShortCode_ = sch_ProductShortCode_;
	}
	public String getSch_ProductShortCode() {
		return sch_ProductShortCode;
	}
	public void setSch_ProductShortCode(String sch_ProductShortCode) {
		this.sch_ProductShortCode = sch_ProductShortCode;
	}
	public String getSchProductShortCode_() {
		return schProductShortCode_;
	}
	public void setSchProductShortCode_(String schProductShortCode_) {
		this.schProductShortCode_ = schProductShortCode_;
	}
	public String getProductShortCodeStart() {
		return productShortCodeStart;
	}
	public void setProductShortCodeStart(String productShortCodeStart) {
		this.productShortCodeStart = productShortCodeStart;
	}
	public String getProductShortCodeEnd() {
		return productShortCodeEnd;
	}
	public void setProductShortCodeEnd(String productShortCodeEnd) {
		this.productShortCodeEnd = productShortCodeEnd;
	}
	public String getSch_OriginalProductCode_() {
		return sch_OriginalProductCode_;
	}
	public void setSch_OriginalProductCode_(String sch_OriginalProductCode_) {
		this.sch_OriginalProductCode_ = sch_OriginalProductCode_;
	}
	public String getSch_OriginalProductCode() {
		return sch_OriginalProductCode;
	}
	public void setSch_OriginalProductCode(String sch_OriginalProductCode) {
		this.sch_OriginalProductCode = sch_OriginalProductCode;
	}
	public String getSchOriginalProductCode_() {
		return schOriginalProductCode_;
	}
	public void setSchOriginalProductCode_(String schOriginalProductCode_) {
		this.schOriginalProductCode_ = schOriginalProductCode_;
	}
	public String getOriginalProductCodeStart() {
		return originalProductCodeStart;
	}
	public void setOriginalProductCodeStart(String originalProductCodeStart) {
		this.originalProductCodeStart = originalProductCodeStart;
	}
	public String getOriginalProductCodeEnd() {
		return originalProductCodeEnd;
	}
	public void setOriginalProductCodeEnd(String originalProductCodeEnd) {
		this.originalProductCodeEnd = originalProductCodeEnd;
	}
	public void setSch_HouseTypeValues_(String sch_HouseTypeValues_) {
		this.sch_HouseTypeValues_ = sch_HouseTypeValues_;
	}
	public String getSch_HouseTypeValues() {
		return sch_HouseTypeValues;
	}
	public void setSch_HouseTypeValues(String sch_HouseTypeValues) {
		this.sch_HouseTypeValues = sch_HouseTypeValues;
	}
	public String getSchHouseTypeValues_() {
		return schHouseTypeValues_;
	}
	public void setSchHouseTypeValues_(String schHouseTypeValues_) {
		this.schHouseTypeValues_ = schHouseTypeValues_;
	}
	public String getHouseTypeValuesStart() {
		return houseTypeValuesStart;
	}
	public void setHouseTypeValuesStart(String houseTypeValuesStart) {
		this.houseTypeValuesStart = houseTypeValuesStart;
	}
	public String getHouseTypeValuesEnd() {
		return houseTypeValuesEnd;
	}
	public void setHouseTypeValuesEnd(String houseTypeValuesEnd) {
		this.houseTypeValuesEnd = houseTypeValuesEnd;
	}
	public String getSch_ProductTypeValue_() {
		return sch_ProductTypeValue_;
	}
	public void setSch_ProductTypeValue_(String sch_ProductTypeValue_) {
		this.sch_ProductTypeValue_ = sch_ProductTypeValue_;
	}
	public String getSch_ProductTypeValue() {
		return sch_ProductTypeValue;
	}
	public void setSch_ProductTypeValue(String sch_ProductTypeValue) {
		this.sch_ProductTypeValue = sch_ProductTypeValue;
	}
	public String getSchProductTypeValue_() {
		return schProductTypeValue_;
	}
	public void setSchProductTypeValue_(String schProductTypeValue_) {
		this.schProductTypeValue_ = schProductTypeValue_;
	}
	public String getProductTypeValueStart() {
		return productTypeValueStart;
	}
	public void setProductTypeValueStart(String productTypeValueStart) {
		this.productTypeValueStart = productTypeValueStart;
	}
	public String getProductTypeValueEnd() {
		return productTypeValueEnd;
	}
	public void setProductTypeValueEnd(String productTypeValueEnd) {
		this.productTypeValueEnd = productTypeValueEnd;
	}
	public String getSch_U3dModelId_() {
		return sch_U3dModelId_;
	}
	public void setSch_U3dModelId_(String sch_U3dModelId_) {
		this.sch_U3dModelId_ = sch_U3dModelId_;
	}
	public String getSch_U3dModelId() {
		return sch_U3dModelId;
	}
	public void setSch_U3dModelId(String sch_U3dModelId) {
		this.sch_U3dModelId = sch_U3dModelId;
	}
	public String getSchU3dModelId_() {
		return schU3dModelId_;
	}
	public void setSchU3dModelId_(String schU3dModelId_) {
		this.schU3dModelId_ = schU3dModelId_;
	}
	public String getU3dModelIdStart() {
		return u3dModelIdStart;
	}
	public void setU3dModelIdStart(String u3dModelIdStart) {
		this.u3dModelIdStart = u3dModelIdStart;
	}
	public String getU3dModelIdEnd() {
		return u3dModelIdEnd;
	}
	public void setU3dModelIdEnd(String u3dModelIdEnd) {
		this.u3dModelIdEnd = u3dModelIdEnd;
	}
	public  String getSch_Att6_() {
		return sch_Att6_;
	}
	public void setSch_Att6_(String sch_Att6_){
		this.sch_Att6_ = sch_Att6_;
	}
	public  String getSch_Att6() {
		return sch_Att6;
	}
	public void setSch_Att6(String sch_Att6){
		this.sch_Att6 = sch_Att6;
	}
	public  String getSchAtt6_() {
		return schAtt6_;
	}
	public void setSchAtt6_(String schAtt6_){
		this.schAtt6_ = schAtt6_;
	}
	public  String getAtt6Start() {
		return att6Start;
	}
	public void setAtt6Start(String att6Start){
		this.att6Start = att6Start;
	}
	public  String getAtt6End() {
		return att6End;
	}
	public void setAtt6End(String att6End){
		this.att6End = att6End;
	}

	public Date getPutawayModifiedStart() {
		return putawayModifiedStart;
	}

	public void setPutawayModifiedStart(Date putawayModifiedStart) {
		this.putawayModifiedStart = putawayModifiedStart;
	}

	public Date getPutawayModifiedEnd() {
		return putawayModifiedEnd;
	}

	public void setPutawayModifiedEnd(Date putawayModifiedEnd) {
		this.putawayModifiedEnd = putawayModifiedEnd;
	}

	public  Date getDateAtt2Start() {
		return dateAtt2Start;
	}
	public void setDateAtt2Start(Date dateAtt2Start){
		this.dateAtt2Start = dateAtt2Start;
	}
	public  Date getDateAtt2End() {
		return dateAtt2End;
	}
	public void setDateAtt2End(Date dateAtt2End){
		this.dateAtt2End = dateAtt2End;
	}
	public  String getSch_Remark_() {
		return sch_Remark_;
	}
	public void setSch_Remark_(String sch_Remark_){
		this.sch_Remark_ = sch_Remark_;
	}
	public  String getSch_Remark() {
		return sch_Remark;
	}
	public void setSch_Remark(String sch_Remark){
		this.sch_Remark = sch_Remark;
	}
	public  String getSchRemark_() {
		return schRemark_;
	}
	public void setSchRemark_(String schRemark_){
		this.schRemark_ = schRemark_;
	}
	public  String getRemarkStart() {
		return remarkStart;
	}
	public void setRemarkStart(String remarkStart){
		this.remarkStart = remarkStart;
	}
	public  String getRemarkEnd() {
		return remarkEnd;
	}
	public void setRemarkEnd(String remarkEnd){
		this.remarkEnd = remarkEnd;
	}
	
	private String inHouseType;

	public String getInHouseType() {
		return inHouseType;
	}
	public void setInHouseType(String inHouseType) {
		this.inHouseType = inHouseType;
	}
	
	

	public String getSch_categoryCode_() {
		return sch_categoryCode_;
	}

	public void setSch_categoryCode_(String sch_categoryCode_) {
		this.sch_categoryCode_ = sch_categoryCode_;
	}

	public String getSch_categoryName_() {
		return sch_categoryName_;
	}

	public void setSch_categoryName_(String sch_categoryName_) {
		this.sch_categoryName_ = sch_categoryName_;
	}

	public String getGmtCreateEndStr() {
		return gmtCreateEndStr;
	}

	public void setGmtCreateEndStr(String gmtCreateEndStr) {
		this.gmtCreateEndStr = gmtCreateEndStr;
	}

	public String getGmtCreateStartStr() {
		return gmtCreateStartStr;
	}

	public void setGmtCreateStartStr(String gmtCreateStartStr) {
		this.gmtCreateStartStr = gmtCreateStartStr;
	}
	
	public String brands ;
	public String bigType ;
	public String smallType ;

	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType) {
		this.bigType = bigType;
	}
	public String getSmallType() {
		return smallType;
	}
	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}
	
	
	
}
