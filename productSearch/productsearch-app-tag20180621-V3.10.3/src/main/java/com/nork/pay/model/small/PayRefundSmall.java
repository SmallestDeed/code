package com.nork.pay.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: PayRefund.java 
 * @Package com.nork.pay.model.small
 * @Description:支付-支付退款
 * @createAuthor pandajun 
 * @CreateDate 2016-09-22 14:41:47
 * @version V1.0   
 */
public class PayRefundSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  凭证ID  **/
	private Integer orderId;
	/**  用户ID  **/
	private Integer userId;
	/**  参考号  **/
	private String refId;
	/**  退款号  **/
	private String refundNo;
	/**  订单总金额，单位为分  **/
	private Integer totalFee;
	/**  退款原因说明  **/
	private String refundReason;
	/**  退款状态  **/
	private String refundState;
	/**  审计状态  **/
	private Integer auditState;
	/**  审核意见  **/
	private String auditOpinion;
	/**  结果详情  **/
	private String resultDetails;
	/**  退款时间  **/
	private Date refundDate;
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
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;

	public  Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId){
		this.orderId = orderId;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getRefId() {
		return refId;
	}
	public void setRefId(String refId){
		this.refId = refId;
	}
	public  String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo){
		this.refundNo = refundNo;
	}
	public  Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee){
		this.totalFee = totalFee;
	}
	public  String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason){
		this.refundReason = refundReason;
	}
	public  String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState){
		this.refundState = refundState;
	}
	public  Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState){
		this.auditState = auditState;
	}
	public  String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion){
		this.auditOpinion = auditOpinion;
	}
	public  String getResultDetails() {
		return resultDetails;
	}
	public void setResultDetails(String resultDetails){
		this.resultDetails = resultDetails;
	}
	public  Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate){
		this.refundDate = refundDate;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1){
		this.att1 = att1;
	}
	public  String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2){
		this.att2 = att2;
	}
	public  Integer getNuma1() {
		return numa1;
	}
	public void setNuma1(Integer numa1){
		this.numa1 = numa1;
	}
	public  Integer getNuma2() {
		return numa2;
	}
	public void setNuma2(Integer numa2){
		this.numa2 = numa2;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


}
