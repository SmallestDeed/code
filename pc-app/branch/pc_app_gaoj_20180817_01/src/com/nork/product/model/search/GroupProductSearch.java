package com.nork.product.model.search;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProduct;
import com.nork.productprops.model.ProductProps;
import com.nork.productprops.model.ProductPropsSimple;

/**   
 * @Title: GroupProductSearch.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品组合主表查询对象
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
public class GroupProductSearch extends  GroupProduct implements Serializable{
private static final long serialVersionUID = 1L;
	/**  组合编码-模糊查询  **/
	private String sch_GroupCode_;
	/**  组合编码-左模糊查询  **/
	private String sch_GroupCode;
	/**  组合编码-右模糊查询  **/
	private String schGroupCode_;
	/**  组合编码-区间查询-开始字符串  **/
	private String groupCodeStart;
	/**  组合编码-区间查询-结束字符串  **/
	private String groupCodeEnd;
	/**  组合名-模糊查询  **/
	private String sch_GroupName_;
	/**  组合名-左模糊查询  **/
	private String sch_GroupName;
	/**  组合名-右模糊查询  **/
	private String schGroupName_;
	/**  组合名-区间查询-开始字符串  **/
	private String groupNameStart;
	/**  组合名-区间查询-结束字符串  **/
	private String groupNameEnd;
	/**  组合编码-模糊查询  **/
	private String sch_Code_;
	/**  组合编码-左模糊查询  **/
	private String sch_Code;
	/**  组合编码-右模糊查询  **/
	private String schCode_;
	/**  组合编码-区间查询-开始字符串  **/
	private String codeStart;
	/**  组合编码-区间查询-结束字符串  **/
	private String codeEnd;
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
	/**  字符备用1-模糊查询  **/
	private String sch_Att1_;
	/**  字符备用1-左模糊查询  **/
	private String sch_Att1;
	/**  字符备用1-右模糊查询  **/
	private String schAtt1_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String att1Start;
	/**  字符备用1-区间查询-结束字符串  **/
	private String att1End;
	/**  字符备用2-模糊查询  **/
	private String sch_Att2_;
	/**  字符备用2-左模糊查询  **/
	private String sch_Att2;
	/**  字符备用2-右模糊查询  **/
	private String schAtt2_;
	/**  字符备用2-区间查询-开始字符串  **/
	private String att2Start;
	/**  字符备用2-区间查询-结束字符串  **/
	private String att2End;
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
	/** 产品ID **/
	private Integer productId;
	private Integer state;
	private List<Integer> states;
	private String categoryCode;
	/*产品houseTypeValue过滤条件*/
	private List<String> houseTypeList;

	/**longCode分解第一级code(查询条件)*/
	private String firstStageCode;
	/**longCode分解第二级code(查询条件)*/
	private String secondStageCode;
	/**longCode分解第三级code(查询条件)*/
	private String thirdStageCode;
	/**longCode分解第四级code(查询条件)*/
	private String fourthStageCode;
	/**longCode分解第五级code(查询条件)*/
	private String fifthStageCode;

	/*撞着该用户授权码的品牌*/
	String [] brandIdList;
	
	private List<Integer> groupIds;

	/**
	 * 查询条件:主产品过滤属性
	 */
	private List<ProductPropsSimple> productPropsSimpleFilterList;

	
	/**
	 * 组合搜索授权码过滤条件
	 */
	private List<BaseProduct> baseProductList;
	
	/**
	 * 授权码过滤查不出产品是否显示无品牌
	 */
	private boolean isShowWu = false;
	
	/**
	 * 无品牌id(用于单品搜索,查询条件)
	 */
	private Integer brandIdWuPinPai;
	
	public List<BaseProduct> getBaseProductList() {
		return baseProductList;
	}

	public void setBaseProductList(List<BaseProduct> baseProductList) {
		this.baseProductList = baseProductList;
	}

	public boolean isShowWu() {
		return isShowWu;
	}

	public void setShowWu(boolean isShowWu) {
		this.isShowWu = isShowWu;
	}

	public Integer getBrandIdWuPinPai() {
		return brandIdWuPinPai;
	}

	public void setBrandIdWuPinPai(Integer brandIdWuPinPai) {
		this.brandIdWuPinPai = brandIdWuPinPai;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}


	public List<ProductPropsSimple> getProductPropsSimpleFilterList() {
		return productPropsSimpleFilterList;
	}

	public void setProductPropsSimpleFilterList(List<ProductPropsSimple> productPropsSimpleFilterList) {
		this.productPropsSimpleFilterList = productPropsSimpleFilterList;
	}


	public String[] getBrandIdList() {
		return brandIdList;
	}

	public void setBrandIdList(String[] brandIdList) {
		this.brandIdList = brandIdList;
	}

	public String getFirstStageCode() {
		return firstStageCode;
	}

	public void setFirstStageCode(String firstStageCode) {
		this.firstStageCode = firstStageCode;
	}

	public String getSecondStageCode() {
		return secondStageCode;
	}

	public void setSecondStageCode(String secondStageCode) {
		this.secondStageCode = secondStageCode;
	}

	public String getThirdStageCode() {
		return thirdStageCode;
	}

	public void setThirdStageCode(String thirdStageCode) {
		this.thirdStageCode = thirdStageCode;
	}

	public String getFourthStageCode() {
		return fourthStageCode;
	}

	public void setFourthStageCode(String fourthStageCode) {
		this.fourthStageCode = fourthStageCode;
	}

	public String getFifthStageCode() {
		return fifthStageCode;
	}

	public void setFifthStageCode(String fifthStageCode) {
		this.fifthStageCode = fifthStageCode;
	}
	
	/**1查询结构组合 2查询普通组合*/
	private Integer structureState;
	
	
	private String productTypeValue;
	
	private String smallTypeValue;
	
	/**
	 * 排序属性集合
	 */
	private List<ProductProps> propsOrderList;
	
	/**
	 * 产品多个属性条件
	 */
	private List<String> attributeConditionList;
	/**
	 * 产品属性数量
	 */
	private Integer attributeConditionSize;
	
	public List<String> getAttributeConditionList() {
		return attributeConditionList;
	}

	public void setAttributeConditionList(List<String> attributeConditionList) {
		this.attributeConditionList = attributeConditionList;
	}

	public Integer getAttributeConditionSize() {
		return attributeConditionSize;
	}

	public void setAttributeConditionSize(Integer attributeConditionSize) {
		this.attributeConditionSize = attributeConditionSize;
	}

	public List<ProductProps> getPropsOrderList() {
		return propsOrderList;
	}

	public void setPropsOrderList(List<ProductProps> propsOrderList) {
		this.propsOrderList = propsOrderList;
	}

	public String getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	 

	public String getSmallTypeValue() {
		return smallTypeValue;
	}

	public void setSmallTypeValue(String smallTypeValue) {
		this.smallTypeValue = smallTypeValue;
	}

	public Integer getStructureState() {
		return structureState;
	}

	public void setStructureState(Integer structureState) {
		this.structureState = structureState;
	}

	public List<String> getHouseTypeList() {
		return houseTypeList;
	}

	public void setHouseTypeList(List<String> houseTypeList) {
		this.houseTypeList = houseTypeList;
	}
	public  String getSch_GroupCode_() {
		return sch_GroupCode_;
	}
	public void setSch_GroupCode_(String sch_GroupCode_){
		this.sch_GroupCode_ = sch_GroupCode_;
	}
	public  String getSch_GroupCode() {
		return sch_GroupCode;
	}
	public void setSch_GroupCode(String sch_GroupCode){
		this.sch_GroupCode = sch_GroupCode;
	}
	public  String getSchGroupCode_() {
		return schGroupCode_;
	}
	public void setSchGroupCode_(String schGroupCode_){
		this.schGroupCode_ = schGroupCode_;
	}
	public  String getGroupCodeStart() {
		return groupCodeStart;
	}
	public void setGroupCodeStart(String groupCodeStart){
		this.groupCodeStart = groupCodeStart;
	}
	public  String getGroupCodeEnd() {
		return groupCodeEnd;
	}
	public void setGroupCodeEnd(String groupCodeEnd){
		this.groupCodeEnd = groupCodeEnd;
	}
	public  String getSch_GroupName_() {
		return sch_GroupName_;
	}
	public void setSch_GroupName_(String sch_GroupName_){
		this.sch_GroupName_ = sch_GroupName_;
	}
	public  String getSch_GroupName() {
		return sch_GroupName;
	}
	public void setSch_GroupName(String sch_GroupName){
		this.sch_GroupName = sch_GroupName;
	}
	public  String getSchGroupName_() {
		return schGroupName_;
	}
	public void setSchGroupName_(String schGroupName_){
		this.schGroupName_ = schGroupName_;
	}
	public  String getGroupNameStart() {
		return groupNameStart;
	}
	public void setGroupNameStart(String groupNameStart){
		this.groupNameStart = groupNameStart;
	}
	public  String getGroupNameEnd() {
		return groupNameEnd;
	}
	public void setGroupNameEnd(String groupNameEnd){
		this.groupNameEnd = groupNameEnd;
	}
	public  String getSch_Code_() {
		return sch_Code_;
	}
	public void setSch_Code_(String sch_Code_){
		this.sch_Code_ = sch_Code_;
	}
	public  String getSch_Code() {
		return sch_Code;
	}
	public void setSch_Code(String sch_Code){
		this.sch_Code = sch_Code;
	}
	public  String getSchCode_() {
		return schCode_;
	}
	public void setSchCode_(String schCode_){
		this.schCode_ = schCode_;
	}
	public  String getCodeStart() {
		return codeStart;
	}
	public void setCodeStart(String codeStart){
		this.codeStart = codeStart;
	}
	public  String getCodeEnd() {
		return codeEnd;
	}
	public void setCodeEnd(String codeEnd){
		this.codeEnd = codeEnd;
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
	public  String getSch_Att1_() {
		return sch_Att1_;
	}
	public void setSch_Att1_(String sch_Att1_){
		this.sch_Att1_ = sch_Att1_;
	}
	public  String getSch_Att1() {
		return sch_Att1;
	}
	public void setSch_Att1(String sch_Att1){
		this.sch_Att1 = sch_Att1;
	}
	public  String getSchAtt1_() {
		return schAtt1_;
	}
	public void setSchAtt1_(String schAtt1_){
		this.schAtt1_ = schAtt1_;
	}
	public  String getAtt1Start() {
		return att1Start;
	}
	public void setAtt1Start(String att1Start){
		this.att1Start = att1Start;
	}
	public  String getAtt1End() {
		return att1End;
	}
	public void setAtt1End(String att1End){
		this.att1End = att1End;
	}
	public  String getSch_Att2_() {
		return sch_Att2_;
	}
	public void setSch_Att2_(String sch_Att2_){
		this.sch_Att2_ = sch_Att2_;
	}
	public  String getSch_Att2() {
		return sch_Att2;
	}
	public void setSch_Att2(String sch_Att2){
		this.sch_Att2 = sch_Att2;
	}
	public  String getSchAtt2_() {
		return schAtt2_;
	}
	public void setSchAtt2_(String schAtt2_){
		this.schAtt2_ = schAtt2_;
	}
	public  String getAtt2Start() {
		return att2Start;
	}
	public void setAtt2Start(String att2Start){
		this.att2Start = att2Start;
	}
	public  String getAtt2End() {
		return att2End;
	}
	public void setAtt2End(String att2End){
		this.att2End = att2End;
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
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public List<Integer> getStates() {
		return states;
	}
	public void setStates(List<Integer> states) {
		this.states = states;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
