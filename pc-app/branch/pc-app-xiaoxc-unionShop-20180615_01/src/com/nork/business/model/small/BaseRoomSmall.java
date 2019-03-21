package com.nork.business.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: BaseRoom.java 
 * @Package com.nork.business.model.small
 * @Description:业务-房型
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:04:29
 * @version V1.0   
 */
public class BaseRoomSmall  implements Serializable{
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


}
