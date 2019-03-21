package com.sandu.pay.order.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: PayRefund.java
 * @Package com.sandu.pay.model
 * @Description:支付-支付退款
 * @createAuthor pandajun
 * @CreateDate 2016-09-22 14:41:47
 * @version V1.0
 */
public class PayRefund extends Mapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	/** 凭证ID **/
	private Integer orderId;
	/** 用户ID **/
	private Integer userId;
	/** 参考号 **/
	private String refId;
	/** 退款号 **/
	private String refundNo;
	/** 订单总金额，单位为分 **/
	private Integer totalFee;
	/** 退款原因说明 **/
	private String refundReason;
	/** 退款状态 **/
	private String refundState;
	/** 审计状态 **/
	private Integer auditState;
	/** 审核意见 **/
	private String auditOpinion;
	/** 结果详情 **/
	private String resultDetails;
	/** 退款时间 **/
	private Date refundDate;
	/** 系统编码 **/
	private String sysCode;
	/** 创建者 **/
	private String creator;
	/** 创建时间 **/
	private Date gmtCreate;
	/** 修改人 **/
	private String modifier;
	/** 修改时间 **/
	private Date gmtModified;
	/** 是否删除 **/
	private Integer isDeleted;
	/** 字符备用1 **/
	private String att1;
	/** 字符备用2 **/
	private String att2;
	/** 整数备用1 **/
	private Integer numa1;
	/** 整数备用2 **/
	private Integer numa2;
	/** 备注 **/
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getResultDetails() {
		return resultDetails;
	}

	public void setResultDetails(String resultDetails) {
		this.resultDetails = resultDetails;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
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

	public String getAtt1() {
		return att1;
	}

	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	public String getAtt2() {
		return att2;
	}

	public void setAtt2(String att2) {
		this.att2 = att2;
	}

	public Integer getNuma1() {
		return numa1;
	}

	public void setNuma1(Integer numa1) {
		this.numa1 = numa1;
	}

	public Integer getNuma2() {
		return numa2;
	}

	public void setNuma2(Integer numa2) {
		this.numa2 = numa2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		PayRefund other = (PayRefund) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getOrderId() == null ? other.getOrderId() == null
						: this.getOrderId().equals(other.getOrderId()))
				&& (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
				&& (this.getRefId() == null ? other.getRefId() == null : this.getRefId().equals(other.getRefId()))
				&& (this.getRefundNo() == null ? other.getRefundNo() == null
						: this.getRefundNo().equals(other.getRefundNo()))
				&& (this.getTotalFee() == null ? other.getTotalFee() == null
						: this.getTotalFee().equals(other.getTotalFee()))
				&& (this.getRefundReason() == null ? other.getRefundReason() == null
						: this.getRefundReason().equals(other.getRefundReason()))
				&& (this.getRefundState() == null ? other.getRefundState() == null
						: this.getRefundState().equals(other.getRefundState()))
				&& (this.getAuditState() == null ? other.getAuditState() == null
						: this.getAuditState().equals(other.getAuditState()))
				&& (this.getAuditOpinion() == null ? other.getAuditOpinion() == null
						: this.getAuditOpinion().equals(other.getAuditOpinion()))
				&& (this.getResultDetails() == null ? other.getResultDetails() == null
						: this.getResultDetails().equals(other.getResultDetails()))
				&& (this.getRefundDate() == null ? other.getRefundDate() == null
						: this.getRefundDate().equals(other.getRefundDate()))
				&& (this.getSysCode() == null ? other.getSysCode() == null
						: this.getSysCode().equals(other.getSysCode()))
				&& (this.getCreator() == null ? other.getCreator() == null
						: this.getCreator().equals(other.getCreator()))
				&& (this.getGmtCreate() == null ? other.getGmtCreate() == null
						: this.getGmtCreate().equals(other.getGmtCreate()))
				&& (this.getModifier() == null ? other.getModifier() == null
						: this.getModifier().equals(other.getModifier()))
				&& (this.getGmtModified() == null ? other.getGmtModified() == null
						: this.getGmtModified().equals(other.getGmtModified()))
				&& (this.getIsDeleted() == null ? other.getIsDeleted() == null
						: this.getIsDeleted().equals(other.getIsDeleted()))
				&& (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
				&& (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
				&& (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
				&& (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
				&& (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
		result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime * result + ((getRefId() == null) ? 0 : getRefId().hashCode());
		result = prime * result + ((getRefundNo() == null) ? 0 : getRefundNo().hashCode());
		result = prime * result + ((getTotalFee() == null) ? 0 : getTotalFee().hashCode());
		result = prime * result + ((getRefundReason() == null) ? 0 : getRefundReason().hashCode());
		result = prime * result + ((getRefundState() == null) ? 0 : getRefundState().hashCode());
		result = prime * result + ((getAuditState() == null) ? 0 : getAuditState().hashCode());
		result = prime * result + ((getAuditOpinion() == null) ? 0 : getAuditOpinion().hashCode());
		result = prime * result + ((getResultDetails() == null) ? 0 : getResultDetails().hashCode());
		result = prime * result + ((getRefundDate() == null) ? 0 : getRefundDate().hashCode());
		result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
		result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
		result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
		result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
		result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
		result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
		result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
		result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
		result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
		result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
	}

	/** 获取对象的copy **/
	public PayRefund copy() {
		PayRefund obj = new PayRefund();
		obj.setOrderId(this.orderId);
		obj.setUserId(this.userId);
		obj.setRefId(this.refId);
		obj.setRefundNo(this.refundNo);
		obj.setTotalFee(this.totalFee);
		obj.setRefundReason(this.refundReason);
		obj.setRefundState(this.refundState);
		obj.setAuditState(this.auditState);
		obj.setAuditOpinion(this.auditOpinion);
		obj.setResultDetails(this.resultDetails);
		obj.setRefundDate(this.refundDate);
		obj.setSysCode(this.sysCode);
		obj.setCreator(this.creator);
		obj.setGmtCreate(this.gmtCreate);
		obj.setModifier(this.modifier);
		obj.setGmtModified(this.gmtModified);
		obj.setIsDeleted(this.isDeleted);
		obj.setAtt1(this.att1);
		obj.setAtt2(this.att2);
		obj.setNuma1(this.numa1);
		obj.setNuma2(this.numa2);
		obj.setRemark(this.remark);

		return obj;
	}

	/** 获取对象的map **/
	public Map toMap() {
		Map map = new HashMap();
		map.put("orderId", this.orderId);
		map.put("userId", this.userId);
		map.put("refId", this.refId);
		map.put("refundNo", this.refundNo);
		map.put("totalFee", this.totalFee);
		map.put("refundReason", this.refundReason);
		map.put("refundState", this.refundState);
		map.put("auditState", this.auditState);
		map.put("auditOpinion", this.auditOpinion);
		map.put("resultDetails", this.resultDetails);
		map.put("refundDate", this.refundDate);
		map.put("sysCode", this.sysCode);
		map.put("creator", this.creator);
		map.put("gmtCreate", this.gmtCreate);
		map.put("modifier", this.modifier);
		map.put("gmtModified", this.gmtModified);
		map.put("isDeleted", this.isDeleted);
		map.put("att1", this.att1);
		map.put("att2", this.att2);
		map.put("numa1", this.numa1);
		map.put("numa2", this.numa2);
		map.put("remark", this.remark);

		return map;
	}
}
