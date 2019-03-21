package com.nork.render.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: RenderTask.java 
 * @Package com.nork.render.model
 * @Description:渲染-渲染任务
 * @createAuthor pandajun 
 * @CreateDate 2017-01-17 20:31:06
 * @version V1.0   
 */
public class RenderTask  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  渲染主机Id  **/
	private Integer hostId;
	/**  任务id  **/
	private Integer taskId;
	/**  任务状态  **/
	private Integer status;
	/**  任务开始时间  **/
	private Date gmtTaskStart;
	/**  任务结束时间  **/
	private Date gmtTaskEnd;
	/**  渲染开始时间  **/
	private Date gmtRenderStart;
	/**  渲染结束时间  **/
	private Date gmtRenderEnd;
	/**  任务权重  **/
	private Integer taskWeight;
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

	public  Integer getHostId() {
		return hostId;
	}
	public void setHostId(Integer hostId){
		this.hostId = hostId;
	}
	public  Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId){
		this.taskId = taskId;
	}
	public  Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public  Date getGmtTaskStart() {
		return gmtTaskStart;
	}
	public void setGmtTaskStart(Date gmtTaskStart){
		this.gmtTaskStart = gmtTaskStart;
	}
	public  Date getGmtTaskEnd() {
		return gmtTaskEnd;
	}
	public void setGmtTaskEnd(Date gmtTaskEnd){
		this.gmtTaskEnd = gmtTaskEnd;
	}
	public  Date getGmtRenderStart() {
		return gmtRenderStart;
	}
	public void setGmtRenderStart(Date gmtRenderStart){
		this.gmtRenderStart = gmtRenderStart;
	}
	public  Date getGmtRenderEnd() {
		return gmtRenderEnd;
	}
	public void setGmtRenderEnd(Date gmtRenderEnd){
		this.gmtRenderEnd = gmtRenderEnd;
	}
	public  Integer getTaskWeight() {
		return taskWeight;
	}
	public void setTaskWeight(Integer taskWeight){
		this.taskWeight = taskWeight;
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
        RenderTask other = (RenderTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHostId() == null ? other.getHostId() == null : this.getHostId().equals(other.getHostId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getGmtTaskStart() == null ? other.getGmtTaskStart() == null : this.getGmtTaskStart().equals(other.getGmtTaskStart()))
            && (this.getGmtTaskEnd() == null ? other.getGmtTaskEnd() == null : this.getGmtTaskEnd().equals(other.getGmtTaskEnd()))
            && (this.getGmtRenderStart() == null ? other.getGmtRenderStart() == null : this.getGmtRenderStart().equals(other.getGmtRenderStart()))
            && (this.getGmtRenderEnd() == null ? other.getGmtRenderEnd() == null : this.getGmtRenderEnd().equals(other.getGmtRenderEnd()))
            && (this.getTaskWeight() == null ? other.getTaskWeight() == null : this.getTaskWeight().equals(other.getTaskWeight()))
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
        result = prime * result + ((getHostId() == null) ? 0 : getHostId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getGmtTaskStart() == null) ? 0 : getGmtTaskStart().hashCode());
        result = prime * result + ((getGmtTaskEnd() == null) ? 0 : getGmtTaskEnd().hashCode());
        result = prime * result + ((getGmtRenderStart() == null) ? 0 : getGmtRenderStart().hashCode());
        result = prime * result + ((getGmtRenderEnd() == null) ? 0 : getGmtRenderEnd().hashCode());
        result = prime * result + ((getTaskWeight() == null) ? 0 : getTaskWeight().hashCode());
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
    public RenderTask copy(){
       RenderTask obj =  new RenderTask();
       obj.setHostId(this.hostId);
       obj.setTaskId(this.taskId);
       obj.setStatus(this.status);
       obj.setGmtTaskStart(this.gmtTaskStart);
       obj.setGmtTaskEnd(this.gmtTaskEnd);
       obj.setGmtRenderStart(this.gmtRenderStart);
       obj.setGmtRenderEnd(this.gmtRenderEnd);
       obj.setTaskWeight(this.taskWeight);
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
       map.put("hostId",this.hostId);
       map.put("taskId",this.taskId);
       map.put("status",this.status);
       map.put("gmtTaskStart",this.gmtTaskStart);
       map.put("gmtTaskEnd",this.gmtTaskEnd);
       map.put("gmtRenderStart",this.gmtRenderStart);
       map.put("gmtRenderEnd",this.gmtRenderEnd);
       map.put("taskWeight",this.taskWeight);
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
