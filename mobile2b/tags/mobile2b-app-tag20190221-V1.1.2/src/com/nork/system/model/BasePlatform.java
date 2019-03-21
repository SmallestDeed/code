package com.nork.system.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**   
 * @Title: BasePlatform.java 
 * @Package com.nork.platform.model
 * @Description:基础-平台表
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
public class BasePlatform extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  平台名称  **/
	private String platformName;
	/**  平台类型(前台/后台)  **/
	private String platformType;
	/**  媒介类型  **/
	private String mediaType;
	/**  平台编码  **/
	private String platformCode;
	/**  所属平台分类(2b/2c/sandu)  **/
	private String platformBussinessType;
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

	public  String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName){
		this.platformName = platformName;
	}
	public  String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType){
		this.platformType = platformType;
	}
	public  String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}
	public  String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode){
		this.platformCode = platformCode;
	}
	public  String getPlatformBussinessType() {
		return platformBussinessType;
	}
	public void setPlatformBussinessType(String platformBussinessType){
		this.platformBussinessType = platformBussinessType;
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
        BasePlatform other = (BasePlatform) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlatformName() == null ? other.getPlatformName() == null : this.getPlatformName().equals(other.getPlatformName()))
            && (this.getPlatformType() == null ? other.getPlatformType() == null : this.getPlatformType().equals(other.getPlatformType()))
            && (this.getMediaType() == null ? other.getMediaType() == null : this.getMediaType().equals(other.getMediaType()))
            && (this.getPlatformCode() == null ? other.getPlatformCode() == null : this.getPlatformCode().equals(other.getPlatformCode()))
            && (this.getPlatformBussinessType() == null ? other.getPlatformBussinessType() == null : this.getPlatformBussinessType().equals(other.getPlatformBussinessType()))
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
        result = prime * result + ((getPlatformName() == null) ? 0 : getPlatformName().hashCode());
        result = prime * result + ((getPlatformType() == null) ? 0 : getPlatformType().hashCode());
        result = prime * result + ((getMediaType() == null) ? 0 : getMediaType().hashCode());
        result = prime * result + ((getPlatformCode() == null) ? 0 : getPlatformCode().hashCode());
        result = prime * result + ((getPlatformBussinessType() == null) ? 0 : getPlatformBussinessType().hashCode());
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
    public BasePlatform copy(){
       BasePlatform obj =  new BasePlatform();
       obj.setPlatformName(this.platformName);
       obj.setPlatformType(this.platformType);
       obj.setMediaType(this.mediaType);
       obj.setPlatformCode(this.platformCode);
       obj.setPlatformBussinessType(this.platformBussinessType);
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
       map.put("platformName",this.platformName);
       map.put("platformType",this.platformType);
       map.put("mediaType",this.mediaType);
       map.put("platformCode",this.platformCode);
       map.put("platformBussinessType",this.platformBussinessType);
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
