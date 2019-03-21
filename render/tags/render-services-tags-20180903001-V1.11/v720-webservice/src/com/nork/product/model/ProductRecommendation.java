package com.nork.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: ProductRecommendation.java 
 * @Package com.nork.product.model
 * @Description:产品-产品推荐
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 14:02:16
 * @version V1.0   
 */
public class ProductRecommendation  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  产品id  **/
	private Integer productId;
	/**  样板间id  **/
	private Integer designTempletId;
	/**  数据来源  **/
	private String dataSources;
	/**  匹配度  **/
	private String matchingDegree;
	/**  样板间产品表Id  **/
	private Integer templetProductId;
	/**  产品序号  **/
	private Integer productNumber;
	
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
	/**  备注  **/
	private String remark;
	
	/**  产品编码  **/
	private String productCode;
	/**  产品名称  **/
	private String productName;
	//品牌
	private String brandName;
	/** 品牌ID **/
	private Integer brandId;
	/** 产品大类 **/
	private String productTypeValue;
	

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public  Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public  Integer getDesignTempletId() {
		return designTempletId;
	}
	public void setDesignTempletId(Integer designTempletId){
		this.designTempletId = designTempletId;
	}
	public  String getDataSources() {
		return dataSources;
	}
	public void setDataSources(String dataSources){
		this.dataSources = dataSources;
	}
	public  String getMatchingDegree() {
		return matchingDegree;
	}
	public void setMatchingDegree(String matchingDegree){
		this.matchingDegree = matchingDegree;
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
	
	public Integer getTempletProductId() {
		return templetProductId;
	}

	public void setTempletProductId(Integer templetProductId) {
		this.templetProductId = templetProductId;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getProductTypeValue() {
		return productTypeValue;
	}
	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
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
        ProductRecommendation other = (ProductRecommendation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getDesignTempletId() == null ? other.getDesignTempletId() == null : this.getDesignTempletId().equals(other.getDesignTempletId()))
            && (this.getDataSources() == null ? other.getDataSources() == null : this.getDataSources().equals(other.getDataSources()))
            && (this.getMatchingDegree() == null ? other.getMatchingDegree() == null : this.getMatchingDegree().equals(other.getMatchingDegree()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getTempletProductId() == null ? other.getTempletProductId() == null : this.getTempletProductId().equals(other.getTempletProductId()))
            && (this.getProductNumber() == null ? other.getProductNumber() == null : this.getProductNumber().equals(other.getProductNumber()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getDesignTempletId() == null) ? 0 : getDesignTempletId().hashCode());
        result = prime * result + ((getDataSources() == null) ? 0 : getDataSources().hashCode());
        result = prime * result + ((getMatchingDegree() == null) ? 0 : getMatchingDegree().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getTempletProductId() == null) ? 0 : getTempletProductId().hashCode());
        result = prime * result + ((getProductNumber() == null) ? 0 : getProductNumber().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public ProductRecommendation copy(){
       ProductRecommendation obj =  new ProductRecommendation();
       obj.setProductId(this.productId);
       obj.setDesignTempletId(this.designTempletId);
       obj.setDataSources(this.dataSources);
       obj.setMatchingDegree(this.matchingDegree);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setTempletProductId(this.templetProductId);
       obj.setProductNumber(this.productNumber);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("productId",this.productId);
       map.put("designTempletId",this.designTempletId);
       map.put("dataSources",this.dataSources);
       map.put("matchingDegree",this.matchingDegree);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("templetProductId",this.templetProductId);
       map.put("productNumber",this.productNumber);
       map.put("remark",this.remark);

       return map;
    }
}
