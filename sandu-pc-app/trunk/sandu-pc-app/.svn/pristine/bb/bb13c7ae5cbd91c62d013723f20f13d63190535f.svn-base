package com.sandu.system.model;

import java.io.Serializable;

import com.sandu.system.model.po.ResDesignRenderScenePO;

public class ResDesignRenderScene extends ResDesignRenderScenePO implements Serializable{

	private static final long serialVersionUID = -5596969193088303990L;

	private String originalPicPath;
	 
	private Integer baseRenderId;
	
	private String picPath;

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
	
	 /**获取对象的copy**/
    public ResDesign resDesignCopy(){
       ResDesign obj =  new ResDesign();
       obj.setSysCode(this.getSysCode());
       obj.setCreator(this.getCreator());
       obj.setGmtCreate(this.getGmtCreate());
       obj.setModifier(this.getModifier());
       obj.setGmtModified(this.getGmtModified());
       obj.setIsDeleted(this.getIsDeleted());
       obj.setFileCode(this.getFileCode());
       obj.setFileName(this.getFileName());
       obj.setFileOriginalName(this.getFileOriginalName());
       obj.setFileType(this.getFileType());
       obj.setFileSize(this.getFileSize());
       obj.setFileSuffix(this.getFileSuffix());
       obj.setFileLevel(this.getFileLevel());
       obj.setFilePath(this.getFilePath());
       obj.setFileDesc(this.getFileDesc());
       obj.setFileOrdering(this.getFileOrdering());
       obj.setFileKey(this.getFileKey());
       obj.setBusinessId(this.getBusinessId());
       obj.setRemark(this.getRemark());
       return obj;
    }
    
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

	public String getOriginalPicPath() {
		return originalPicPath;
	}

	public void setOriginalPicPath(String originalPicPath) {
		this.originalPicPath = originalPicPath;
	}

	public Integer getBaseRenderId() {
		return baseRenderId;
	}

	public void setBaseRenderId(Integer baseRenderId) {
		this.baseRenderId = baseRenderId;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
