package com.nork.cityunion.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: UnionDesignPlanStore.java 
 * @Package com.nork.cityunion.model.small
 * @Description:同城联盟-联盟设计方案素材库表
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:21:19
 * @version V1.0   
 */
public class UnionDesignPlanStoreSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  设计方案ID  **/
	private Integer designPlanId;
	/**  方案名称  **/
	private String designPlanName;
	/**  方案类型(0效果图方案/1推荐方案)  **/
	private Integer designPlanType;
	/**  用户ID  **/
	private Integer userId;
	/**  品牌id(推荐方案发布绑定的品牌ID)  **/
	private Integer brandId;
	/**  720渲染图ID  **/
	private Integer renderPicId;
	/**  720渲染缩略图ID  **/
	private Integer renderPicSmallId;
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

	public  Integer getDesignPlanId() {
		return designPlanId;
	}
	public void setDesignPlanId(Integer designPlanId){
		this.designPlanId = designPlanId;
	}
	public  String getDesignPlanName() {
		return designPlanName;
	}
	public void setDesignPlanName(String designPlanName){
		this.designPlanName = designPlanName;
	}
	public  Integer getDesignPlanType() {
		return designPlanType;
	}
	public void setDesignPlanType(Integer designPlanType){
		this.designPlanType = designPlanType;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId){
		this.brandId = brandId;
	}
	public  Integer getRenderPicId() {
		return renderPicId;
	}
	public void setRenderPicId(Integer renderPicId){
		this.renderPicId = renderPicId;
	}
	public  Integer getRenderPicSmallId() {
		return renderPicSmallId;
	}
	public void setRenderPicSmallId(Integer renderPicSmallId){
		this.renderPicSmallId = renderPicSmallId;
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
