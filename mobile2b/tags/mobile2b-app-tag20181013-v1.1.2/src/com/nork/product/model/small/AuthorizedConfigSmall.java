package com.nork.product.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: AuthorizedConfig.java 
 * @Package com.nork.product.model.small
 * @Description:产品-授权配置
 * @createAuthor pandajun 
 * @CreateDate 2016-04-27 14:07:34
 * @version V1.0   
 */
public class AuthorizedConfigSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  序列号  **/
	private String authorizedCode;
	/**  密码  **/
	private String password;
	/**  企业ID  **/
	private String companyId;
	/**  品牌ID  **/
	private String brandIds;
	/**  大分类  **/
	private String bigType;
	/**  小分类  **/
	private String smallType;
	/**  小分类ids  **/
	private String smallTypeIds;
	/**  产品IDS  **/
	private String productIds;
	/**  授权状态  **/
	private Integer state;
	/**  代理商  **/
	private Integer userId;
	/**  终端IMEI  **/
	private String terminalImei;
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

	public  String getAuthorizedCode() {
		return authorizedCode;
	}
	public void setAuthorizedCode(String authorizedCode){
		this.authorizedCode = authorizedCode;
	}
	public  String getPassword() {
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public  String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}
	public  String getBrandIds() {
		return brandIds;
	}
	public void setBrandIds(String brandIds){
		this.brandIds = brandIds;
	}
	public  String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType){
		this.bigType = bigType;
	}
	public  String getSmallType() {
		return smallType;
	}
	public void setSmallType(String smallType){
		this.smallType = smallType;
	}
	public  String getSmallTypeIds() {
		return smallTypeIds;
	}
	public void setSmallTypeIds(String smallTypeIds){
		this.smallTypeIds = smallTypeIds;
	}
	public  String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds){
		this.productIds = productIds;
	}
	public  Integer getState() {
		return state;
	}
	public void setState(Integer state){
		this.state = state;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getTerminalImei() {
		return terminalImei;
	}
	public void setTerminalImei(String terminalImei){
		this.terminalImei = terminalImei;
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
