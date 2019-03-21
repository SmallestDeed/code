package com.nork.design.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: DesignPlanCustomizedProduct.java 
 * @Package com.nork.design.model.small
 * @Description:设计方案-设计方案定制产品表
 * @createAuthor pandajun 
 * @CreateDate 2018-08-28 11:04:09
 * @version V1.0   
 */
public class DesignPlanCustomizedProductSmall  implements Serializable{
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
