package com.sandu.pay.mgrRecharge.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**   
 * @Title: MgrRechargeLog.java 
 * @Package com.sandu.mgrRecharge.model
 * @Description:日常工作-充值记录
 * @createAuthor pandajun 
 * @CreateDate 2017-03-26 05:15:26
 * @version V1.0   
 */
public class MgrRechargeLog extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  用户id  **/
	private Integer userId;
	/**  充值金额(元)  **/
	private Double rechargeAmount;
	
	/**  实时余额(元)  **/
	private Double currentBalance;
	
	/**  充值类型  **/
	private Integer rechargeType;
	/**  充值状态  **/
	private Integer rechargeStatus;
	/**  充值操作人员id  **/
	private Integer administratorId;	
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
	/**被充值人员用户名*/
	private String username;
	/**充值类型(name)*/
	private String rechargeTypeInfo;
	/**充值状态(name)*/
	private String rechargeStatusInfo;
	/**充值人员姓名*/
	private String administratorName;
	/**被充值人员手机号*/
	private String mobile;
	/**
	 * 所属平台分类(2b/2c/sandu)
	 */
	private String platformBussinessType;

	public String getPlatformBussinessType() {
		return platformBussinessType;
	}

	public void setPlatformBussinessType(String platformBussinessType) {
		this.platformBussinessType = platformBussinessType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	public String getCurrentBalanceForShow() {
//		if(currentBalance == null){
//			return "";
//		}
//		System.out.println("" + currentBalance.setScale(0, BigDecimal.ROUND_HALF_UP));
//		return "" + currentBalance.setScale(0, BigDecimal.ROUND_HALF_UP);
//	}

	public String getAdministratorName() {
		return administratorName;
	}

	public void setAdministratorName(String administratorName) {
		this.administratorName = administratorName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRechargeTypeInfo() {
		return rechargeTypeInfo;
	}

	public void setRechargeTypeInfo(String rechargeTypeInfo) {
		this.rechargeTypeInfo = rechargeTypeInfo;
	}

	public String getRechargeStatusInfo() {
		return rechargeStatusInfo;
	}

	public void setRechargeStatusInfo(String rechargeStatusInfo) {
		this.rechargeStatusInfo = rechargeStatusInfo;
	}

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	public  Integer getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(Integer rechargeType){
		this.rechargeType = rechargeType;
	}
	public  Integer getRechargeStatus() {
		return rechargeStatus;
	}
	public void setRechargeStatus(Integer rechargeStatus){
		this.rechargeStatus = rechargeStatus;
	}
	public  Integer getAdministratorId() {
		return administratorId;
	}
	public void setAdministratorId(Integer administratorId){
		this.administratorId = administratorId;
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
        MgrRechargeLog other = (MgrRechargeLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRechargeAmount() == null ? other.getRechargeAmount() == null : this.getRechargeAmount().equals(other.getRechargeAmount()))
            && (this.getCurrentBalance() == null ? other.getCurrentBalance() == null : this.getCurrentBalance().equals(other.getCurrentBalance()))
            && (this.getRechargeType() == null ? other.getRechargeType() == null : this.getRechargeType().equals(other.getRechargeType()))
            && (this.getRechargeStatus() == null ? other.getRechargeStatus() == null : this.getRechargeStatus().equals(other.getRechargeStatus()))
            && (this.getAdministratorId() == null ? other.getAdministratorId() == null : this.getAdministratorId().equals(other.getAdministratorId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
            && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRechargeAmount() == null) ? 0 : getRechargeAmount().hashCode());
        result = prime * result + ((getCurrentBalance() == null) ? 0 : getCurrentBalance().hashCode());
        result = prime * result + ((getRechargeType() == null) ? 0 : getRechargeType().hashCode());
        result = prime * result + ((getRechargeStatus() == null) ? 0 : getRechargeStatus().hashCode());
        result = prime * result + ((getAdministratorId() == null) ? 0 : getAdministratorId().hashCode());
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
    
    /**获取对象的copy**/
    public MgrRechargeLog copy(){
       MgrRechargeLog obj =  new MgrRechargeLog();
       obj.setUserId(this.userId);
       obj.setRechargeAmount(this.rechargeAmount);
       obj.setCurrentBalance(this.currentBalance);
       obj.setRechargeType(this.rechargeType);
       obj.setRechargeStatus(this.rechargeStatus);
       obj.setAdministratorId(this.administratorId);
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
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("userId",this.userId);
       map.put("rechargeAmount",this.rechargeAmount);
       map.put("currentBalance",this.currentBalance);
       map.put("rechargeType",this.rechargeType);
       map.put("rechargeStatus",this.rechargeStatus);
       map.put("administratorId",this.administratorId);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);

       return map;
    }
    
    /**
     * 交易订单号
     */
    private String orderNo;
    
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	
    
}
