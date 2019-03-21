package com.sandu.pay.order.model.search;

import com.sandu.pay.order.model.PayOrder;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: PayOrderSearch.java 
 * @Package com.sandu.pay.model
 * @Description:支付-支出凭证查询对象
 * @createAuthor pandajun 
 * @CreateDate 2016-09-19 16:17:40
 * @version V1.0   
 */
public class PayOrderSearch extends PayOrder implements Serializable{
private static final long serialVersionUID = 1L;
    private String beginDate;
    private String endDate;
	/**  产品类型-模糊查询  **/
	private String sch_ProductType_;
	/**  产品类型-左模糊查询  **/
	private String sch_ProductType;
	/**  产品类型-右模糊查询  **/
	private String schProductType_;
	/**  产品类型-区间查询-开始字符串  **/
	private String productTypeStart;
	/**  产品类型-区间查询-结束字符串  **/
	private String productTypeEnd;
	/**  订单号-模糊查询  **/
	private String sch_OrderNo_;
	/**  订单号-左模糊查询  **/
	private String sch_OrderNo;
	/**  订单号-右模糊查询  **/
	private String schOrderNo_;
	/**  订单号-区间查询-开始字符串  **/
	private String orderNoStart;
	/**  订单号-区间查询-结束字符串  **/
	private String orderNoEnd;
	/**  支出类型-模糊查询  **/
	private String sch_PayType_;
	/**  支出类型-左模糊查询  **/
	private String sch_PayType;
	/**  支出类型-右模糊查询  **/
	private String schPayType_;
	/**  支出类型-区间查询-开始字符串  **/
	private String payTypeStart;
	/**  支出类型-区间查询-结束字符串  **/
	private String payTypeEnd;
	/**  支出状态-模糊查询  **/
	private String sch_PayState_;
	/**  支出状态-左模糊查询  **/
	private String sch_PayState;
	/**  支出状态-右模糊查询  **/
	private String schPayState_;
	/**  支出状态-区间查询-开始字符串  **/
	private String payStateStart;
	/**  支出状态-区间查询-结束字符串  **/
	private String payStateEnd;
	/**  预付id-模糊查询  **/
	private String sch_PrepayId_;
	/**  预付id-左模糊查询  **/
	private String sch_PrepayId;
	/**  预付id-右模糊查询  **/
	private String schPrepayId_;
	/**  预付id-区间查询-开始字符串  **/
	private String prepayIdStart;
	/**  预付id-区间查询-结束字符串  **/
	private String prepayIdEnd;
	/**  编码路径-模糊查询  **/
	private String sch_CodeUrl_;
	/**  编码路径-左模糊查询  **/
	private String sch_CodeUrl;
	/**  编码路径-右模糊查询  **/
	private String schCodeUrl_;
	/**  编码路径-区间查询-开始字符串  **/
	private String codeUrlStart;
	/**  编码路径-区间查询-结束字符串  **/
	private String codeUrlEnd;
	/**  参考id-模糊查询  **/
	private String sch_RefId_;
	/**  参考id-左模糊查询  **/
	private String sch_RefId;
	/**  参考id-右模糊查询  **/
	private String schRefId_;
	/**  参考id-区间查询-开始字符串  **/
	private String refIdStart;
	/**  参考id-区间查询-结束字符串  **/
	private String refIdEnd;
	/**  公开Id-模糊查询  **/
	private String sch_OpenId_;
	/**  公开Id-左模糊查询  **/
	private String sch_OpenId;
	/**  公开Id-右模糊查询  **/
	private String schOpenId_;
	/**  公开Id-区间查询-开始字符串  **/
	private String openIdStart;
	/**  公开Id-区间查询-结束字符串  **/
	private String openIdEnd;
	/**  凭证时间-区间查询-开始时间  **/
	private Date orderDateStart;
	/**  凭证时间-区间查询-结束时间  **/
	private Date orderDateEnd;
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
	/**搜索条件,小于orderDate*/
	private Date orderDateLess;
	/**平台编码*/
	private String platformCode;

	/**
	 * 所属平台分类
	 */
	private String platformBussinessType;

	/**
	 * 经销商账号组id
	 */
	private Integer franchiserGroupId;

	public String getPlatformBussinessType() {
		return platformBussinessType;
	}

	public void setPlatformBussinessType(String platformBussinessType) {
		this.platformBussinessType = platformBussinessType;
	}

	public Integer getFranchiserGroupId() {
		return franchiserGroupId;
	}

	public void setFranchiserGroupId(Integer franchiserGroupId) {
		this.franchiserGroupId = franchiserGroupId;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public Date getOrderDateLess() {
		return orderDateLess;
	}
	public void setOrderDateLess(Date orderDateLess) {
		this.orderDateLess = orderDateLess;
	}
	public  String getSch_ProductType_() {
		return sch_ProductType_;
	}
	public void setSch_ProductType_(String sch_ProductType_){
		this.sch_ProductType_ = sch_ProductType_;
	}
	public  String getSch_ProductType() {
		return sch_ProductType;
	}
	public void setSch_ProductType(String sch_ProductType){
		this.sch_ProductType = sch_ProductType;
	}
	public  String getSchProductType_() {
		return schProductType_;
	}
	public void setSchProductType_(String schProductType_){
		this.schProductType_ = schProductType_;
	}
	public  String getProductTypeStart() {
		return productTypeStart;
	}
	public void setProductTypeStart(String productTypeStart){
		this.productTypeStart = productTypeStart;
	}
	public  String getProductTypeEnd() {
		return productTypeEnd;
	}
	public void setProductTypeEnd(String productTypeEnd){
		this.productTypeEnd = productTypeEnd;
	}
	public  String getSch_OrderNo_() {
		return sch_OrderNo_;
	}
	public void setSch_OrderNo_(String sch_OrderNo_){
		this.sch_OrderNo_ = sch_OrderNo_;
	}
	public  String getSch_OrderNo() {
		return sch_OrderNo;
	}
	public void setSch_OrderNo(String sch_OrderNo){
		this.sch_OrderNo = sch_OrderNo;
	}
	public  String getSchOrderNo_() {
		return schOrderNo_;
	}
	public void setSchOrderNo_(String schOrderNo_){
		this.schOrderNo_ = schOrderNo_;
	}
	public  String getOrderNoStart() {
		return orderNoStart;
	}
	public void setOrderNoStart(String orderNoStart){
		this.orderNoStart = orderNoStart;
	}
	public  String getOrderNoEnd() {
		return orderNoEnd;
	}
	public void setOrderNoEnd(String orderNoEnd){
		this.orderNoEnd = orderNoEnd;
	}
	public  String getSch_PayType_() {
		return sch_PayType_;
	}
	public void setSch_PayType_(String sch_PayType_){
		this.sch_PayType_ = sch_PayType_;
	}
	public  String getSch_PayType() {
		return sch_PayType;
	}
	public void setSch_PayType(String sch_PayType){
		this.sch_PayType = sch_PayType;
	}
	public  String getSchPayType_() {
		return schPayType_;
	}
	public void setSchPayType_(String schPayType_){
		this.schPayType_ = schPayType_;
	}
	public  String getPayTypeStart() {
		return payTypeStart;
	}
	public void setPayTypeStart(String payTypeStart){
		this.payTypeStart = payTypeStart;
	}
	public  String getPayTypeEnd() {
		return payTypeEnd;
	}
	public void setPayTypeEnd(String payTypeEnd){
		this.payTypeEnd = payTypeEnd;
	}
	public  String getSch_PayState_() {
		return sch_PayState_;
	}
	public void setSch_PayState_(String sch_PayState_){
		this.sch_PayState_ = sch_PayState_;
	}
	public  String getSch_PayState() {
		return sch_PayState;
	}
	public void setSch_PayState(String sch_PayState){
		this.sch_PayState = sch_PayState;
	}
	public  String getSchPayState_() {
		return schPayState_;
	}
	public void setSchPayState_(String schPayState_){
		this.schPayState_ = schPayState_;
	}
	public  String getPayStateStart() {
		return payStateStart;
	}
	public void setPayStateStart(String payStateStart){
		this.payStateStart = payStateStart;
	}
	public  String getPayStateEnd() {
		return payStateEnd;
	}
	public void setPayStateEnd(String payStateEnd){
		this.payStateEnd = payStateEnd;
	}
	public  String getSch_PrepayId_() {
		return sch_PrepayId_;
	}
	public void setSch_PrepayId_(String sch_PrepayId_){
		this.sch_PrepayId_ = sch_PrepayId_;
	}
	public  String getSch_PrepayId() {
		return sch_PrepayId;
	}
	public void setSch_PrepayId(String sch_PrepayId){
		this.sch_PrepayId = sch_PrepayId;
	}
	public  String getSchPrepayId_() {
		return schPrepayId_;
	}
	public void setSchPrepayId_(String schPrepayId_){
		this.schPrepayId_ = schPrepayId_;
	}
	public  String getPrepayIdStart() {
		return prepayIdStart;
	}
	public void setPrepayIdStart(String prepayIdStart){
		this.prepayIdStart = prepayIdStart;
	}
	public  String getPrepayIdEnd() {
		return prepayIdEnd;
	}
	public void setPrepayIdEnd(String prepayIdEnd){
		this.prepayIdEnd = prepayIdEnd;
	}
	public  String getSch_CodeUrl_() {
		return sch_CodeUrl_;
	}
	public void setSch_CodeUrl_(String sch_CodeUrl_){
		this.sch_CodeUrl_ = sch_CodeUrl_;
	}
	public  String getSch_CodeUrl() {
		return sch_CodeUrl;
	}
	public void setSch_CodeUrl(String sch_CodeUrl){
		this.sch_CodeUrl = sch_CodeUrl;
	}
	public  String getSchCodeUrl_() {
		return schCodeUrl_;
	}
	public void setSchCodeUrl_(String schCodeUrl_){
		this.schCodeUrl_ = schCodeUrl_;
	}
	public  String getCodeUrlStart() {
		return codeUrlStart;
	}
	public void setCodeUrlStart(String codeUrlStart){
		this.codeUrlStart = codeUrlStart;
	}
	public  String getCodeUrlEnd() {
		return codeUrlEnd;
	}
	public void setCodeUrlEnd(String codeUrlEnd){
		this.codeUrlEnd = codeUrlEnd;
	}
	public  String getSch_RefId_() {
		return sch_RefId_;
	}
	public void setSch_RefId_(String sch_RefId_){
		this.sch_RefId_ = sch_RefId_;
	}
	public  String getSch_RefId() {
		return sch_RefId;
	}
	public void setSch_RefId(String sch_RefId){
		this.sch_RefId = sch_RefId;
	}
	public  String getSchRefId_() {
		return schRefId_;
	}
	public void setSchRefId_(String schRefId_){
		this.schRefId_ = schRefId_;
	}
	public  String getRefIdStart() {
		return refIdStart;
	}
	public void setRefIdStart(String refIdStart){
		this.refIdStart = refIdStart;
	}
	public  String getRefIdEnd() {
		return refIdEnd;
	}
	public void setRefIdEnd(String refIdEnd){
		this.refIdEnd = refIdEnd;
	}
	public  String getSch_OpenId_() {
		return sch_OpenId_;
	}
	public void setSch_OpenId_(String sch_OpenId_){
		this.sch_OpenId_ = sch_OpenId_;
	}
	public  String getSch_OpenId() {
		return sch_OpenId;
	}
	public void setSch_OpenId(String sch_OpenId){
		this.sch_OpenId = sch_OpenId;
	}
	public  String getSchOpenId_() {
		return schOpenId_;
	}
	public void setSchOpenId_(String schOpenId_){
		this.schOpenId_ = schOpenId_;
	}
	public  String getOpenIdStart() {
		return openIdStart;
	}
	public void setOpenIdStart(String openIdStart){
		this.openIdStart = openIdStart;
	}
	public  String getOpenIdEnd() {
		return openIdEnd;
	}
	public void setOpenIdEnd(String openIdEnd){
		this.openIdEnd = openIdEnd;
	}
	public  Date getOrderDateStart() {
		return orderDateStart;
	}
	public void setOrderDateStart(Date orderDateStart){
		this.orderDateStart = orderDateStart;
	}
	public  Date getOrderDateEnd() {
		return orderDateEnd;
	}
	public void setOrderDateEnd(Date orderDateEnd){
		this.orderDateEnd = orderDateEnd;
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
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
