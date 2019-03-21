package com.nork.render.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: RenderHost.java 
 * @Package com.nork.render.model.small
 * @Description:渲染-渲染主机
 * @createAuthor pandajun 
 * @CreateDate 2017-01-15 17:45:34
 * @version V1.0   
 */
public class RenderHostSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  主机IP  **/
	private String ip;
	/**  端口  **/
	private Integer port;
	/**  主机组名  **/
	private String groupName;
	/**  主机状态  **/
	private Integer status;
	/**  主机类型  **/
	private Integer type;
	/**  性能权重  **/
	private Integer abilityWeight;
	/**  主机最大渲染任务数量  **/
	private Integer abilityMaxNumTask;
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

	public  String getIp() {
		return ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public  Integer getPort() {
		return port;
	}
	public void setPort(Integer port){
		this.port = port;
	}
	public  String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	public  Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public  Integer getType() {
		return type;
	}
	public void setType(Integer type){
		this.type = type;
	}
	public  Integer getAbilityWeight() {
		return abilityWeight;
	}
	public void setAbilityWeight(Integer abilityWeight){
		this.abilityWeight = abilityWeight;
	}
	public  Integer getAbilityMaxNumTask() {
		return abilityMaxNumTask;
	}
	public void setAbilityMaxNumTask(Integer abilityMaxNumTask){
		this.abilityMaxNumTask = abilityMaxNumTask;
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
