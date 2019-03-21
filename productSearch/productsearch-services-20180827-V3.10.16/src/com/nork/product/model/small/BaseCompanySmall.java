package com.nork.product.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: BaseCompany.java 
 * @Package com.nork.product.model.small
 * @Description:产品模块-企业表
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:45
 * @version V1.0   
 */
public class BaseCompanySmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
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
	/**  企业编码  **/
	private String companyCode;
	/**  企业名称  **/
	private String companyName;
	/**  企业网站  **/
	private String companyUrl;
	/**  企业介绍  **/
	private String companyDesc;
	/**  企业地址  **/
	private String companyAddress;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  字符备用3  **/
	private String att3;
	/**  字符备用4  **/
	private String att4;
	/**  字符备用5  **/
	private String att5;
	/**  字符备用6  **/
	private String att6;
	/**  时间备用1  **/
	private Date dateAtt1;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  整数备用1  **/
	private Integer numAtt1;
	/**  整数备用2  **/
	private Integer numAtt2;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;

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
	public  String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode){
		this.companyCode = companyCode;
	}
	public  String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	public  String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl){
		this.companyUrl = companyUrl;
	}
	public  String getCompanyDesc() {
		return companyDesc;
	}
	public void setCompanyDesc(String companyDesc){
		this.companyDesc = companyDesc;
	}
	public  String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress){
		this.companyAddress = companyAddress;
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
	public  String getAtt3() {
		return att3;
	}
	public void setAtt3(String att3){
		this.att3 = att3;
	}
	public  String getAtt4() {
		return att4;
	}
	public void setAtt4(String att4){
		this.att4 = att4;
	}
	public  String getAtt5() {
		return att5;
	}
	public void setAtt5(String att5){
		this.att5 = att5;
	}
	public  String getAtt6() {
		return att6;
	}
	public void setAtt6(String att6){
		this.att6 = att6;
	}
	public  Date getDateAtt1() {
		return dateAtt1;
	}
	public void setDateAtt1(Date dateAtt1){
		this.dateAtt1 = dateAtt1;
	}
	public  Date getDateAtt2() {
		return dateAtt2;
	}
	public void setDateAtt2(Date dateAtt2){
		this.dateAtt2 = dateAtt2;
	}
	public  Integer getNumAtt1() {
		return numAtt1;
	}
	public void setNumAtt1(Integer numAtt1){
		this.numAtt1 = numAtt1;
	}
	public  Integer getNumAtt2() {
		return numAtt2;
	}
	public void setNumAtt2(Integer numAtt2){
		this.numAtt2 = numAtt2;
	}
	public  Double getNumAtt3() {
		return numAtt3;
	}
	public void setNumAtt3(Double numAtt3){
		this.numAtt3 = numAtt3;
	}
	public  Double getNumAtt4() {
		return numAtt4;
	}
	public void setNumAtt4(Double numAtt4){
		this.numAtt4 = numAtt4;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


}
