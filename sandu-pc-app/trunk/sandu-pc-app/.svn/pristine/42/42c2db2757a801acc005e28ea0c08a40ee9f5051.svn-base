package com.sandu.system.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.common.util.Utils;
import com.sandu.system.model.po.ResFilePO;

/**   
 * @Title: ResFile.java 
 * @Package com.nork.system.model
 * @Description:系统模块-文件资源库
 * @createAuthor pandajun 
 * @CreateDate 2015-07-02 17:36:00
 * @version V1.0   
 */
public class ResFile extends ResFilePO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String gmtCreateStr;

	/*添加三个字段:三个字段能获得file_type信息*/
	private String firstMenu;
	
	private String secondMenu;
	
	private String thirdMenuF;

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

	public String getFirstMenu() {
		return firstMenu;
	}

	public void setFirstMenu(String firstMenu) {
		this.firstMenu = firstMenu;
	}

	public String getSecondMenu() {
		return secondMenu;
	}

	public void setSecondMenu(String secondMenu) {
		this.secondMenu = secondMenu;
	}

	public String getThirdMenuF() {
		return thirdMenuF;
	}

	public void setThirdMenuF(String thirdMenuF) {
		this.thirdMenuF = thirdMenuF;
	}

	public void setGmtCreateStr(String gmtCreateStr) {
		this.gmtCreateStr = gmtCreateStr;
	}

	public String getGmtCreateStr() {
		return Utils.getDateStr(this.getGmtCreate(),"yyyy-MM-dd HH:mm:ss");
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
        ResFile other = (ResFile) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getFileCode() == null ? other.getFileCode() == null : this.getFileCode().equals(other.getFileCode()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getFileOriginalName() == null ? other.getFileOriginalName() == null : this.getFileOriginalName().equals(other.getFileOriginalName()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFileSuffix() == null ? other.getFileSuffix() == null : this.getFileSuffix().equals(other.getFileSuffix()))
            && (this.getFileLevel() == null ? other.getFileLevel() == null : this.getFileLevel().equals(other.getFileLevel()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getFileDesc() == null ? other.getFileDesc() == null : this.getFileDesc().equals(other.getFileDesc()))
            && (this.getFileOrdering() == null ? other.getFileOrdering() == null : this.getFileOrdering().equals(other.getFileOrdering()))
            && (this.getFileKeys() == null ? other.getFileKeys() == null : this.getFileKeys().equals(other.getFileKeys()))
            && (this.getBusinessIds() == null ? other.getBusinessIds() == null : this.getBusinessIds().equals(other.getBusinessIds()))
            && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
            && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
            && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
            && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
  
            && (this.getNumAtt2() == null ? other.getNumAtt2() == null : this.getNumAtt2().equals(other.getNumAtt2()))
            && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
            && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getFileCode() == null) ? 0 : getFileCode().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFileOriginalName() == null) ? 0 : getFileOriginalName().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFileSuffix() == null) ? 0 : getFileSuffix().hashCode());
        result = prime * result + ((getFileLevel() == null) ? 0 : getFileLevel().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getFileDesc() == null) ? 0 : getFileDesc().hashCode());
        result = prime * result + ((getFileOrdering() == null) ? 0 : getFileOrdering().hashCode());
        result = prime * result + ((getFileKeys() == null) ? 0 : getFileKeys().hashCode());
        result = prime * result + ((getBusinessIds() == null) ? 0 : getBusinessIds().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());

        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    /*public ResFile copy(){
       ResFile obj =  new ResFile();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setFileCode(this.fileCode);
       obj.setFileName(this.fileName);
       obj.setFileOriginalName(this.fileOriginalName);
       obj.setFileType(this.fileType);
       obj.setFileSize(this.fileSize);
       obj.setFileSuffix(this.fileSuffix);
       obj.setFileLevel(this.fileLevel);
       obj.setFilePath(this.filePath);
       obj.setFileDesc(this.fileDesc);
       obj.setFileOrdering(this.fileOrdering);
       obj.setFileKey(this.fileKey);
       obj.setFileKeys(this.fileKeys);
       obj.setBusinessIds(this.businessIds);
       obj.setAtt4(this.att4);
       obj.setAtt5(this.att5);
       obj.setAtt6(this.att6);
       obj.setDateAtt1(this.dateAtt1);
       obj.setDateAtt2(this.dateAtt2);
       obj.setBusinessId(this.businessId);
       obj.setNumAtt2(this.numAtt2);
       obj.setNumAtt3(this.numAtt3);
       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);
       return obj;
    }*/
    
     /**获取对象的map**/
    /*public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("fileCode",this.fileCode);
       map.put("fileName",this.fileName);
       map.put("fileOriginalName",this.fileOriginalName);
       map.put("fileType",this.fileType);
       map.put("fileSize",this.fileSize);
       map.put("fileSuffix",this.fileSuffix);
       map.put("fileLevel",this.fileLevel);
       map.put("filePath",this.filePath);
       map.put("fileDesc",this.fileDesc);
       map.put("fileOrdering",this.fileOrdering);
       map.put("fileKey",this.fileKey);
       map.put("fileKeys",this.fileKeys);
       map.put("businessIds",this.businessIds);
       map.put("att4",this.att4);
       map.put("att5",this.att5);
       map.put("att6",this.att6);
       map.put("dateAtt1",this.dateAtt1);
       map.put("dateAtt2",this.dateAtt2);
       map.put("businessId",this.businessId);
       map.put("numAtt2",this.numAtt2);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);
       return map;
    }*/
    
    public ResFile toObj(Map map){
    	ResFile obj =  new ResFile();
    	if(map == null){
    		map = new HashMap();
    	}
    	obj.setSysCode((String) map.get("sysCode"));
        obj.setCreator((String) map.get("creator"));
        obj.setGmtCreate((Date) map.get("gmtCreate"));
        obj.setModifier((String) map.get("modifier"));
        obj.setGmtModified((Date) map.get("gmtModified"));
        obj.setIsDeleted((Integer) map.get("isDeleted"));
        obj.setFileCode((String) map.get("fileCode"));
        obj.setFileName((String)map.get("fileName"));
        obj.setFileOriginalName((String) map.get("fileOriginalName"));
        obj.setFileType((String) map.get("fileType"));
        obj.setFileSize((String) map.get("fileSize"));
        obj.setFileSuffix((String) map.get("fileSuffix"));
        obj.setFileLevel((String) map.get("fileLevel"));
        obj.setFilePath((String) map.get("filePath"));
        obj.setFileDesc((String) map.get("fileDesc"));
        obj.setFileOrdering((Integer) map.get("fileOrdering"));
        obj.setFileKey((String)map.get("fileKey"));
        return obj;
     }

	private String resIds;
	
	public String getResIds() {
		return resIds;
	}

	public void setResIds(String resIds) {
		this.resIds = resIds;
	}

	private List<Integer> resIdList;
	public List<Integer> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}
}
