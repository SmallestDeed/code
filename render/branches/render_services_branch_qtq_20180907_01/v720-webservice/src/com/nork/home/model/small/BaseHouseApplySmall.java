package com.nork.home.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: BaseHouseApply.java 
 * @Package com.nork.home.model.small
 * @Description:户型房型-户型申请表
 * @createAuthor pandajun 
 * @CreateDate 2016-10-13 11:45:31
 * @version V1.0   
 */
public class BaseHouseApplySmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  申请时间  **/
	private Date applyTime;
	/**  用户id  **/
	private Integer userId;
	/**  图片id  **/
	private Integer picId;
	/**  描述  **/
	private String description;
	/**  处理状态  **/
	private Integer status;
	/**  城市信息  **/
	private String cityInfo;
	/**  小区信息  **/
	private String livingInfo;
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

	public  Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime){
		this.applyTime = applyTime;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId){
		this.picId = picId;
	}
	public  String getDescription() {
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public  Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public  String getCityInfo() {
		return cityInfo;
	}
	public void setCityInfo(String cityInfo){
		this.cityInfo = cityInfo;
	}
	public  String getLivingInfo() {
		return livingInfo;
	}
	public void setLivingInfo(String livingInfo){
		this.livingInfo = livingInfo;
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
