package com.nork.resource.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: ResPic.java 
 * @Package com.nork.system.model
 * @Description:系统-图片资源库
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
public class ResPicHouse  extends Mapper implements Serializable{
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
	/**  图片编码  **/
	private String picCode;
	/**  图片名称  **/
	private String picName;
	/**  图片文件名称  **/
	private String picFileName;
	/**  图片类型  **/
	private String picType;
	/**  图片大小  **/
	private Integer picSize;
	/**  图片长  **/
	private String picWeight;
	/**  图片高  **/
	private String picHigh;
	/**  图片后缀  **/
	private String picSuffix;
	/**  图片级别  **/
	private String picLevel;
	/**  图片格式  **/
	private String picFormat;
	/**  图片路径  **/
	private String picPath;
	/**  图片描述  **/
	private String picDesc;
	/**  图片排序  **/
	private String picOrdering;
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
	public  String getPicCode() {
		return picCode;
	}
	public void setPicCode(String picCode){
		this.picCode = picCode;
	}
	public  String getPicName() {
		return picName;
	}
	public void setPicName(String picName){
		this.picName = picName;
	}
	public  String getPicFileName() {
		return picFileName;
	}
	public void setPicFileName(String picFileName){
		this.picFileName = picFileName;
	}
	public  String getPicType() {
		return picType;
	}
	public void setPicType(String picType){
		this.picType = picType;
	}
	public  Integer getPicSize() {
		return picSize;
	}
	public void setPicSize(Integer picSize){
		this.picSize = picSize;
	}
	public  String getPicWeight() {
		return picWeight;
	}
	public void setPicWeight(String picWeight){
		this.picWeight = picWeight;
	}
	public  String getPicHigh() {
		return picHigh;
	}
	public void setPicHigh(String picHigh){
		this.picHigh = picHigh;
	}
	public  String getPicSuffix() {
		return picSuffix;
	}
	public void setPicSuffix(String picSuffix){
		this.picSuffix = picSuffix;
	}
	public  String getPicLevel() {
		return picLevel;
	}
	public void setPicLevel(String picLevel){
		this.picLevel = picLevel;
	}
	public  String getPicFormat() {
		return picFormat;
	}
	public void setPicFormat(String picFormat){
		this.picFormat = picFormat;
	}
	public  String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath){
		this.picPath = picPath;
	}
	public  String getPicDesc() {
		return picDesc;
	}
	public void setPicDesc(String picDesc){
		this.picDesc = picDesc;
	}
	public  String getPicOrdering() {
		return picOrdering;
	}
	public void setPicOrdering(String picOrdering){
		this.picOrdering = picOrdering;
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
        ResPicHouse other = (ResPicHouse) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getPicCode() == null ? other.getPicCode() == null : this.getPicCode().equals(other.getPicCode()))
            && (this.getPicName() == null ? other.getPicName() == null : this.getPicName().equals(other.getPicName()))
            && (this.getPicFileName() == null ? other.getPicFileName() == null : this.getPicFileName().equals(other.getPicFileName()))
            && (this.getPicType() == null ? other.getPicType() == null : this.getPicType().equals(other.getPicType()))
            && (this.getPicSize() == null ? other.getPicSize() == null : this.getPicSize().equals(other.getPicSize()))
            && (this.getPicWeight() == null ? other.getPicWeight() == null : this.getPicWeight().equals(other.getPicWeight()))
            && (this.getPicHigh() == null ? other.getPicHigh() == null : this.getPicHigh().equals(other.getPicHigh()))
            && (this.getPicSuffix() == null ? other.getPicSuffix() == null : this.getPicSuffix().equals(other.getPicSuffix()))
            && (this.getPicLevel() == null ? other.getPicLevel() == null : this.getPicLevel().equals(other.getPicLevel()))
            && (this.getPicFormat() == null ? other.getPicFormat() == null : this.getPicFormat().equals(other.getPicFormat()))
            && (this.getPicPath() == null ? other.getPicPath() == null : this.getPicPath().equals(other.getPicPath()))
            && (this.getPicDesc() == null ? other.getPicDesc() == null : this.getPicDesc().equals(other.getPicDesc()))
            && (this.getPicOrdering() == null ? other.getPicOrdering() == null : this.getPicOrdering().equals(other.getPicOrdering()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
            && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
            && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
            && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
            && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getNumAtt1() == null ? other.getNumAtt1() == null : this.getNumAtt1().equals(other.getNumAtt1()))
            && (this.getNumAtt2() == null ? other.getNumAtt2() == null : this.getNumAtt2().equals(other.getNumAtt2()))
            && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
            && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getPicCode() == null) ? 0 : getPicCode().hashCode());
        result = prime * result + ((getPicName() == null) ? 0 : getPicName().hashCode());
        result = prime * result + ((getPicFileName() == null) ? 0 : getPicFileName().hashCode());
        result = prime * result + ((getPicType() == null) ? 0 : getPicType().hashCode());
        result = prime * result + ((getPicSize() == null) ? 0 : getPicSize().hashCode());
        result = prime * result + ((getPicWeight() == null) ? 0 : getPicWeight().hashCode());
        result = prime * result + ((getPicHigh() == null) ? 0 : getPicHigh().hashCode());
        result = prime * result + ((getPicSuffix() == null) ? 0 : getPicSuffix().hashCode());
        result = prime * result + ((getPicLevel() == null) ? 0 : getPicLevel().hashCode());
        result = prime * result + ((getPicFormat() == null) ? 0 : getPicFormat().hashCode());
        result = prime * result + ((getPicPath() == null) ? 0 : getPicPath().hashCode());
        result = prime * result + ((getPicDesc() == null) ? 0 : getPicDesc().hashCode());
        result = prime * result + ((getPicOrdering() == null) ? 0 : getPicOrdering().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getNumAtt1() == null) ? 0 : getNumAtt1().hashCode());
        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public ResPicHouse copy(){
       ResPicHouse obj =  new ResPicHouse();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setPicCode(this.picCode);
       obj.setPicName(this.picName);
       obj.setPicFileName(this.picFileName);
       obj.setPicType(this.picType);
       obj.setPicSize(this.picSize);
       obj.setPicWeight(this.picWeight);
       obj.setPicHigh(this.picHigh);
       obj.setPicSuffix(this.picSuffix);
       obj.setPicLevel(this.picLevel);
       obj.setPicFormat(this.picFormat);
       obj.setPicPath(this.picPath);
       obj.setPicDesc(this.picDesc);
       obj.setPicOrdering(this.picOrdering);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setAtt3(this.att3);
       obj.setAtt4(this.att4);
       obj.setAtt5(this.att5);
       obj.setAtt6(this.att6);
       obj.setDateAtt1(this.dateAtt1);
       obj.setDateAtt2(this.dateAtt2);
       obj.setNumAtt1(this.numAtt1);
       obj.setNumAtt2(this.numAtt2);
       obj.setNumAtt3(this.numAtt3);
       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("picCode",this.picCode);
       map.put("picName",this.picName);
       map.put("picFileName",this.picFileName);
       map.put("picType",this.picType);
       map.put("picSize",this.picSize);
       map.put("picWeight",this.picWeight);
       map.put("picHigh",this.picHigh);
       map.put("picSuffix",this.picSuffix);
       map.put("picLevel",this.picLevel);
       map.put("picFormat",this.picFormat);
       map.put("picPath",this.picPath);
       map.put("picDesc",this.picDesc);
       map.put("picOrdering",this.picOrdering);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("att3",this.att3);
       map.put("att4",this.att4);
       map.put("att5",this.att5);
       map.put("att6",this.att6);
       map.put("dateAtt1",this.dateAtt1);
       map.put("dateAtt2",this.dateAtt2);
       map.put("numAtt1",this.numAtt1);
       map.put("numAtt2",this.numAtt2);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);

       return map;
    }
}
