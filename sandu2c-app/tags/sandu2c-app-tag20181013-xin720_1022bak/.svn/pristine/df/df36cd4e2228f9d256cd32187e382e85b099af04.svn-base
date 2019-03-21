package com.sandu.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.sandu.common.model.Mapper;

import java.util.HashMap;


/**   
 * @Title: SysUserMessage.java 
 * @Package com.nork.system.model
 * @Description:系统-我的消息表
 * @createAuthor pandajun 
 * @CreateDate 2017-12-21 14:50:38
 * @version V1.0   
 */
public class SysUserMessage  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    //平台业务类型
    private String platformBussinessType;
    
    public String getPlatformBussinessType() {
		return platformBussinessType;
	}

	public void setPlatformBussinessType(String platformBussinessType) {
		this.platformBussinessType = platformBussinessType;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  消息标题  **/
	private String title;
	/**  消息内容  **/
	private String content;
	/**  消息类型（1:方案渲染,2:系统消息，3:活动推送）  **/
	private Integer messageType;
	/**  业务id  **/
	private Integer taskId;
	/**  用户id  **/
	private Integer userId;
	/**  是否已读（0:未读，1:已读）  **/
	private Integer isRead;
	/**  状态（0:失败，1:成功）  **/
	private Integer status;
	/**  失败原因  **/
	private String failingReason;
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
	/**  是否删除（0:否，1:是）  **/
	private Integer isDeleted;
	/**  备注  **/
	private String remark;
	private Integer platformId;
	
	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public  String getTitle() {
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public  String getContent() {
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public  Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType){
		this.messageType = messageType;
	}
	public  Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId){
		this.taskId = taskId;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead){
		this.isRead = isRead;
	}
	public  Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public  String getFailingReason() {
		return failingReason;
	}
	public void setFailingReason(String failingReason){
		this.failingReason = failingReason;
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
        SysUserMessage other = (SysUserMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getMessageType() == null ? other.getMessageType() == null : this.getMessageType().equals(other.getMessageType()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIsRead() == null ? other.getIsRead() == null : this.getIsRead().equals(other.getIsRead()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getFailingReason() == null ? other.getFailingReason() == null : this.getFailingReason().equals(other.getFailingReason()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getMessageType() == null) ? 0 : getMessageType().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIsRead() == null) ? 0 : getIsRead().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getFailingReason() == null) ? 0 : getFailingReason().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public SysUserMessage copy(){
       SysUserMessage obj =  new SysUserMessage();
       obj.setTitle(this.title);
       obj.setContent(this.content);
       obj.setMessageType(this.messageType);
       obj.setTaskId(this.taskId);
       obj.setUserId(this.userId);
       obj.setIsRead(this.isRead);
       obj.setStatus(this.status);
       obj.setFailingReason(this.failingReason);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("title",this.title);
       map.put("content",this.content);
       map.put("messageType",this.messageType);
       map.put("businessId",this.taskId);
       map.put("userId",this.userId);
       map.put("isRead",this.isRead);
       map.put("status",this.status);
       map.put("failingReason",this.failingReason);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("remark",this.remark);

       return map;
    }

    public enum MESSAGE_TYPE{
    	DESIGN_PLAN_RENDER,PAY_ORDER,SYSTEM
	}

	@Override
	public String toString() {
		return "SysUserMessage{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", messageType=" + messageType +
				", taskId=" + taskId +
				", userId=" + userId +
				", isRead=" + isRead +
				", status=" + status +
				", failingReason='" + failingReason + '\'' +
				", sysCode='" + sysCode + '\'' +
				", creator='" + creator + '\'' +
				", gmtCreate=" + gmtCreate +
				", modifier='" + modifier + '\'' +
				", gmtModified=" + gmtModified +
				", isDeleted=" + isDeleted +
				", remark='" + remark + '\'' +
				'}';
	}
}
