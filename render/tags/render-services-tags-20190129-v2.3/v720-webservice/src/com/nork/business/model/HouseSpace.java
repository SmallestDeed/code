package com.nork.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: HouseSpace.java 
 * @Package com.nork.business.model
 * @Description:业务-房型空间定义
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:58:14
 * @version V1.0   
 */
public class HouseSpace  extends Mapper implements Serializable{
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
	private Integer livingId;
	public Integer getLivingId() {
		return livingId;
	}

	public void setLivingId(Integer livingId) {
		this.livingId = livingId;
	}

	/**  创建时间  **/
	private Date gmtCreateStart;
	/**  创建时间  **/
	private Date gmtCreateEnd;
	private Date gmtModifiedStart;
	private Date gmtModifiedEnd;
	public Date getGmtModifiedStart() {
		return gmtModifiedStart;
	}

	public void setGmtModifiedStart(Date gmtModifiedStart) {
		this.gmtModifiedStart = gmtModifiedStart;
	}

	public Date getGmtModifiedEnd() {
		return gmtModifiedEnd;
	}

	public void setGmtModifiedEnd(Date gmtModifiedEnd) {
		this.gmtModifiedEnd = gmtModifiedEnd;
	}

	public Date getGmtCreateStart() {
		return gmtCreateStart;
	}

	public void setGmtCreateStart(Date gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}

	public Date getGmtCreateEnd() {
		return gmtCreateEnd;
	}

	public void setGmtCreateEnd(Date gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
	}

	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  空间类型  **/
	private String spaceType;
	/**  空间名称  **/
	private String spaceName;
	/**  相对门的位置X坐标(单位为空间数量)  **/
	private Integer positionx;
	/**  相对门的位置Y坐标(单位为空间数量)  **/
	private Integer positiony;
	/**  是否是主要空间  **/
	private String isMain;
	/**  空间实际形状  **/
	private String spaceShape;
	/**  规划空间形状  **/
	private String logicShape;
	/**  是否存在  **/
	private Integer isExists;
	/**  上1坐标  **/
	private Integer aboveX1;
	/**  上2坐标  **/
	private Integer aboveX2;
	/**  下1坐标  **/
	private Integer belowX1;
	/**  下2坐标  **/
	private Integer belowX2;
	/**  占地长度  **/
	private String maxLength;
	/**  占地宽度  **/
	private String maxWidth;
	/**  占地面积  **/
	private String maxAreas;
	/**  主体长度  **/
	private String mixLength;
	/**  主体宽度  **/
	private String mixWidth;
	/**  主体面积  **/
	private String mixAreas;
	/**  主体高度  **/
	private String mixHigh;
	/**  附属空间id  **/
	private Integer spacePid;
	/**  空间符号  **/
	private String spaceSign;
	/**  房间id  **/
	private Integer houseId;
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
	private Date spaceModified;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  通用空间id  **/
	private Integer spaceId;
	/**  标准空间id  **/
	private Integer standardSpaceId;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;
	
	/**  重置定位找他默认为0  **/
	private Integer resetLocationState;
	/**  不需重置定位状态默认为0，  **/
	private Integer unresetLocationState;
	
	public Integer getResetLocationState() {
		return resetLocationState;
	}

	public void setResetLocationState(Integer resetLocationState) {
		this.resetLocationState = resetLocationState;
	}

	public Integer getUnresetLocationState() {
		return unresetLocationState;
	}

	public void setUnresetLocationState(Integer unresetLocationState) {
		this.unresetLocationState = unresetLocationState;
	}

	//空间图
	private String picPath;
	//空间类型名称
	private String spaceCommonName;
	//户型名称
	private String houseName;
	

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
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
	public  String getSpaceType() {
		return spaceType;
	}
	public void setSpaceType(String spaceType){
		this.spaceType = spaceType;
	}
	public  String getSpaceName() {
		return spaceName;
	}
	public void setSpaceName(String spaceName){
		this.spaceName = spaceName;
	}
	public  Integer getPositionx() {
		return positionx;
	}
	public void setPositionx(Integer positionx){
		this.positionx = positionx;
	}
	public  Integer getPositiony() {
		return positiony;
	}
	public void setPositiony(Integer positiony){
		this.positiony = positiony;
	}
	public  String getIsMain() {
		return isMain;
	}
	public void setIsMain(String isMain){
		this.isMain = isMain;
	}
	public  String getSpaceShape() {
		return spaceShape;
	}
	public void setSpaceShape(String spaceShape){
		this.spaceShape = spaceShape;
	}
	public  String getLogicShape() {
		return logicShape;
	}
	public void setLogicShape(String logicShape){
		this.logicShape = logicShape;
	}
	public  Integer getIsExists() {
		return isExists;
	}
	public void setIsExists(Integer isExists){
		this.isExists = isExists;
	}
	public  Integer getAboveX1() {
		return aboveX1;
	}
	public void setAboveX1(Integer aboveX1){
		this.aboveX1 = aboveX1;
	}
	public  Integer getAboveX2() {
		return aboveX2;
	}
	public void setAboveX2(Integer aboveX2){
		this.aboveX2 = aboveX2;
	}
	public  Integer getBelowX1() {
		return belowX1;
	}
	public void setBelowX1(Integer belowX1){
		this.belowX1 = belowX1;
	}
	public  Integer getBelowX2() {
		return belowX2;
	}
	public void setBelowX2(Integer belowX2){
		this.belowX2 = belowX2;
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
	public  String getMixLength() {
		return mixLength;
	}
	public void setMixLength(String mixLength){
		this.mixLength = mixLength;
	}
	public  String getMixWidth() {
		return mixWidth;
	}
	public void setMixWidth(String mixWidth){
		this.mixWidth = mixWidth;
	}
	public  String getMixAreas() {
		return mixAreas;
	}
	public void setMixAreas(String mixAreas){
		this.mixAreas = mixAreas;
	}
	public  String getMixHigh() {
		return mixHigh;
	}
	public void setMixHigh(String mixHigh){
		this.mixHigh = mixHigh;
	}
	public  Integer getSpacePid() {
		return spacePid;
	}
	public void setSpacePid(Integer spacePid){
		this.spacePid = spacePid;
	}
	public  String getSpaceSign() {
		return spaceSign;
	}
	public void setSpaceSign(String spaceSign){
		this.spaceSign = spaceSign;
	}
	public  Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId){
		this.houseId = houseId;
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
	public Date getSpaceModified() {
		return spaceModified;
	}
	public void setSpaceModified(Date spaceModified) {
		this.spaceModified = spaceModified;
	}
	public  Date getDateAtt2() {
		return dateAtt2;
	}
	public void setDateAtt2(Date dateAtt2){
		this.dateAtt2 = dateAtt2;
	}
	public  Integer getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(Integer spaceId){
		this.spaceId = spaceId;
	}
	
	public Integer getStandardSpaceId() {
		return standardSpaceId;
	}

	public void setStandardSpaceId(Integer standardSpaceId) {
		this.standardSpaceId = standardSpaceId;
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

    public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	

    public String getSpaceCommonName() {
		return spaceCommonName;
	}

	public void setSpaceCommonName(String spaceCommonName) {
		this.spaceCommonName = spaceCommonName;
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
        HouseSpace other = (HouseSpace) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getSpaceType() == null ? other.getSpaceType() == null : this.getSpaceType().equals(other.getSpaceType()))
            && (this.getSpaceName() == null ? other.getSpaceName() == null : this.getSpaceName().equals(other.getSpaceName()))
            && (this.getPositionx() == null ? other.getPositionx() == null : this.getPositionx().equals(other.getPositionx()))
            && (this.getPositiony() == null ? other.getPositiony() == null : this.getPositiony().equals(other.getPositiony()))
            && (this.getIsMain() == null ? other.getIsMain() == null : this.getIsMain().equals(other.getIsMain()))
            && (this.getSpaceShape() == null ? other.getSpaceShape() == null : this.getSpaceShape().equals(other.getSpaceShape()))
            && (this.getLogicShape() == null ? other.getLogicShape() == null : this.getLogicShape().equals(other.getLogicShape()))
            && (this.getIsExists() == null ? other.getIsExists() == null : this.getIsExists().equals(other.getIsExists()))
            && (this.getAboveX1() == null ? other.getAboveX1() == null : this.getAboveX1().equals(other.getAboveX1()))
            && (this.getAboveX2() == null ? other.getAboveX2() == null : this.getAboveX2().equals(other.getAboveX2()))
            && (this.getBelowX1() == null ? other.getBelowX1() == null : this.getBelowX1().equals(other.getBelowX1()))
            && (this.getBelowX2() == null ? other.getBelowX2() == null : this.getBelowX2().equals(other.getBelowX2()))
            && (this.getMaxLength() == null ? other.getMaxLength() == null : this.getMaxLength().equals(other.getMaxLength()))
            && (this.getMaxWidth() == null ? other.getMaxWidth() == null : this.getMaxWidth().equals(other.getMaxWidth()))
            && (this.getMaxAreas() == null ? other.getMaxAreas() == null : this.getMaxAreas().equals(other.getMaxAreas()))
            && (this.getMixLength() == null ? other.getMixLength() == null : this.getMixLength().equals(other.getMixLength()))
            && (this.getMixWidth() == null ? other.getMixWidth() == null : this.getMixWidth().equals(other.getMixWidth()))
            && (this.getMixAreas() == null ? other.getMixAreas() == null : this.getMixAreas().equals(other.getMixAreas()))
            && (this.getMixHigh() == null ? other.getMixHigh() == null : this.getMixHigh().equals(other.getMixHigh()))
            && (this.getSpacePid() == null ? other.getSpacePid() == null : this.getSpacePid().equals(other.getSpacePid()))
            && (this.getSpaceSign() == null ? other.getSpaceSign() == null : this.getSpaceSign().equals(other.getSpaceSign()))
            && (this.getHouseId() == null ? other.getHouseId() == null : this.getHouseId().equals(other.getHouseId()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
            && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
            && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
            && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
            && (this.getSpaceModified() == null ? other.getSpaceModified() == null : this.getSpaceModified().equals(other.getSpaceModified()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getSpaceId() == null ? other.getSpaceId() == null : this.getSpaceId().equals(other.getSpaceId()))
            && (this.getStandardSpaceId() == null ? other.getStandardSpaceId() == null : this.getStandardSpaceId().equals(other.getStandardSpaceId()))
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
        result = prime * result + ((getSpaceType() == null) ? 0 : getSpaceType().hashCode());
        result = prime * result + ((getSpaceName() == null) ? 0 : getSpaceName().hashCode());
        result = prime * result + ((getPositionx() == null) ? 0 : getPositionx().hashCode());
        result = prime * result + ((getPositiony() == null) ? 0 : getPositiony().hashCode());
        result = prime * result + ((getIsMain() == null) ? 0 : getIsMain().hashCode());
        result = prime * result + ((getSpaceShape() == null) ? 0 : getSpaceShape().hashCode());
        result = prime * result + ((getLogicShape() == null) ? 0 : getLogicShape().hashCode());
        result = prime * result + ((getIsExists() == null) ? 0 : getIsExists().hashCode());
        result = prime * result + ((getAboveX1() == null) ? 0 : getAboveX1().hashCode());
        result = prime * result + ((getAboveX2() == null) ? 0 : getAboveX2().hashCode());
        result = prime * result + ((getBelowX1() == null) ? 0 : getBelowX1().hashCode());
        result = prime * result + ((getBelowX2() == null) ? 0 : getBelowX2().hashCode());
        result = prime * result + ((getMaxLength() == null) ? 0 : getMaxLength().hashCode());
        result = prime * result + ((getMaxWidth() == null) ? 0 : getMaxWidth().hashCode());
        result = prime * result + ((getMaxAreas() == null) ? 0 : getMaxAreas().hashCode());
        result = prime * result + ((getMixLength() == null) ? 0 : getMixLength().hashCode());
        result = prime * result + ((getMixWidth() == null) ? 0 : getMixWidth().hashCode());
        result = prime * result + ((getMixAreas() == null) ? 0 : getMixAreas().hashCode());
        result = prime * result + ((getMixHigh() == null) ? 0 : getMixHigh().hashCode());
        result = prime * result + ((getSpacePid() == null) ? 0 : getSpacePid().hashCode());
        result = prime * result + ((getSpaceSign() == null) ? 0 : getSpaceSign().hashCode());
        result = prime * result + ((getHouseId() == null) ? 0 : getHouseId().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getSpaceModified() == null) ? 0 : getSpaceModified().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getSpaceId() == null) ? 0 : getSpaceId().hashCode());
        result = prime * result + ((getStandardSpaceId() == null) ? 0 : getStandardSpaceId().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public HouseSpace copy(){
       HouseSpace obj =  new HouseSpace();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setSpaceType(this.spaceType);
       obj.setSpaceName(this.spaceName);
       obj.setPositionx(this.positionx);
       obj.setPositiony(this.positiony);
       obj.setIsMain(this.isMain);
       obj.setSpaceShape(this.spaceShape);
       obj.setLogicShape(this.logicShape);
       obj.setIsExists(this.isExists);
       obj.setAboveX1(this.aboveX1);
       obj.setAboveX2(this.aboveX2);
       obj.setBelowX1(this.belowX1);
       obj.setBelowX2(this.belowX2);
       obj.setMaxLength(this.maxLength);
       obj.setMaxWidth(this.maxWidth);
       obj.setMaxAreas(this.maxAreas);
       obj.setMixLength(this.mixLength);
       obj.setMixWidth(this.mixWidth);
       obj.setMixAreas(this.mixAreas);
       obj.setMixHigh(this.mixHigh);
       obj.setSpacePid(this.spacePid);
       obj.setSpaceSign(this.spaceSign);
       obj.setHouseId(this.houseId);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setAtt3(this.att3);
       obj.setAtt4(this.att4);
       obj.setAtt5(this.att5);
       obj.setAtt6(this.att6);
       obj.setSpaceModified(this.spaceModified);
       obj.setDateAtt2(this.dateAtt2);
       obj.setSpaceId(this.spaceId);
       obj.setStandardSpaceId(this.standardSpaceId);
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
       map.put("spaceType",this.spaceType);
       map.put("spaceName",this.spaceName);
       map.put("positionx",this.positionx);
       map.put("positiony",this.positiony);
       map.put("isMain",this.isMain);
       map.put("spaceShape",this.spaceShape);
       map.put("logicShape",this.logicShape);
       map.put("isExists",this.isExists);
       map.put("aboveX1",this.aboveX1);
       map.put("aboveX2",this.aboveX2);
       map.put("belowX1",this.belowX1);
       map.put("belowX2",this.belowX2);
       map.put("maxLength",this.maxLength);
       map.put("maxWidth",this.maxWidth);
       map.put("maxAreas",this.maxAreas);
       map.put("mixLength",this.mixLength);
       map.put("mixWidth",this.mixWidth);
       map.put("mixAreas",this.mixAreas);
       map.put("mixHigh",this.mixHigh);
       map.put("spacePid",this.spacePid);
       map.put("spaceSign",this.spaceSign);
       map.put("houseId",this.houseId);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("att3",this.att3);
       map.put("att4",this.att4);
       map.put("att5",this.att5);
       map.put("att6",this.att6);
       map.put("spaceModified",this.spaceModified);
       map.put("dateAtt2",this.dateAtt2);
       map.put("spaceId",this.spaceId);
       map.put("standardSpaceId",this.standardSpaceId);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);

       return map;
    }
}
