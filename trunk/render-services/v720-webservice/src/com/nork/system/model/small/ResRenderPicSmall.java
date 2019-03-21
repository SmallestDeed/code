package com.nork.system.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: ResRenderPic.java 
 * @Package com.nork.system.model.small
 * @Description:渲染图片资源库-渲染图片资源库
 * @createAuthor pandajun 
 * @CreateDate 2017-03-22 14:39:08
 * @version V1.0   
 */
public class ResRenderPicSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  设计方案  **/
	private Integer businessId;
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
	private Integer picWeight;
	/**  图片高  **/
	private Integer picHigh;
	/**  图片后缀  **/
	private String picSuffix;
	/**  图片级别  **/
	private Integer picLevel;
	/**  图片格式  **/
	private String picFormat;
	/**  图片路径  **/
	private String picPath;
	/**  图片描述  **/
	private String picDesc;
	/**  图片排序  **/
	private Integer picOrdering;
	/**  key标识  **/
	private String fileKey;
	/**  key标识（多个）  **/
	private String fileKeys;
	/**  图片对应的缩略图id信息  **/
	private String smallPicInfo;
	/**  渲染图视角  **/
	private Integer viewPoint;
	/**  渲染图场景  **/
	private Integer scene;
	/**  渲染类型  **/
	private Integer renderingType;
	/**  图片文件排序序号  **/
	private Integer sequence;
	/**  全景图地址  **/
	private String panoPath;
	/**  渲染任务创建时间  **/
	private Date taskCreateTime;
	/**  原图id  **/
	private Integer pid;
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

	public  Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId){
		this.businessId = businessId;
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
	public  Integer getPicWeight() {
		return picWeight;
	}
	public void setPicWeight(Integer picWeight){
		this.picWeight = picWeight;
	}
	public  Integer getPicHigh() {
		return picHigh;
	}
	public void setPicHigh(Integer picHigh){
		this.picHigh = picHigh;
	}
	public  String getPicSuffix() {
		return picSuffix;
	}
	public void setPicSuffix(String picSuffix){
		this.picSuffix = picSuffix;
	}
	public  Integer getPicLevel() {
		return picLevel;
	}
	public void setPicLevel(Integer picLevel){
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
	public  Integer getPicOrdering() {
		return picOrdering;
	}
	public void setPicOrdering(Integer picOrdering){
		this.picOrdering = picOrdering;
	}
	public  String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey){
		this.fileKey = fileKey;
	}
	public  String getFileKeys() {
		return fileKeys;
	}
	public void setFileKeys(String fileKeys){
		this.fileKeys = fileKeys;
	}
	public  String getSmallPicInfo() {
		return smallPicInfo;
	}
	public void setSmallPicInfo(String smallPicInfo){
		this.smallPicInfo = smallPicInfo;
	}
	public  Integer getViewPoint() {
		return viewPoint;
	}
	public void setViewPoint(Integer viewPoint){
		this.viewPoint = viewPoint;
	}
	public  Integer getScene() {
		return scene;
	}
	public void setScene(Integer scene){
		this.scene = scene;
	}
	public  Integer getRenderingType() {
		return renderingType;
	}
	public void setRenderingType(Integer renderingType){
		this.renderingType = renderingType;
	}
	public  Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence){
		this.sequence = sequence;
	}
	public  String getPanoPath() {
		return panoPath;
	}
	public void setPanoPath(String panoPath){
		this.panoPath = panoPath;
	}
	public  Date getTaskCreateTime() {
		return taskCreateTime;
	}
	public void setTaskCreateTime(Date taskCreateTime){
		this.taskCreateTime = taskCreateTime;
	}
	public  Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid){
		this.pid = pid;
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
