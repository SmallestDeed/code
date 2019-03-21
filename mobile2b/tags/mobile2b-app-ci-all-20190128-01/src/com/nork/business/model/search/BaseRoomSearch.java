package com.nork.business.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.business.model.BaseRoom;

/**   
 * @Title: BaseRoomSearch.java 
 * @Package com.nork.business.model
 * @Description:业务-房型查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:04:29
 * @version V1.0   
 */
public class BaseRoomSearch extends  BaseRoom implements Serializable{
private static final long serialVersionUID = 1L;
	/**  系统编码-模糊查询  **/
	private String sch_SysCode_;
	/**  系统编码-左模糊查询  **/
	private String sch_SysCode;
	/**  系统编码-右模糊查询  **/
	private String schSysCode_;
	/**  系统编码-区间查询-开始字符串  **/
	private String sysCodeStart;
	/**  系统编码-区间查询-结束字符串  **/
	private String sysCodeEnd;
	/**  创建者-模糊查询  **/
	private String sch_Creator_;
	/**  创建者-左模糊查询  **/
	private String sch_Creator;
	/**  创建者-右模糊查询  **/
	private String schCreator_;
	/**  创建者-区间查询-开始字符串  **/
	private String creatorStart;
	/**  创建者-区间查询-结束字符串  **/
	private String creatorEnd;
	/**  创建时间-区间查询-开始时间  **/
	private Date gmtCreateStart;
	/**  创建时间-区间查询-结束时间  **/
	private Date gmtCreateEnd;
	/**  修改人-模糊查询  **/
	private String sch_Modifier_;
	/**  修改人-左模糊查询  **/
	private String sch_Modifier;
	/**  修改人-右模糊查询  **/
	private String schModifier_;
	/**  修改人-区间查询-开始字符串  **/
	private String modifierStart;
	/**  修改人-区间查询-结束字符串  **/
	private String modifierEnd;
	/**  修改时间-区间查询-开始时间  **/
	private Date gmtModifiedStart;
	/**  修改时间-区间查询-结束时间  **/
	private Date gmtModifiedEnd;
	/**  房间编码-模糊查询  **/
	private String sch_RoomCode_;
	/**  房间编码-左模糊查询  **/
	private String sch_RoomCode;
	/**  房间编码-右模糊查询  **/
	private String schRoomCode_;
	/**  房间编码-区间查询-开始字符串  **/
	private String roomCodeStart;
	/**  房间编码-区间查询-结束字符串  **/
	private String roomCodeEnd;
	/**  房间名称-模糊查询  **/
	private String sch_RoomName_;
	/**  房间名称-左模糊查询  **/
	private String sch_RoomName;
	/**  房间名称-右模糊查询  **/
	private String schRoomName_;
	/**  房间名称-区间查询-开始字符串  **/
	private String roomNameStart;
	/**  房间名称-区间查询-结束字符串  **/
	private String roomNameEnd;
	/**  占地长度-模糊查询  **/
	private String sch_MaxLength_;
	/**  占地长度-左模糊查询  **/
	private String sch_MaxLength;
	/**  占地长度-右模糊查询  **/
	private String schMaxLength_;
	/**  占地长度-区间查询-开始字符串  **/
	private String maxLengthStart;
	/**  占地长度-区间查询-结束字符串  **/
	private String maxLengthEnd;
	/**  占地宽度-模糊查询  **/
	private String sch_MaxWidth_;
	/**  占地宽度-左模糊查询  **/
	private String sch_MaxWidth;
	/**  占地宽度-右模糊查询  **/
	private String schMaxWidth_;
	/**  占地宽度-区间查询-开始字符串  **/
	private String maxWidthStart;
	/**  占地宽度-区间查询-结束字符串  **/
	private String maxWidthEnd;
	/**  占地面积-模糊查询  **/
	private String sch_MaxAreas_;
	/**  占地面积-左模糊查询  **/
	private String sch_MaxAreas;
	/**  占地面积-右模糊查询  **/
	private String schMaxAreas_;
	/**  占地面积-区间查询-开始字符串  **/
	private String maxAreasStart;
	/**  占地面积-区间查询-结束字符串  **/
	private String maxAreasEnd;
	/**  主体长度-模糊查询  **/
	private String sch_MainLength_;
	/**  主体长度-左模糊查询  **/
	private String sch_MainLength;
	/**  主体长度-右模糊查询  **/
	private String schMainLength_;
	/**  主体长度-区间查询-开始字符串  **/
	private String mainLengthStart;
	/**  主体长度-区间查询-结束字符串  **/
	private String mainLengthEnd;
	/**  主体宽度-模糊查询  **/
	private String sch_MainWidth_;
	/**  主体宽度-左模糊查询  **/
	private String sch_MainWidth;
	/**  主体宽度-右模糊查询  **/
	private String schMainWidth_;
	/**  主体宽度-区间查询-开始字符串  **/
	private String mainWidthStart;
	/**  主体宽度-区间查询-结束字符串  **/
	private String mainWidthEnd;
	/**  主体面积-模糊查询  **/
	private String sch_MainAreas_;
	/**  主体面积-左模糊查询  **/
	private String sch_MainAreas;
	/**  主体面积-右模糊查询  **/
	private String schMainAreas_;
	/**  主体面积-区间查询-开始字符串  **/
	private String mainAreasStart;
	/**  主体面积-区间查询-结束字符串  **/
	private String mainAreasEnd;
	/**  主体高度-模糊查询  **/
	private String sch_MainHigh_;
	/**  主体高度-左模糊查询  **/
	private String sch_MainHigh;
	/**  主体高度-右模糊查询  **/
	private String schMainHigh_;
	/**  主体高度-区间查询-开始字符串  **/
	private String mainHighStart;
	/**  主体高度-区间查询-结束字符串  **/
	private String mainHighEnd;
	/**  是否认证-模糊查询  **/
	private String sch_IsCertified_;
	/**  是否认证-左模糊查询  **/
	private String sch_IsCertified;
	/**  是否认证-右模糊查询  **/
	private String schIsCertified_;
	/**  是否认证-区间查询-开始字符串  **/
	private String isCertifiedStart;
	/**  是否认证-区间查询-结束字符串  **/
	private String isCertifiedEnd;
	/**  认证人-模糊查询  **/
	private String sch_CertifiedPerson_;
	/**  认证人-左模糊查询  **/
	private String sch_CertifiedPerson;
	/**  认证人-右模糊查询  **/
	private String schCertifiedPerson_;
	/**  认证人-区间查询-开始字符串  **/
	private String certifiedPersonStart;
	/**  认证人-区间查询-结束字符串  **/
	private String certifiedPersonEnd;
	/**  认证方式-模糊查询  **/
	private String sch_CertifiedType_;
	/**  认证方式-左模糊查询  **/
	private String sch_CertifiedType;
	/**  认证方式-右模糊查询  **/
	private String schCertifiedType_;
	/**  认证方式-区间查询-开始字符串  **/
	private String certifiedTypeStart;
	/**  认证方式-区间查询-结束字符串  **/
	private String certifiedTypeEnd;
	/**  房间描述-模糊查询  **/
	private String sch_RoomDesc_;
	/**  房间描述-左模糊查询  **/
	private String sch_RoomDesc;
	/**  房间描述-右模糊查询  **/
	private String schRoomDesc_;
	/**  房间描述-区间查询-开始字符串  **/
	private String roomDescStart;
	/**  房间描述-区间查询-结束字符串  **/
	private String roomDescEnd;
	/**  房型类型-模糊查询  **/
	private String sch_RoomCommonCode_;
	/**  房型类型-左模糊查询  **/
	private String sch_RoomCommonCode;
	/**  房型类型-右模糊查询  **/
	private String schRoomCommonCode_;
	/**  房型类型-区间查询-开始字符串  **/
	private String roomCommonCodeStart;
	/**  房型类型-区间查询-结束字符串  **/
	private String roomCommonCodeEnd;
	/**  房型标示-模糊查询  **/
	private String sch_RoomTypeCode_;
	/**  房型标示-左模糊查询  **/
	private String sch_RoomTypeCode;
	/**  房型标示-右模糊查询  **/
	private String schRoomTypeCode_;
	/**  房型标示-区间查询-开始字符串  **/
	private String roomTypeCodeStart;
	/**  房型标示-区间查询-结束字符串  **/
	private String roomTypeCodeEnd;
	/**  字符备用1-模糊查询  **/
	private String sch_Att1_;
	/**  字符备用1-左模糊查询  **/
	private String sch_Att1;
	/**  字符备用1-右模糊查询  **/
	private String schAtt1_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String att1Start;
	/**  字符备用1-区间查询-结束字符串  **/
	private String att1End;
	/**  字符备用2-模糊查询  **/
	private String sch_Att2_;
	/**  字符备用2-左模糊查询  **/
	private String sch_Att2;
	/**  字符备用2-右模糊查询  **/
	private String schAtt2_;
	/**  字符备用2-区间查询-开始字符串  **/
	private String att2Start;
	/**  字符备用2-区间查询-结束字符串  **/
	private String att2End;
	/**  字符备用3-模糊查询  **/
	private String sch_Att3_;
	/**  字符备用3-左模糊查询  **/
	private String sch_Att3;
	/**  字符备用3-右模糊查询  **/
	private String schAtt3_;
	/**  字符备用3-区间查询-开始字符串  **/
	private String att3Start;
	/**  字符备用3-区间查询-结束字符串  **/
	private String att3End;
	/**  字符备用4-模糊查询  **/
	private String sch_Att4_;
	/**  字符备用4-左模糊查询  **/
	private String sch_Att4;
	/**  字符备用4-右模糊查询  **/
	private String schAtt4_;
	/**  字符备用4-区间查询-开始字符串  **/
	private String att4Start;
	/**  字符备用4-区间查询-结束字符串  **/
	private String att4End;
	/**  字符备用5-模糊查询  **/
	private String sch_Att5_;
	/**  字符备用5-左模糊查询  **/
	private String sch_Att5;
	/**  字符备用5-右模糊查询  **/
	private String schAtt5_;
	/**  字符备用5-区间查询-开始字符串  **/
	private String att5Start;
	/**  字符备用5-区间查询-结束字符串  **/
	private String att5End;
	/**  字符备用6-模糊查询  **/
	private String sch_Att6_;
	/**  字符备用6-左模糊查询  **/
	private String sch_Att6;
	/**  字符备用6-右模糊查询  **/
	private String schAtt6_;
	/**  字符备用6-区间查询-开始字符串  **/
	private String att6Start;
	/**  字符备用6-区间查询-结束字符串  **/
	private String att6End;
	/**  时间备用1-区间查询-开始时间  **/
	private Date dateAtt1Start;
	/**  时间备用1-区间查询-结束时间  **/
	private Date dateAtt1End;
	/**  时间备用2-区间查询-开始时间  **/
	private Date dateAtt2Start;
	/**  时间备用2-区间查询-结束时间  **/
	private Date dateAtt2End;
	/**  备注-模糊查询  **/
	private String sch_Remark_;
	/**  备注-左模糊查询  **/
	private String sch_Remark;
	/**  备注-右模糊查询  **/
	private String schRemark_;
	/**  备注-区间查询-开始字符串  **/
	private String remarkStart;
	/**  备注-区间查询-结束字符串  **/
	private String remarkEnd;

	public  String getSch_SysCode_() {
		return sch_SysCode_;
	}
	public void setSch_SysCode_(String sch_SysCode_){
		this.sch_SysCode_ = sch_SysCode_;
	}
	public  String getSch_SysCode() {
		return sch_SysCode;
	}
	public void setSch_SysCode(String sch_SysCode){
		this.sch_SysCode = sch_SysCode;
	}
	public  String getSchSysCode_() {
		return schSysCode_;
	}
	public void setSchSysCode_(String schSysCode_){
		this.schSysCode_ = schSysCode_;
	}
	public  String getSysCodeStart() {
		return sysCodeStart;
	}
	public void setSysCodeStart(String sysCodeStart){
		this.sysCodeStart = sysCodeStart;
	}
	public  String getSysCodeEnd() {
		return sysCodeEnd;
	}
	public void setSysCodeEnd(String sysCodeEnd){
		this.sysCodeEnd = sysCodeEnd;
	}
	public  String getSch_Creator_() {
		return sch_Creator_;
	}
	public void setSch_Creator_(String sch_Creator_){
		this.sch_Creator_ = sch_Creator_;
	}
	public  String getSch_Creator() {
		return sch_Creator;
	}
	public void setSch_Creator(String sch_Creator){
		this.sch_Creator = sch_Creator;
	}
	public  String getSchCreator_() {
		return schCreator_;
	}
	public void setSchCreator_(String schCreator_){
		this.schCreator_ = schCreator_;
	}
	public  String getCreatorStart() {
		return creatorStart;
	}
	public void setCreatorStart(String creatorStart){
		this.creatorStart = creatorStart;
	}
	public  String getCreatorEnd() {
		return creatorEnd;
	}
	public void setCreatorEnd(String creatorEnd){
		this.creatorEnd = creatorEnd;
	}
	public  Date getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(Date gmtCreateStart){
		this.gmtCreateStart = gmtCreateStart;
	}
	public  Date getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(Date gmtCreateEnd){
		this.gmtCreateEnd = gmtCreateEnd;
	}
	public  String getSch_Modifier_() {
		return sch_Modifier_;
	}
	public void setSch_Modifier_(String sch_Modifier_){
		this.sch_Modifier_ = sch_Modifier_;
	}
	public  String getSch_Modifier() {
		return sch_Modifier;
	}
	public void setSch_Modifier(String sch_Modifier){
		this.sch_Modifier = sch_Modifier;
	}
	public  String getSchModifier_() {
		return schModifier_;
	}
	public void setSchModifier_(String schModifier_){
		this.schModifier_ = schModifier_;
	}
	public  String getModifierStart() {
		return modifierStart;
	}
	public void setModifierStart(String modifierStart){
		this.modifierStart = modifierStart;
	}
	public  String getModifierEnd() {
		return modifierEnd;
	}
	public void setModifierEnd(String modifierEnd){
		this.modifierEnd = modifierEnd;
	}
	public  Date getGmtModifiedStart() {
		return gmtModifiedStart;
	}
	public void setGmtModifiedStart(Date gmtModifiedStart){
		this.gmtModifiedStart = gmtModifiedStart;
	}
	public  Date getGmtModifiedEnd() {
		return gmtModifiedEnd;
	}
	public void setGmtModifiedEnd(Date gmtModifiedEnd){
		this.gmtModifiedEnd = gmtModifiedEnd;
	}
	public  String getSch_RoomCode_() {
		return sch_RoomCode_;
	}
	public void setSch_RoomCode_(String sch_RoomCode_){
		this.sch_RoomCode_ = sch_RoomCode_;
	}
	public  String getSch_RoomCode() {
		return sch_RoomCode;
	}
	public void setSch_RoomCode(String sch_RoomCode){
		this.sch_RoomCode = sch_RoomCode;
	}
	public  String getSchRoomCode_() {
		return schRoomCode_;
	}
	public void setSchRoomCode_(String schRoomCode_){
		this.schRoomCode_ = schRoomCode_;
	}
	public  String getRoomCodeStart() {
		return roomCodeStart;
	}
	public void setRoomCodeStart(String roomCodeStart){
		this.roomCodeStart = roomCodeStart;
	}
	public  String getRoomCodeEnd() {
		return roomCodeEnd;
	}
	public void setRoomCodeEnd(String roomCodeEnd){
		this.roomCodeEnd = roomCodeEnd;
	}
	public  String getSch_RoomName_() {
		return sch_RoomName_;
	}
	public void setSch_RoomName_(String sch_RoomName_){
		this.sch_RoomName_ = sch_RoomName_;
	}
	public  String getSch_RoomName() {
		return sch_RoomName;
	}
	public void setSch_RoomName(String sch_RoomName){
		this.sch_RoomName = sch_RoomName;
	}
	public  String getSchRoomName_() {
		return schRoomName_;
	}
	public void setSchRoomName_(String schRoomName_){
		this.schRoomName_ = schRoomName_;
	}
	public  String getRoomNameStart() {
		return roomNameStart;
	}
	public void setRoomNameStart(String roomNameStart){
		this.roomNameStart = roomNameStart;
	}
	public  String getRoomNameEnd() {
		return roomNameEnd;
	}
	public void setRoomNameEnd(String roomNameEnd){
		this.roomNameEnd = roomNameEnd;
	}
	public  String getSch_MaxLength_() {
		return sch_MaxLength_;
	}
	public void setSch_MaxLength_(String sch_MaxLength_){
		this.sch_MaxLength_ = sch_MaxLength_;
	}
	public  String getSch_MaxLength() {
		return sch_MaxLength;
	}
	public void setSch_MaxLength(String sch_MaxLength){
		this.sch_MaxLength = sch_MaxLength;
	}
	public  String getSchMaxLength_() {
		return schMaxLength_;
	}
	public void setSchMaxLength_(String schMaxLength_){
		this.schMaxLength_ = schMaxLength_;
	}
	public  String getMaxLengthStart() {
		return maxLengthStart;
	}
	public void setMaxLengthStart(String maxLengthStart){
		this.maxLengthStart = maxLengthStart;
	}
	public  String getMaxLengthEnd() {
		return maxLengthEnd;
	}
	public void setMaxLengthEnd(String maxLengthEnd){
		this.maxLengthEnd = maxLengthEnd;
	}
	public  String getSch_MaxWidth_() {
		return sch_MaxWidth_;
	}
	public void setSch_MaxWidth_(String sch_MaxWidth_){
		this.sch_MaxWidth_ = sch_MaxWidth_;
	}
	public  String getSch_MaxWidth() {
		return sch_MaxWidth;
	}
	public void setSch_MaxWidth(String sch_MaxWidth){
		this.sch_MaxWidth = sch_MaxWidth;
	}
	public  String getSchMaxWidth_() {
		return schMaxWidth_;
	}
	public void setSchMaxWidth_(String schMaxWidth_){
		this.schMaxWidth_ = schMaxWidth_;
	}
	public  String getMaxWidthStart() {
		return maxWidthStart;
	}
	public void setMaxWidthStart(String maxWidthStart){
		this.maxWidthStart = maxWidthStart;
	}
	public  String getMaxWidthEnd() {
		return maxWidthEnd;
	}
	public void setMaxWidthEnd(String maxWidthEnd){
		this.maxWidthEnd = maxWidthEnd;
	}
	public  String getSch_MaxAreas_() {
		return sch_MaxAreas_;
	}
	public void setSch_MaxAreas_(String sch_MaxAreas_){
		this.sch_MaxAreas_ = sch_MaxAreas_;
	}
	public  String getSch_MaxAreas() {
		return sch_MaxAreas;
	}
	public void setSch_MaxAreas(String sch_MaxAreas){
		this.sch_MaxAreas = sch_MaxAreas;
	}
	public  String getSchMaxAreas_() {
		return schMaxAreas_;
	}
	public void setSchMaxAreas_(String schMaxAreas_){
		this.schMaxAreas_ = schMaxAreas_;
	}
	public  String getMaxAreasStart() {
		return maxAreasStart;
	}
	public void setMaxAreasStart(String maxAreasStart){
		this.maxAreasStart = maxAreasStart;
	}
	public  String getMaxAreasEnd() {
		return maxAreasEnd;
	}
	public void setMaxAreasEnd(String maxAreasEnd){
		this.maxAreasEnd = maxAreasEnd;
	}
	public  String getSch_MainLength_() {
		return sch_MainLength_;
	}
	public void setSch_MainLength_(String sch_MainLength_){
		this.sch_MainLength_ = sch_MainLength_;
	}
	public  String getSch_MainLength() {
		return sch_MainLength;
	}
	public void setSch_MainLength(String sch_MainLength){
		this.sch_MainLength = sch_MainLength;
	}
	public  String getSchMainLength_() {
		return schMainLength_;
	}
	public void setSchMainLength_(String schMainLength_){
		this.schMainLength_ = schMainLength_;
	}
	public  String getMainLengthStart() {
		return mainLengthStart;
	}
	public void setMainLengthStart(String mainLengthStart){
		this.mainLengthStart = mainLengthStart;
	}
	public  String getMainLengthEnd() {
		return mainLengthEnd;
	}
	public void setMainLengthEnd(String mainLengthEnd){
		this.mainLengthEnd = mainLengthEnd;
	}
	public  String getSch_MainWidth_() {
		return sch_MainWidth_;
	}
	public void setSch_MainWidth_(String sch_MainWidth_){
		this.sch_MainWidth_ = sch_MainWidth_;
	}
	public  String getSch_MainWidth() {
		return sch_MainWidth;
	}
	public void setSch_MainWidth(String sch_MainWidth){
		this.sch_MainWidth = sch_MainWidth;
	}
	public  String getSchMainWidth_() {
		return schMainWidth_;
	}
	public void setSchMainWidth_(String schMainWidth_){
		this.schMainWidth_ = schMainWidth_;
	}
	public  String getMainWidthStart() {
		return mainWidthStart;
	}
	public void setMainWidthStart(String mainWidthStart){
		this.mainWidthStart = mainWidthStart;
	}
	public  String getMainWidthEnd() {
		return mainWidthEnd;
	}
	public void setMainWidthEnd(String mainWidthEnd){
		this.mainWidthEnd = mainWidthEnd;
	}
	public  String getSch_MainAreas_() {
		return sch_MainAreas_;
	}
	public void setSch_MainAreas_(String sch_MainAreas_){
		this.sch_MainAreas_ = sch_MainAreas_;
	}
	public  String getSch_MainAreas() {
		return sch_MainAreas;
	}
	public void setSch_MainAreas(String sch_MainAreas){
		this.sch_MainAreas = sch_MainAreas;
	}
	public  String getSchMainAreas_() {
		return schMainAreas_;
	}
	public void setSchMainAreas_(String schMainAreas_){
		this.schMainAreas_ = schMainAreas_;
	}
	public  String getMainAreasStart() {
		return mainAreasStart;
	}
	public void setMainAreasStart(String mainAreasStart){
		this.mainAreasStart = mainAreasStart;
	}
	public  String getMainAreasEnd() {
		return mainAreasEnd;
	}
	public void setMainAreasEnd(String mainAreasEnd){
		this.mainAreasEnd = mainAreasEnd;
	}
	public  String getSch_MainHigh_() {
		return sch_MainHigh_;
	}
	public void setSch_MainHigh_(String sch_MainHigh_){
		this.sch_MainHigh_ = sch_MainHigh_;
	}
	public  String getSch_MainHigh() {
		return sch_MainHigh;
	}
	public void setSch_MainHigh(String sch_MainHigh){
		this.sch_MainHigh = sch_MainHigh;
	}
	public  String getSchMainHigh_() {
		return schMainHigh_;
	}
	public void setSchMainHigh_(String schMainHigh_){
		this.schMainHigh_ = schMainHigh_;
	}
	public  String getMainHighStart() {
		return mainHighStart;
	}
	public void setMainHighStart(String mainHighStart){
		this.mainHighStart = mainHighStart;
	}
	public  String getMainHighEnd() {
		return mainHighEnd;
	}
	public void setMainHighEnd(String mainHighEnd){
		this.mainHighEnd = mainHighEnd;
	}
	public  String getSch_IsCertified_() {
		return sch_IsCertified_;
	}
	public void setSch_IsCertified_(String sch_IsCertified_){
		this.sch_IsCertified_ = sch_IsCertified_;
	}
	public  String getSch_IsCertified() {
		return sch_IsCertified;
	}
	public void setSch_IsCertified(String sch_IsCertified){
		this.sch_IsCertified = sch_IsCertified;
	}
	public  String getSchIsCertified_() {
		return schIsCertified_;
	}
	public void setSchIsCertified_(String schIsCertified_){
		this.schIsCertified_ = schIsCertified_;
	}
	public  String getIsCertifiedStart() {
		return isCertifiedStart;
	}
	public void setIsCertifiedStart(String isCertifiedStart){
		this.isCertifiedStart = isCertifiedStart;
	}
	public  String getIsCertifiedEnd() {
		return isCertifiedEnd;
	}
	public void setIsCertifiedEnd(String isCertifiedEnd){
		this.isCertifiedEnd = isCertifiedEnd;
	}
	public  String getSch_CertifiedPerson_() {
		return sch_CertifiedPerson_;
	}
	public void setSch_CertifiedPerson_(String sch_CertifiedPerson_){
		this.sch_CertifiedPerson_ = sch_CertifiedPerson_;
	}
	public  String getSch_CertifiedPerson() {
		return sch_CertifiedPerson;
	}
	public void setSch_CertifiedPerson(String sch_CertifiedPerson){
		this.sch_CertifiedPerson = sch_CertifiedPerson;
	}
	public  String getSchCertifiedPerson_() {
		return schCertifiedPerson_;
	}
	public void setSchCertifiedPerson_(String schCertifiedPerson_){
		this.schCertifiedPerson_ = schCertifiedPerson_;
	}
	public  String getCertifiedPersonStart() {
		return certifiedPersonStart;
	}
	public void setCertifiedPersonStart(String certifiedPersonStart){
		this.certifiedPersonStart = certifiedPersonStart;
	}
	public  String getCertifiedPersonEnd() {
		return certifiedPersonEnd;
	}
	public void setCertifiedPersonEnd(String certifiedPersonEnd){
		this.certifiedPersonEnd = certifiedPersonEnd;
	}
	public  String getSch_CertifiedType_() {
		return sch_CertifiedType_;
	}
	public void setSch_CertifiedType_(String sch_CertifiedType_){
		this.sch_CertifiedType_ = sch_CertifiedType_;
	}
	public  String getSch_CertifiedType() {
		return sch_CertifiedType;
	}
	public void setSch_CertifiedType(String sch_CertifiedType){
		this.sch_CertifiedType = sch_CertifiedType;
	}
	public  String getSchCertifiedType_() {
		return schCertifiedType_;
	}
	public void setSchCertifiedType_(String schCertifiedType_){
		this.schCertifiedType_ = schCertifiedType_;
	}
	public  String getCertifiedTypeStart() {
		return certifiedTypeStart;
	}
	public void setCertifiedTypeStart(String certifiedTypeStart){
		this.certifiedTypeStart = certifiedTypeStart;
	}
	public  String getCertifiedTypeEnd() {
		return certifiedTypeEnd;
	}
	public void setCertifiedTypeEnd(String certifiedTypeEnd){
		this.certifiedTypeEnd = certifiedTypeEnd;
	}
	public  String getSch_RoomDesc_() {
		return sch_RoomDesc_;
	}
	public void setSch_RoomDesc_(String sch_RoomDesc_){
		this.sch_RoomDesc_ = sch_RoomDesc_;
	}
	public  String getSch_RoomDesc() {
		return sch_RoomDesc;
	}
	public void setSch_RoomDesc(String sch_RoomDesc){
		this.sch_RoomDesc = sch_RoomDesc;
	}
	public  String getSchRoomDesc_() {
		return schRoomDesc_;
	}
	public void setSchRoomDesc_(String schRoomDesc_){
		this.schRoomDesc_ = schRoomDesc_;
	}
	public  String getRoomDescStart() {
		return roomDescStart;
	}
	public void setRoomDescStart(String roomDescStart){
		this.roomDescStart = roomDescStart;
	}
	public  String getRoomDescEnd() {
		return roomDescEnd;
	}
	public void setRoomDescEnd(String roomDescEnd){
		this.roomDescEnd = roomDescEnd;
	}
	public  String getSch_RoomCommonCode_() {
		return sch_RoomCommonCode_;
	}
	public void setSch_RoomCommonCode_(String sch_RoomCommonCode_){
		this.sch_RoomCommonCode_ = sch_RoomCommonCode_;
	}
	public  String getSch_RoomCommonCode() {
		return sch_RoomCommonCode;
	}
	public void setSch_RoomCommonCode(String sch_RoomCommonCode){
		this.sch_RoomCommonCode = sch_RoomCommonCode;
	}
	public  String getSchRoomCommonCode_() {
		return schRoomCommonCode_;
	}
	public void setSchRoomCommonCode_(String schRoomCommonCode_){
		this.schRoomCommonCode_ = schRoomCommonCode_;
	}
	public  String getRoomCommonCodeStart() {
		return roomCommonCodeStart;
	}
	public void setRoomCommonCodeStart(String roomCommonCodeStart){
		this.roomCommonCodeStart = roomCommonCodeStart;
	}
	public  String getRoomCommonCodeEnd() {
		return roomCommonCodeEnd;
	}
	public void setRoomCommonCodeEnd(String roomCommonCodeEnd){
		this.roomCommonCodeEnd = roomCommonCodeEnd;
	}
	public  String getSch_RoomTypeCode_() {
		return sch_RoomTypeCode_;
	}
	public void setSch_RoomTypeCode_(String sch_RoomTypeCode_){
		this.sch_RoomTypeCode_ = sch_RoomTypeCode_;
	}
	public  String getSch_RoomTypeCode() {
		return sch_RoomTypeCode;
	}
	public void setSch_RoomTypeCode(String sch_RoomTypeCode){
		this.sch_RoomTypeCode = sch_RoomTypeCode;
	}
	public  String getSchRoomTypeCode_() {
		return schRoomTypeCode_;
	}
	public void setSchRoomTypeCode_(String schRoomTypeCode_){
		this.schRoomTypeCode_ = schRoomTypeCode_;
	}
	public  String getRoomTypeCodeStart() {
		return roomTypeCodeStart;
	}
	public void setRoomTypeCodeStart(String roomTypeCodeStart){
		this.roomTypeCodeStart = roomTypeCodeStart;
	}
	public  String getRoomTypeCodeEnd() {
		return roomTypeCodeEnd;
	}
	public void setRoomTypeCodeEnd(String roomTypeCodeEnd){
		this.roomTypeCodeEnd = roomTypeCodeEnd;
	}
	public  String getSch_Att1_() {
		return sch_Att1_;
	}
	public void setSch_Att1_(String sch_Att1_){
		this.sch_Att1_ = sch_Att1_;
	}
	public  String getSch_Att1() {
		return sch_Att1;
	}
	public void setSch_Att1(String sch_Att1){
		this.sch_Att1 = sch_Att1;
	}
	public  String getSchAtt1_() {
		return schAtt1_;
	}
	public void setSchAtt1_(String schAtt1_){
		this.schAtt1_ = schAtt1_;
	}
	public  String getAtt1Start() {
		return att1Start;
	}
	public void setAtt1Start(String att1Start){
		this.att1Start = att1Start;
	}
	public  String getAtt1End() {
		return att1End;
	}
	public void setAtt1End(String att1End){
		this.att1End = att1End;
	}
	public  String getSch_Att2_() {
		return sch_Att2_;
	}
	public void setSch_Att2_(String sch_Att2_){
		this.sch_Att2_ = sch_Att2_;
	}
	public  String getSch_Att2() {
		return sch_Att2;
	}
	public void setSch_Att2(String sch_Att2){
		this.sch_Att2 = sch_Att2;
	}
	public  String getSchAtt2_() {
		return schAtt2_;
	}
	public void setSchAtt2_(String schAtt2_){
		this.schAtt2_ = schAtt2_;
	}
	public  String getAtt2Start() {
		return att2Start;
	}
	public void setAtt2Start(String att2Start){
		this.att2Start = att2Start;
	}
	public  String getAtt2End() {
		return att2End;
	}
	public void setAtt2End(String att2End){
		this.att2End = att2End;
	}
	public  String getSch_Att3_() {
		return sch_Att3_;
	}
	public void setSch_Att3_(String sch_Att3_){
		this.sch_Att3_ = sch_Att3_;
	}
	public  String getSch_Att3() {
		return sch_Att3;
	}
	public void setSch_Att3(String sch_Att3){
		this.sch_Att3 = sch_Att3;
	}
	public  String getSchAtt3_() {
		return schAtt3_;
	}
	public void setSchAtt3_(String schAtt3_){
		this.schAtt3_ = schAtt3_;
	}
	public  String getAtt3Start() {
		return att3Start;
	}
	public void setAtt3Start(String att3Start){
		this.att3Start = att3Start;
	}
	public  String getAtt3End() {
		return att3End;
	}
	public void setAtt3End(String att3End){
		this.att3End = att3End;
	}
	public  String getSch_Att4_() {
		return sch_Att4_;
	}
	public void setSch_Att4_(String sch_Att4_){
		this.sch_Att4_ = sch_Att4_;
	}
	public  String getSch_Att4() {
		return sch_Att4;
	}
	public void setSch_Att4(String sch_Att4){
		this.sch_Att4 = sch_Att4;
	}
	public  String getSchAtt4_() {
		return schAtt4_;
	}
	public void setSchAtt4_(String schAtt4_){
		this.schAtt4_ = schAtt4_;
	}
	public  String getAtt4Start() {
		return att4Start;
	}
	public void setAtt4Start(String att4Start){
		this.att4Start = att4Start;
	}
	public  String getAtt4End() {
		return att4End;
	}
	public void setAtt4End(String att4End){
		this.att4End = att4End;
	}
	public  String getSch_Att5_() {
		return sch_Att5_;
	}
	public void setSch_Att5_(String sch_Att5_){
		this.sch_Att5_ = sch_Att5_;
	}
	public  String getSch_Att5() {
		return sch_Att5;
	}
	public void setSch_Att5(String sch_Att5){
		this.sch_Att5 = sch_Att5;
	}
	public  String getSchAtt5_() {
		return schAtt5_;
	}
	public void setSchAtt5_(String schAtt5_){
		this.schAtt5_ = schAtt5_;
	}
	public  String getAtt5Start() {
		return att5Start;
	}
	public void setAtt5Start(String att5Start){
		this.att5Start = att5Start;
	}
	public  String getAtt5End() {
		return att5End;
	}
	public void setAtt5End(String att5End){
		this.att5End = att5End;
	}
	public  String getSch_Att6_() {
		return sch_Att6_;
	}
	public void setSch_Att6_(String sch_Att6_){
		this.sch_Att6_ = sch_Att6_;
	}
	public  String getSch_Att6() {
		return sch_Att6;
	}
	public void setSch_Att6(String sch_Att6){
		this.sch_Att6 = sch_Att6;
	}
	public  String getSchAtt6_() {
		return schAtt6_;
	}
	public void setSchAtt6_(String schAtt6_){
		this.schAtt6_ = schAtt6_;
	}
	public  String getAtt6Start() {
		return att6Start;
	}
	public void setAtt6Start(String att6Start){
		this.att6Start = att6Start;
	}
	public  String getAtt6End() {
		return att6End;
	}
	public void setAtt6End(String att6End){
		this.att6End = att6End;
	}
	public  Date getDateAtt1Start() {
		return dateAtt1Start;
	}
	public void setDateAtt1Start(Date dateAtt1Start){
		this.dateAtt1Start = dateAtt1Start;
	}
	public  Date getDateAtt1End() {
		return dateAtt1End;
	}
	public void setDateAtt1End(Date dateAtt1End){
		this.dateAtt1End = dateAtt1End;
	}
	public  Date getDateAtt2Start() {
		return dateAtt2Start;
	}
	public void setDateAtt2Start(Date dateAtt2Start){
		this.dateAtt2Start = dateAtt2Start;
	}
	public  Date getDateAtt2End() {
		return dateAtt2End;
	}
	public void setDateAtt2End(Date dateAtt2End){
		this.dateAtt2End = dateAtt2End;
	}
	public  String getSch_Remark_() {
		return sch_Remark_;
	}
	public void setSch_Remark_(String sch_Remark_){
		this.sch_Remark_ = sch_Remark_;
	}
	public  String getSch_Remark() {
		return sch_Remark;
	}
	public void setSch_Remark(String sch_Remark){
		this.sch_Remark = sch_Remark;
	}
	public  String getSchRemark_() {
		return schRemark_;
	}
	public void setSchRemark_(String schRemark_){
		this.schRemark_ = schRemark_;
	}
	public  String getRemarkStart() {
		return remarkStart;
	}
	public void setRemarkStart(String remarkStart){
		this.remarkStart = remarkStart;
	}
	public  String getRemarkEnd() {
		return remarkEnd;
	}
	public void setRemarkEnd(String remarkEnd){
		this.remarkEnd = remarkEnd;
	}

}
