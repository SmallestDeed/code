package com.nork.task.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;

/**
 * Created by Administrator on 2016/10/16.
 */
public class SysTaskRecord  extends Mapper implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**  业务ID  **/
    private Integer businessId;
    /**  业务CODE  **/
    private String businessCode;
    /**  业务名称  **/
    private String businessName;
    /**  处理状态  **/
    private Integer state;
    /**  处理次数  **/
    private Integer execCount;
    /**  处理参数  **/
    private String params;
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
    /** 任务服务器地址 **/
    private String taskServer;
    /** 渲染服务器地址 **/
    private String renderServer;
    /** 任务优先级 **/
    private Integer priority;
    private List<Integer> stateList;
    //附加属性,用来储存渲染角度和场景参数
    private String attribute;
    /** 图片id**/
    private Integer picId;
    /** 发送渲染指令时间 **/
    private Date instructionTime;
    /** 暂停时间 **/
    private Date suspendTime;


    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public  Integer getBusinessId() {
        return businessId;
    }
    public void setBusinessId(Integer businessId){
        this.businessId = businessId;
    }
    public  String getBusinessCode() {
        return businessCode;
    }
    public void setBusinessCode(String businessCode){
        this.businessCode = businessCode;
    }
    public  String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName){
        this.businessName = businessName;
    }
    public  Integer getState() {
        return state;
    }
    public void setState(Integer state){
        this.state = state;
    }
    public Integer getExecCount() {
        return execCount;
    }
    public void setExecCount(Integer execCount) {
        this.execCount = execCount;
    }
    public String getParams() {
        return params;
    }
    public void setParams(String params) {
        this.params = params;
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
    public List<Integer> getStateList() {
        return stateList;
    }
    public String getTaskServer() {
        return taskServer;
    }
    public void setTaskServer(String taskServer) {
        this.taskServer = taskServer;
    }
    public String getRenderServer() {
        return renderServer;
    }
    public void setRenderServer(String renderServer) {
        this.renderServer = renderServer;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public void setStateList(List<Integer> stateList) {
        this.stateList = stateList;
    }
    public Date getInstructionTime() {
        return instructionTime;
    }
    public void setInstructionTime(Date instructionTime) {
        this.instructionTime = instructionTime;
    }
    public Date getSuspendTime() {
        return suspendTime;
    }
    public void setSuspendTime(Date suspendTime) {
        this.suspendTime = suspendTime;
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
        SysTask other = (SysTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
                && (this.getBusinessCode() == null ? other.getBusinessCode() == null : this.getBusinessCode().equals(other.getBusinessCode()))
                && (this.getBusinessName() == null ? other.getBusinessName() == null : this.getBusinessName().equals(other.getBusinessName()))
                && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
                && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
                && (this.getExecCount() == null ? other.getExecCount() == null : this.getExecCount().equals(other.getExecCount()))
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
        result = prime * result + ((getBusinessCode() == null) ? 0 : getBusinessCode().hashCode());
        result = prime * result + ((getBusinessName() == null) ? 0 : getBusinessName().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getExecCount() == null) ? 0 : getExecCount().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
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
    public SysTask copy(){
        SysTask obj =  new SysTask();
        obj.setBusinessId(this.businessId);
        obj.setBusinessCode(this.businessCode);
        obj.setBusinessName(this.businessName);
        obj.setState(this.state);
        obj.setExecCount(this.execCount);
        obj.setParams(this.params);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setAtt1(this.att1);
        obj.setAtt2(this.att2);
        obj.setNuma1(this.numa1);
        obj.setRemark(this.remark);

        return obj;
    }

    /**获取对象的map**/
    public Map toMap(){
        Map map =  new HashMap();
        map.put("businessId",this.businessId);
        map.put("businessCode",this.businessCode);
        map.put("businessName",this.businessName);
        map.put("state",this.state);
        map.put("execCount",this.execCount);
        map.put("params",this.params);
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
