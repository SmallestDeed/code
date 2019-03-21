package com.nork.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: BaseRoom.java 
 * @Package com.nork.business.model
 * @Description:业务-房型
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:04:29
 * @version V1.0   
 */
public class BaseRoom  extends Mapper implements Serializable{
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
	/**  房间编码  **/
	private String roomCode;
	/**  房间名称  **/
	private String roomName;
	/**  房间类型  **/
	private Integer roomTypeId;
	/**  占地长度  **/
	private String maxLength;
	/**  占地宽度  **/
	private String maxWidth;
	/**  占地面积  **/
	private String maxAreas;
	/**  主体长度  **/
	private String mainLength;
	/**  主体宽度  **/
	private String mainWidth;
	/**  主体面积  **/
	private String mainAreas;
	/**  主体高度  **/
	private String mainHigh;
	/**  是否认证  **/
	private String isCertified;
	/**  认证人  **/
	private String certifiedPerson;
	/**  认证方式  **/
	private String certifiedType;
	/**  房间描述  **/
	private String roomDesc;
	/**  房间id  **/
	private Integer houseId;
	/**  空间id  **/
	private Integer houseSpaceId;
	/**  房型类型  **/
	private String roomCommonCode;
	/**  房型标示  **/
	private String roomTypeCode;
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
	public  String getRoomCode() {
		return roomCode;
	}
	public void setRoomCode(String roomCode){
		this.roomCode = roomCode;
	}
	public  String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName){
		this.roomName = roomName;
	}
	public  Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer roomTypeId){
		this.roomTypeId = roomTypeId;
	}
	public  String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength){
		this.maxLength = maxLength;
	}
	public  String getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(String maxWidth){
		this.maxWidth = maxWidth;
	}
	public  String getMaxAreas() {
		return maxAreas;
	}
	public void setMaxAreas(String maxAreas){
		this.maxAreas = maxAreas;
	}
	public  String getMainLength() {
		return mainLength;
	}
	public void setMainLength(String mainLength){
		this.mainLength = mainLength;
	}
	public  String getMainWidth() {
		return mainWidth;
	}
	public void setMainWidth(String mainWidth){
		this.mainWidth = mainWidth;
	}
	public  String getMainAreas() {
		return mainAreas;
	}
	public void setMainAreas(String mainAreas){
		this.mainAreas = mainAreas;
	}
	public  String getMainHigh() {
		return mainHigh;
	}
	public void setMainHigh(String mainHigh){
		this.mainHigh = mainHigh;
	}
	public  String getIsCertified() {
		return isCertified;
	}
	public void setIsCertified(String isCertified){
		this.isCertified = isCertified;
	}
	public  String getCertifiedPerson() {
		return certifiedPerson;
	}
	public void setCertifiedPerson(String certifiedPerson){
		this.certifiedPerson = certifiedPerson;
	}
	public  String getCertifiedType() {
		return certifiedType;
	}
	public void setCertifiedType(String certifiedType){
		this.certifiedType = certifiedType;
	}
	public  String getRoomDesc() {
		return roomDesc;
	}
	public void setRoomDesc(String roomDesc){
		this.roomDesc = roomDesc;
	}
	public  Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId){
		this.houseId = houseId;
	}
	public  Integer getHouseSpaceId() {
		return houseSpaceId;
	}
	public void setHouseSpaceId(Integer houseSpaceId){
		this.houseSpaceId = houseSpaceId;
	}
	public  String getRoomCommonCode() {
		return roomCommonCode;
	}
	public void setRoomCommonCode(String roomCommonCode){
		this.roomCommonCode = roomCommonCode;
	}
	public  String getRoomTypeCode() {
		return roomTypeCode;
	}
	public void setRoomTypeCode(String roomTypeCode){
		this.roomTypeCode = roomTypeCode;
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
        BaseRoom other = (BaseRoom) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getRoomCode() == null ? other.getRoomCode() == null : this.getRoomCode().equals(other.getRoomCode()))
            && (this.getRoomName() == null ? other.getRoomName() == null : this.getRoomName().equals(other.getRoomName()))
            && (this.getRoomTypeId() == null ? other.getRoomTypeId() == null : this.getRoomTypeId().equals(other.getRoomTypeId()))
            && (this.getMaxLength() == null ? other.getMaxLength() == null : this.getMaxLength().equals(other.getMaxLength()))
            && (this.getMaxWidth() == null ? other.getMaxWidth() == null : this.getMaxWidth().equals(other.getMaxWidth()))
            && (this.getMaxAreas() == null ? other.getMaxAreas() == null : this.getMaxAreas().equals(other.getMaxAreas()))
            && (this.getMainLength() == null ? other.getMainLength() == null : this.getMainLength().equals(other.getMainLength()))
            && (this.getMainWidth() == null ? other.getMainWidth() == null : this.getMainWidth().equals(other.getMainWidth()))
            && (this.getMainAreas() == null ? other.getMainAreas() == null : this.getMainAreas().equals(other.getMainAreas()))
            && (this.getMainHigh() == null ? other.getMainHigh() == null : this.getMainHigh().equals(other.getMainHigh()))
            && (this.getIsCertified() == null ? other.getIsCertified() == null : this.getIsCertified().equals(other.getIsCertified()))
            && (this.getCertifiedPerson() == null ? other.getCertifiedPerson() == null : this.getCertifiedPerson().equals(other.getCertifiedPerson()))
            && (this.getCertifiedType() == null ? other.getCertifiedType() == null : this.getCertifiedType().equals(other.getCertifiedType()))
            && (this.getRoomDesc() == null ? other.getRoomDesc() == null : this.getRoomDesc().equals(other.getRoomDesc()))
            && (this.getHouseId() == null ? other.getHouseId() == null : this.getHouseId().equals(other.getHouseId()))
            && (this.getHouseSpaceId() == null ? other.getHouseSpaceId() == null : this.getHouseSpaceId().equals(other.getHouseSpaceId()))
            && (this.getRoomCommonCode() == null ? other.getRoomCommonCode() == null : this.getRoomCommonCode().equals(other.getRoomCommonCode()))
            && (this.getRoomTypeCode() == null ? other.getRoomTypeCode() == null : this.getRoomTypeCode().equals(other.getRoomTypeCode()))
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
        result = prime * result + ((getRoomCode() == null) ? 0 : getRoomCode().hashCode());
        result = prime * result + ((getRoomName() == null) ? 0 : getRoomName().hashCode());
        result = prime * result + ((getRoomTypeId() == null) ? 0 : getRoomTypeId().hashCode());
        result = prime * result + ((getMaxLength() == null) ? 0 : getMaxLength().hashCode());
        result = prime * result + ((getMaxWidth() == null) ? 0 : getMaxWidth().hashCode());
        result = prime * result + ((getMaxAreas() == null) ? 0 : getMaxAreas().hashCode());
        result = prime * result + ((getMainLength() == null) ? 0 : getMainLength().hashCode());
        result = prime * result + ((getMainWidth() == null) ? 0 : getMainWidth().hashCode());
        result = prime * result + ((getMainAreas() == null) ? 0 : getMainAreas().hashCode());
        result = prime * result + ((getMainHigh() == null) ? 0 : getMainHigh().hashCode());
        result = prime * result + ((getIsCertified() == null) ? 0 : getIsCertified().hashCode());
        result = prime * result + ((getCertifiedPerson() == null) ? 0 : getCertifiedPerson().hashCode());
        result = prime * result + ((getCertifiedType() == null) ? 0 : getCertifiedType().hashCode());
        result = prime * result + ((getRoomDesc() == null) ? 0 : getRoomDesc().hashCode());
        result = prime * result + ((getHouseId() == null) ? 0 : getHouseId().hashCode());
        result = prime * result + ((getHouseSpaceId() == null) ? 0 : getHouseSpaceId().hashCode());
        result = prime * result + ((getRoomCommonCode() == null) ? 0 : getRoomCommonCode().hashCode());
        result = prime * result + ((getRoomTypeCode() == null) ? 0 : getRoomTypeCode().hashCode());
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
    public BaseRoom copy(){
       BaseRoom obj =  new BaseRoom();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setRoomCode(this.roomCode);
       obj.setRoomName(this.roomName);
       obj.setRoomTypeId(this.roomTypeId);
       obj.setMaxLength(this.maxLength);
       obj.setMaxWidth(this.maxWidth);
       obj.setMaxAreas(this.maxAreas);
       obj.setMainLength(this.mainLength);
       obj.setMainWidth(this.mainWidth);
       obj.setMainAreas(this.mainAreas);
       obj.setMainHigh(this.mainHigh);
       obj.setIsCertified(this.isCertified);
       obj.setCertifiedPerson(this.certifiedPerson);
       obj.setCertifiedType(this.certifiedType);
       obj.setRoomDesc(this.roomDesc);
       obj.setHouseId(this.houseId);
       obj.setHouseSpaceId(this.houseSpaceId);
       obj.setRoomCommonCode(this.roomCommonCode);
       obj.setRoomTypeCode(this.roomTypeCode);
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
       map.put("roomCode",this.roomCode);
       map.put("roomName",this.roomName);
       map.put("roomTypeId",this.roomTypeId);
       map.put("maxLength",this.maxLength);
       map.put("maxWidth",this.maxWidth);
       map.put("maxAreas",this.maxAreas);
       map.put("mainLength",this.mainLength);
       map.put("mainWidth",this.mainWidth);
       map.put("mainAreas",this.mainAreas);
       map.put("mainHigh",this.mainHigh);
       map.put("isCertified",this.isCertified);
       map.put("certifiedPerson",this.certifiedPerson);
       map.put("certifiedType",this.certifiedType);
       map.put("roomDesc",this.roomDesc);
       map.put("houseId",this.houseId);
       map.put("houseSpaceId",this.houseSpaceId);
       map.put("roomCommonCode",this.roomCommonCode);
       map.put("roomTypeCode",this.roomTypeCode);
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
