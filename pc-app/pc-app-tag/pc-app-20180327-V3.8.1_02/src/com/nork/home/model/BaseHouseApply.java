package com.nork.home.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: BaseHouseApply.java 
 * @Package com.nork.home.model
 * @Description:户型房型-户型申请表
 * @createAuthor pandajun 
 * @CreateDate 2016-10-13 11:45:31
 * @version V1.0   
 */
public class BaseHouseApply  extends Mapper implements Serializable{
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
	/**  小区名称  **/
	private String houseName;
	/** 小区面积  **/
	private String houseArea;

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

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
        BaseHouseApply other = (BaseHouseApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplyTime() == null ? other.getApplyTime() == null : this.getApplyTime().equals(other.getApplyTime()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCityInfo() == null ? other.getCityInfo() == null : this.getCityInfo().equals(other.getCityInfo()))
            && (this.getLivingInfo() == null ? other.getLivingInfo() == null : this.getLivingInfo().equals(other.getLivingInfo()))
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
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCityInfo() == null) ? 0 : getCityInfo().hashCode());
        result = prime * result + ((getLivingInfo() == null) ? 0 : getLivingInfo().hashCode());
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
;
        return result;
    }
    
    /**获取对象的copy**/
    public BaseHouseApply copy(){
       BaseHouseApply obj =  new BaseHouseApply();
       obj.setApplyTime(this.applyTime);
       obj.setUserId(this.userId);
       obj.setPicId(this.picId);
       obj.setDescription(this.description);
       obj.setStatus(this.status);
       obj.setCityInfo(this.cityInfo);
       obj.setLivingInfo(this.livingInfo);
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
       map.put("applyTime",this.applyTime);
       map.put("userId",this.userId);
       map.put("picId",this.picId);
       map.put("description",this.description);
       map.put("status",this.status);
       map.put("cityInfo",this.cityInfo);
       map.put("livingInfo",this.livingInfo);
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
}
