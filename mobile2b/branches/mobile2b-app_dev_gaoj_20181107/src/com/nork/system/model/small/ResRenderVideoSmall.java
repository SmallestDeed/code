package com.nork.system.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: ResRenderVideo.java 
 * @Package com.nork.system.model.small
 * @Description:渲染视频资源库-渲染视频资源表
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 22:42:48
 * @version V1.0   
 */
public class ResRenderVideoSmall  implements Serializable{
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
	/**  视频编码  **/
	private String videoCode;
	/**  视频名称  **/
	private String videoName;
	/**  视频文件名称  **/
	private String videoFileName;
	/**  视频类型  **/
	private String videoType;
	/**  视频大小  **/
	private Integer videoSize;
	/**  视频后缀  **/
	private String videoSuffix;
	/**  视频级别  **/
	private Integer videoLevel;
	/**  视频格式  **/
	private String videoFormat;
	/**  视频路径  **/
	private String videoPath;
	/**  视频描述  **/
	private String videoDesc;
	/**  视频排序  **/
	private Integer videoOrdering;
	/**  key标识  **/
	private String fileKey;
	/**  key标识（多个）  **/
	private String fileKeys;
	/**  视频对应的封面图id信息  **/
	private String smallVideoInfo;
	/**  渲染类型  **/
	private Integer renderingType;
	/**  视频文件排序序号  **/
	private Integer sequence;
	/**  渲染任务创建时间  **/
	private Date taskCreateTime;
	/**  关联封面图Id  **/
	private Integer sysTaskPicId;
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
	public  String getVideoCode() {
		return videoCode;
	}
	public void setVideoCode(String videoCode){
		this.videoCode = videoCode;
	}
	public  String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName){
		this.videoName = videoName;
	}
	public  String getVideoFileName() {
		return videoFileName;
	}
	public void setVideoFileName(String videoFileName){
		this.videoFileName = videoFileName;
	}
	public  String getVideoType() {
		return videoType;
	}
	public void setVideoType(String videoType){
		this.videoType = videoType;
	}
	public  Integer getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(Integer videoSize){
		this.videoSize = videoSize;
	}
	public  String getVideoSuffix() {
		return videoSuffix;
	}
	public void setVideoSuffix(String videoSuffix){
		this.videoSuffix = videoSuffix;
	}
	public  Integer getVideoLevel() {
		return videoLevel;
	}
	public void setVideoLevel(Integer videoLevel){
		this.videoLevel = videoLevel;
	}
	public  String getVideoFormat() {
		return videoFormat;
	}
	public void setVideoFormat(String videoFormat){
		this.videoFormat = videoFormat;
	}
	public  String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath){
		this.videoPath = videoPath;
	}
	public  String getVideoDesc() {
		return videoDesc;
	}
	public void setVideoDesc(String videoDesc){
		this.videoDesc = videoDesc;
	}
	public  Integer getVideoOrdering() {
		return videoOrdering;
	}
	public void setVideoOrdering(Integer videoOrdering){
		this.videoOrdering = videoOrdering;
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
	public  String getSmallVideoInfo() {
		return smallVideoInfo;
	}
	public void setSmallVideoInfo(String smallVideoInfo){
		this.smallVideoInfo = smallVideoInfo;
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
	public  Date getTaskCreateTime() {
		return taskCreateTime;
	}
	public void setTaskCreateTime(Date taskCreateTime){
		this.taskCreateTime = taskCreateTime;
	}
	public  Integer getSysTaskPicId() {
		return sysTaskPicId;
	}
	public void setSysTaskPicId(Integer sysTaskPicId){
		this.sysTaskPicId = sysTaskPicId;
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
