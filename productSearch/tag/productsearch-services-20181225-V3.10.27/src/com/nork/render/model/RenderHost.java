package com.nork.render.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: RenderHost.java 
 * @Package com.nork.render.model
 * @Description:渲染-渲染主机
 * @createAuthor pandajun 
 * @CreateDate 2017-01-15 17:45:34
 * @version V1.0   
 */
public class RenderHost  extends Mapper implements Serializable{
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
        RenderHost other = (RenderHost) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getPort() == null ? other.getPort() == null : this.getPort().equals(other.getPort()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getAbilityWeight() == null ? other.getAbilityWeight() == null : this.getAbilityWeight().equals(other.getAbilityWeight()))
            && (this.getAbilityMaxNumTask() == null ? other.getAbilityMaxNumTask() == null : this.getAbilityMaxNumTask().equals(other.getAbilityMaxNumTask()))
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
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getPort() == null) ? 0 : getPort().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getAbilityWeight() == null) ? 0 : getAbilityWeight().hashCode());
        result = prime * result + ((getAbilityMaxNumTask() == null) ? 0 : getAbilityMaxNumTask().hashCode());
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
    public RenderHost copy(){
       RenderHost obj =  new RenderHost();
       obj.setIp(this.ip);
       obj.setPort(this.port);
       obj.setGroupName(this.groupName);
       obj.setStatus(this.status);
       obj.setType(this.type);
       obj.setAbilityWeight(this.abilityWeight);
       obj.setAbilityMaxNumTask(this.abilityMaxNumTask);
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
       map.put("ip",this.ip);
       map.put("port",this.port);
       map.put("groupName",this.groupName);
       map.put("status",this.status);
       map.put("type",this.type);
       map.put("abilityWeight",this.abilityWeight);
       map.put("abilityMaxNumTask",this.abilityMaxNumTask);
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
