package com.nork.design.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.nork.common.model.Mapper;

/**   
 * @Title: DesignPlanCustomizedProductOrder.java 
 * @Package com.nork.design.model
 * @Description:设计方案-设计方案定制产品订单表
 * @createAuthor pandajun 
 * @CreateDate 2018-11-26 17:46:44
 * @version V1.0   
 */
public class DesignPlanCustomizedProductOrder  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  合作商  **/
	private String partnersName;
	/**  设计方案ID  **/
	private Integer planId;
	/**  外部订单Id  **/
	private String exteriorOrderId;
	/**  订单号  **/
	private String orderCode;
	/**  客户名称  **/
	private String clientName;
	/**  合同号  **/
	private String pactNo;
	/**  公司ID  **/
	private Integer companyId;
	/**  公司名称  **/
	private String companyName;
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
	/**  合同号相同最新时间 排序用  **/
	private Date pactNoSortTime;
	/**  客户名字相同最新时间 排序用  **/
	private Date clientNameSortTime;
	/**  备注  **/
	private String remark;

	public  String getPartnersName() {
		return partnersName;
	}
	public void setPartnersName(String partnersName){
		this.partnersName = partnersName;
	}
	public  Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId){
		this.planId = planId;
	}
	public  String getExteriorOrderId() {
		return exteriorOrderId;
	}
	public void setExteriorOrderId(String exteriorOrderId){
		this.exteriorOrderId = exteriorOrderId;
	}
	public  String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode){
		this.orderCode = orderCode;
	}
	public  String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName){
		this.clientName = clientName;
	}
	public  String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo){
		this.pactNo = pactNo;
	}
	public  Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId){
		this.companyId = companyId;
	}
	public  String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName){
		this.companyName = companyName;
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
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}

	public Date getPactNoSortTime() {
		return pactNoSortTime;
	}

	public void setPactNoSortTime(Date pactNoSortTime) {
		this.pactNoSortTime = pactNoSortTime;
	}

	public Date getClientNameSortTime() {
		return clientNameSortTime;
	}

	public void setClientNameSortTime(Date clientNameSortTime) {
		this.clientNameSortTime = clientNameSortTime;
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
        DesignPlanCustomizedProductOrder other = (DesignPlanCustomizedProductOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPartnersName() == null ? other.getPartnersName() == null : this.getPartnersName().equals(other.getPartnersName()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getExteriorOrderId() == null ? other.getExteriorOrderId() == null : this.getExteriorOrderId().equals(other.getExteriorOrderId()))
            && (this.getOrderCode() == null ? other.getOrderCode() == null : this.getOrderCode().equals(other.getOrderCode()))
            && (this.getClientName() == null ? other.getClientName() == null : this.getClientName().equals(other.getClientName()))
            && (this.getPactNo() == null ? other.getPactNo() == null : this.getPactNo().equals(other.getPactNo()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getCompanyName() == null ? other.getCompanyName() == null : this.getCompanyName().equals(other.getCompanyName()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPartnersName() == null) ? 0 : getPartnersName().hashCode());
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getExteriorOrderId() == null) ? 0 : getExteriorOrderId().hashCode());
        result = prime * result + ((getOrderCode() == null) ? 0 : getOrderCode().hashCode());
        result = prime * result + ((getClientName() == null) ? 0 : getClientName().hashCode());
        result = prime * result + ((getPactNo() == null) ? 0 : getPactNo().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getCompanyName() == null) ? 0 : getCompanyName().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public DesignPlanCustomizedProductOrder copy(){
       DesignPlanCustomizedProductOrder obj =  new DesignPlanCustomizedProductOrder();
       obj.setPartnersName(this.partnersName);
       obj.setPlanId(this.planId);
       obj.setExteriorOrderId(this.exteriorOrderId);
       obj.setOrderCode(this.orderCode);
       obj.setClientName(this.clientName);
       obj.setPactNo(this.pactNo);
       obj.setCompanyId(this.companyId);
       obj.setCompanyName(this.companyName);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("partnersName",this.partnersName);
       map.put("planId",this.planId);
       map.put("exteriorOrderId",this.exteriorOrderId);
       map.put("orderCode",this.orderCode);
       map.put("clientName",this.clientName);
       map.put("pactNo",this.pactNo);
       map.put("companyId",this.companyId);
       map.put("companyName",this.companyName);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("remark",this.remark);

       return map;
    }
}
