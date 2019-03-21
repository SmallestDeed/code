package com.nork.design.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.nork.common.model.Mapper;

/**   
 * @Title: DesignPlanCustomizedProduct.java 
 * @Package com.nork.design.model
 * @Description:设计方案-设计方案定制产品表
 * @createAuthor pandajun 
 * @CreateDate 2018-08-28 11:04:09
 * @version V1.0   
 */
public class DesignPlanCustomizedProduct  extends Mapper implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  方案ID  **/
	private Integer planId;
	/**  外部产品ID  **/
	private Integer exteriorProductId;
	/**  外部产品类型  **/
	private String productType;
	/**  模型文件  **/
	private String modelUrl;
	/**  公司标识  **/
	private String companySign;
	/**  店铺标识  **/
	private String shopSign;
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

	public  Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId){
		this.planId = planId;
	}
	public  Integer getExteriorProductId() {
		return exteriorProductId;
	}
	public void setExteriorProductId(Integer exteriorProductId){
		this.exteriorProductId = exteriorProductId;
	}
	public  String getProductType() {
		return productType;
	}
	public void setProductType(String productType){
		this.productType = productType;
	}
	public  String getModelUrl() {
		return modelUrl;
	}
	public void setModelUrl(String modelUrl){
		this.modelUrl = modelUrl;
	}
	public  String getCompanySign() {
		return companySign;
	}
	public void setCompanySign(String companySign){
		this.companySign = companySign;
	}
	public  String getShopSign() {
		return shopSign;
	}
	public void setShopSign(String shopSign){
		this.shopSign = shopSign;
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
        DesignPlanCustomizedProduct other = (DesignPlanCustomizedProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getExteriorProductId() == null ? other.getExteriorProductId() == null : this.getExteriorProductId().equals(other.getExteriorProductId()))
            && (this.getProductType() == null ? other.getProductType() == null : this.getProductType().equals(other.getProductType()))
            && (this.getModelUrl() == null ? other.getModelUrl() == null : this.getModelUrl().equals(other.getModelUrl()))
            && (this.getCompanySign() == null ? other.getCompanySign() == null : this.getCompanySign().equals(other.getCompanySign()))
            && (this.getShopSign() == null ? other.getShopSign() == null : this.getShopSign().equals(other.getShopSign()))
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
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getExteriorProductId() == null) ? 0 : getExteriorProductId().hashCode());
        result = prime * result + ((getProductType() == null) ? 0 : getProductType().hashCode());
        result = prime * result + ((getModelUrl() == null) ? 0 : getModelUrl().hashCode());
        result = prime * result + ((getCompanySign() == null) ? 0 : getCompanySign().hashCode());
        result = prime * result + ((getShopSign() == null) ? 0 : getShopSign().hashCode());
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
    public DesignPlanCustomizedProduct copy(){
       DesignPlanCustomizedProduct obj =  new DesignPlanCustomizedProduct();
       obj.setPlanId(this.planId);
       obj.setExteriorProductId(this.exteriorProductId);
       obj.setProductType(this.productType);
       obj.setModelUrl(this.modelUrl);
       obj.setCompanySign(this.companySign);
       obj.setShopSign(this.shopSign);
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
       map.put("planId",this.planId);
       map.put("exteriorProductId",this.exteriorProductId);
       map.put("productType",this.productType);
       map.put("modelUrl",this.modelUrl);
       map.put("companySign",this.companySign);
       map.put("shopSign",this.shopSign);
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
