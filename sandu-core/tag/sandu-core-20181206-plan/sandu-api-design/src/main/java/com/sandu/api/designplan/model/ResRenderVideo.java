package com.sandu.api.designplan.model;



import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**   
 * @Title: ResRenderVideo.java 
 * @Package com.nork.system.model
 * @Description:渲染视频资源库-渲染视频资源表
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 22:42:48
 * @version V1.0   
 */
public class ResRenderVideo implements Serializable{
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
	
	
	private Integer sourcePlanId;
	private Integer templateId;
	
	/**
     * 平台Id
     * add by chenm 20180509
     */
    private Integer platformId;
    
    

	public Integer getPlatformId() {
      return platformId;
    }
  
    public void setPlatformId(Integer platformId) {
      this.platformId = platformId;
    }

    public Integer getSourcePlanId() {
		return sourcePlanId;
	}

	public void setSourcePlanId(Integer sourcePlanId) {
		this.sourcePlanId = sourcePlanId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

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
        ResRenderVideo other = (ResRenderVideo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
            && (this.getVideoCode() == null ? other.getVideoCode() == null : this.getVideoCode().equals(other.getVideoCode()))
            && (this.getVideoName() == null ? other.getVideoName() == null : this.getVideoName().equals(other.getVideoName()))
            && (this.getVideoFileName() == null ? other.getVideoFileName() == null : this.getVideoFileName().equals(other.getVideoFileName()))
            && (this.getVideoType() == null ? other.getVideoType() == null : this.getVideoType().equals(other.getVideoType()))
            && (this.getVideoSize() == null ? other.getVideoSize() == null : this.getVideoSize().equals(other.getVideoSize()))
            && (this.getVideoSuffix() == null ? other.getVideoSuffix() == null : this.getVideoSuffix().equals(other.getVideoSuffix()))
            && (this.getVideoLevel() == null ? other.getVideoLevel() == null : this.getVideoLevel().equals(other.getVideoLevel()))
            && (this.getVideoFormat() == null ? other.getVideoFormat() == null : this.getVideoFormat().equals(other.getVideoFormat()))
            && (this.getVideoPath() == null ? other.getVideoPath() == null : this.getVideoPath().equals(other.getVideoPath()))
            && (this.getVideoDesc() == null ? other.getVideoDesc() == null : this.getVideoDesc().equals(other.getVideoDesc()))
            && (this.getVideoOrdering() == null ? other.getVideoOrdering() == null : this.getVideoOrdering().equals(other.getVideoOrdering()))
            && (this.getFileKey() == null ? other.getFileKey() == null : this.getFileKey().equals(other.getFileKey()))
            && (this.getFileKeys() == null ? other.getFileKeys() == null : this.getFileKeys().equals(other.getFileKeys()))
            && (this.getSmallVideoInfo() == null ? other.getSmallVideoInfo() == null : this.getSmallVideoInfo().equals(other.getSmallVideoInfo()))
            && (this.getRenderingType() == null ? other.getRenderingType() == null : this.getRenderingType().equals(other.getRenderingType()))
            && (this.getSequence() == null ? other.getSequence() == null : this.getSequence().equals(other.getSequence()))
            && (this.getTaskCreateTime() == null ? other.getTaskCreateTime() == null : this.getTaskCreateTime().equals(other.getTaskCreateTime()))
            && (this.getSysTaskPicId() == null ? other.getSysTaskPicId() == null : this.getSysTaskPicId().equals(other.getSysTaskPicId()))
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
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getVideoCode() == null) ? 0 : getVideoCode().hashCode());
        result = prime * result + ((getVideoName() == null) ? 0 : getVideoName().hashCode());
        result = prime * result + ((getVideoFileName() == null) ? 0 : getVideoFileName().hashCode());
        result = prime * result + ((getVideoType() == null) ? 0 : getVideoType().hashCode());
        result = prime * result + ((getVideoSize() == null) ? 0 : getVideoSize().hashCode());
        result = prime * result + ((getVideoSuffix() == null) ? 0 : getVideoSuffix().hashCode());
        result = prime * result + ((getVideoLevel() == null) ? 0 : getVideoLevel().hashCode());
        result = prime * result + ((getVideoFormat() == null) ? 0 : getVideoFormat().hashCode());
        result = prime * result + ((getVideoPath() == null) ? 0 : getVideoPath().hashCode());
        result = prime * result + ((getVideoDesc() == null) ? 0 : getVideoDesc().hashCode());
        result = prime * result + ((getVideoOrdering() == null) ? 0 : getVideoOrdering().hashCode());
        result = prime * result + ((getFileKey() == null) ? 0 : getFileKey().hashCode());
        result = prime * result + ((getFileKeys() == null) ? 0 : getFileKeys().hashCode());
        result = prime * result + ((getSmallVideoInfo() == null) ? 0 : getSmallVideoInfo().hashCode());
        result = prime * result + ((getRenderingType() == null) ? 0 : getRenderingType().hashCode());
        result = prime * result + ((getSequence() == null) ? 0 : getSequence().hashCode());
        result = prime * result + ((getTaskCreateTime() == null) ? 0 : getTaskCreateTime().hashCode());
        result = prime * result + ((getSysTaskPicId() == null) ? 0 : getSysTaskPicId().hashCode());
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
    public ResRenderVideo copy(){
       ResRenderVideo obj =  new ResRenderVideo();
       obj.setBusinessId(this.businessId);
       obj.setVideoCode(this.videoCode);
       obj.setVideoName(this.videoName);
       obj.setVideoFileName(this.videoFileName);
       obj.setVideoType(this.videoType);
       obj.setVideoSize(this.videoSize);
       obj.setVideoSuffix(this.videoSuffix);
       obj.setVideoLevel(this.videoLevel);
       obj.setVideoFormat(this.videoFormat);
       obj.setVideoPath(this.videoPath);
       obj.setVideoDesc(this.videoDesc);
       obj.setVideoOrdering(this.videoOrdering);
       obj.setFileKey(this.fileKey);
       obj.setFileKeys(this.fileKeys);
       obj.setSmallVideoInfo(this.smallVideoInfo);
       obj.setRenderingType(this.renderingType);
       obj.setSequence(this.sequence);
       obj.setTaskCreateTime(this.taskCreateTime);
       obj.setSysTaskPicId(this.sysTaskPicId);
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
       map.put("businessId",this.businessId);
       map.put("videoCode",this.videoCode);
       map.put("videoName",this.videoName);
       map.put("videoFileName",this.videoFileName);
       map.put("videoType",this.videoType);
       map.put("videoSize",this.videoSize);
       map.put("videoSuffix",this.videoSuffix);
       map.put("videoLevel",this.videoLevel);
       map.put("videoFormat",this.videoFormat);
       map.put("videoPath",this.videoPath);
       map.put("videoDesc",this.videoDesc);
       map.put("videoOrdering",this.videoOrdering);
       map.put("fileKey",this.fileKey);
       map.put("fileKeys",this.fileKeys);
       map.put("smallVideoInfo",this.smallVideoInfo);
       map.put("renderingType",this.renderingType);
       map.put("sequence",this.sequence);
       map.put("taskCreateTime",this.taskCreateTime);
       map.put("sysTaskPicId",this.sysTaskPicId);
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
