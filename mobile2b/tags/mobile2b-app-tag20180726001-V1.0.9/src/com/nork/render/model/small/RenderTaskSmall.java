package com.nork.render.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: RenderTask.java 
 * @Package com.nork.render.model.small
 * @Description:渲染-渲染任务
 * @createAuthor pandajun 
 * @CreateDate 2017-01-17 20:31:06
 * @version V1.0   
 */
public class RenderTaskSmall  implements Serializable{
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


}
