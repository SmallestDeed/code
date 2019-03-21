package com.sandu.system.model;

import java.io.Serializable;

import com.sandu.system.model.po.ResDesignPO;

public class ResDesign extends ResDesignPO implements Serializable{

	private static final long serialVersionUID = 1L;

	String originalPicPath;
 
	Integer baseRenderId;
	
	String picPath;

	//0 非父配置文件 1 父配置文件
	Integer isParent;
	
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
       obj.setAtt4(this.getAtt4());
       obj.setAtt5(this.getAtt5());
       obj.setAtt6(this.getAtt6());
       obj.setBusinessId(this.getBusinessId());
       obj.setNuma2(this.getNuma2());
       obj.setNuma3(this.getNuma3());
       obj.setNuma4(this.getNuma4());
       obj.setNuma5(this.getNuma5());
       obj.setNuma6(this.getNuma6());
       obj.setRemark(this.getRemark());
       return obj;
    }
    
    
    /**获取对象的copy**/
    public ResDesignRenderScene resDesignRenderSceneCopy(){
       ResDesignRenderScene obj =  new ResDesignRenderScene();
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

	public String getPicPath() {
		return picPath;
	}
	
	public void setPicPath(String picPath) {
		this.picPath = picPath;
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

	public Integer getIsParent() {
		return isParent;
	}
	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}
	@Override
	public String toString() {
		return "ResDesign{" +
				"originalPicPath='" + originalPicPath + '\'' +
				", baseRenderId=" + baseRenderId +
				", picPath='" + picPath + '\'' +
				", id=" + this.getId() +
				", oldId=" + this.getOldId() +
				", fileCode='" + this.getFileCode() + '\'' +
				", fileName='" + this.getFileName() + '\'' +
				", fileOriginalName='" + this.getFileOriginalName() + '\'' +
				", fileType='" + this.getFileType() + '\'' +
				", fileSize='" + this.getFileSize() + '\'' +
				", fileWeight='" + this.getFileWeight() + '\'' +
				", fileHigh='" + this.getFileHigh() + '\'' +
				", fileLength=" + this.getFileLength() +
				", fileSuffix='" + this.getFileSuffix() + '\'' +
				", fileFormat='" + this.getFileFormat() + '\'' +
				", fileLevel='" + this.getFileLevel() + '\'' +
				", filePath='" + this.getFilePath() + '\'' +
				", fileDesc='" + this.getFileDesc() + '\'' +
				", fileOrdering=" + this.getFileOrdering() +
				", sysCode='" + this.getSysCode() + '\'' +
				", creator='" + this.getCreator() + '\'' +
				", gmtCreate=" + this.getGmtCreate() +
				", modifier='" + this.getModifier() + '\'' +
				", gmtModified=" + this.getGmtModified() +
				", isDeleted=" + this.getIsDeleted() +
				", fileKey='" + this.getFileKey() + '\'' +
				", businessId=" + this.getBusinessId() +
				", smallPicInfo='" + this.getSmallPicInfo() + '\'' +
				", viewPoint=" + this.getViewPoint() +
				", scene=" + this.getScene() +
				", sequence=" + this.getSequence() +
				", renderingType=" + this.getRenderingType() +
				", panoPath='" + this.getPanoPath() + '\'' +
				", minHeight=" + this.getMinHeight() +
				", isModelShare=" + this.getIsModelShare() +
				", att1='" + this.getAtt1() + '\'' +
				", att2='" + this.getAtt2() + '\'' +
				", att3='" + this.getAtt3() + '\'' +
				", att4='" + this.getAtt4() + '\'' +
				", att5='" + this.getAtt5() + '\'' +
				", att6='" + this.getAtt6() + '\'' +
				", numa1=" + this.getNuma1() +
				", numa2=" + this.getNuma2() +
				", numa3=" + this.getNuma3() +
				", numa4=" + this.getNuma4() +
				", numa5=" + this.getNuma5() +
				", numa6=" + this.getNuma6() +
				", remark='" + this.getRemark() + '\'' +
				", source='" + this.getSource() + '\'' +
				'}';
	}
}
