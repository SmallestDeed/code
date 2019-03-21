package com.sandu.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sandu.product.model.po.StructureProductPO;

/**   
 * @Title: StructureProduct.java 
 * @Package com.nork.product.model
 * @Description:产品模块-结构表
 * @createAuthor pandajun 
 * @CreateDate 2016-12-05 14:46:50
 * @version V1.0   
 */
public class StructureProduct extends StructureProductPO implements Serializable{
	
	private static final long serialVersionUID = 1L;

    /*结束时间string*/
    String gmtCreateEndStr;
    /*开始时间string*/
    String gmtCreateStartStr;
    
    /**
	 * 空间编码
	 */
	private String spaceCode;

	/**
	 * 结构类型集合，用于in查询
	 */
	List <Integer >structureSmallTypeList = new ArrayList<Integer>();
    
	private List<Integer> putawayStateList;
	
	//方案结构区域标识
	private String planStructureRegionMark;
	
    private String  deviceId = null;
    
	private String  msgId = null;
	
	private String  ids = null;
	
	private Integer start = 0;
	
	private Integer limit = 20;
	
	private String  order = null;
	
	private String  orderNum = null;
	
	private String  orders = null;
	
	/**级别限制的资源数量*/
	private int levelLimitCount=0;
    
    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public int getLevelLimitCount() {
		return levelLimitCount;
	}

	public void setLevelLimitCount(int levelLimitCount) {
		this.levelLimitCount = levelLimitCount;
	}

	public String getGmtCreateEndStr() {
		return gmtCreateEndStr;
	}

	public void setGmtCreateEndStr(String gmtCreateEndStr) {
		this.gmtCreateEndStr = gmtCreateEndStr;
	}

	public String getGmtCreateStartStr() {
		return gmtCreateStartStr;
	}

	public void setGmtCreateStartStr(String gmtCreateStartStr) {
		this.gmtCreateStartStr = gmtCreateStartStr;
	}

	/**
	 * 用于查询 该结构 存在 哪个城市
	 */
	private String areaLongCode;
	
	public String getAreaLongCode() {
		return areaLongCode;
	}

	public void setAreaLongCode(String areaLongCode) {
		this.areaLongCode = areaLongCode;
	}
	
	public String getSpaceCode() {
		return spaceCode;
	}

	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}

	public List<Integer> getStructureSmallTypeList() {
		return structureSmallTypeList;
	}

	public void setStructureSmallTypeList(List<Integer> structureSmallTypeList) {
		this.structureSmallTypeList = structureSmallTypeList;
	}

	public String getPlanStructureRegionMark() {
		return planStructureRegionMark;
	}

	public void setPlanStructureRegionMark(String planStructureRegionMark) {
		this.planStructureRegionMark = planStructureRegionMark;
	}

	public List<Integer> getPutawayStateList() {
		return putawayStateList;
	}

	public void setPutawayStateList(List<Integer> putawayStateList) {
		this.putawayStateList = putawayStateList;
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
        StructureProduct other = (StructureProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStructureCode() == null ? other.getStructureCode() == null : this.getStructureCode().equals(other.getStructureCode()))
            && (this.getStructureName() == null ? other.getStructureName() == null : this.getStructureName().equals(other.getStructureName()))
            && (this.getTempletId() == null ? other.getTempletId() == null : this.getTempletId().equals(other.getTempletId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getConfigFileId() == null ? other.getConfigFileId() == null : this.getConfigFileId().equals(other.getConfigFileId()))
            && (this.getGroupFlag() == null ? other.getGroupFlag() == null : this.getGroupFlag().equals(other.getGroupFlag()))
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
        result = prime * result + ((getStructureCode() == null) ? 0 : getStructureCode().hashCode());
        result = prime * result + ((getStructureName() == null) ? 0 : getStructureName().hashCode());
        result = prime * result + ((getTempletId() == null) ? 0 : getTempletId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getConfigFileId() == null) ? 0 : getConfigFileId().hashCode());
        result = prime * result + ((getGroupFlag() == null) ? 0 : getGroupFlag().hashCode());
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
    /*public StructureProduct copy(){
       StructureProduct obj =  new StructureProduct();
       obj.setStructureCode(this.structureCode);
       obj.setStructureName(this.structureName);
       obj.setTempletId(this.templetId);
       obj.setStatus(this.status);
       obj.setPicId(this.picId);
       obj.setConfigFileId(this.configFileId);
       obj.setGroupFlag(this.groupFlag);
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
    }*/
    
     /**获取对象的map**/
    /*public Map toMap(){
       Map map =  new HashMap();
       map.put("structureCode",this.structureCode);
       map.put("structureName",this.structureName);
       map.put("templetId",this.templetId);
       map.put("status",this.status);
       map.put("picId",this.picId);
       map.put("configFileId",this.configFileId);
       map.put("groupFlag",this.groupFlag);
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
    }*/
}
