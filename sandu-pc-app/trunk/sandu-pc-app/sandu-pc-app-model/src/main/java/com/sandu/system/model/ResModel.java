package com.sandu.system.model;

import java.io.Serializable;
import java.util.List;

import com.sandu.system.model.po.ResModelPO;

/**   
 * @Title: ResModel.java 
 * @Package com.nork.system.model
 * @Description:系统-模型资源库
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:05:22
 * @version V1.0   
 */
public class ResModel extends ResModelPO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/*添加三个字段:三个字段能获得file_type信息*/
	private String firstMenu;
	
	private String secondMenu;
	
	private String thirdMenuM;
	
	private String resIds;

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

	public String getThirdMenuM() {
		return thirdMenuM;
	}

	public void setThirdMenuM(String thirdMenuM) {
		this.thirdMenuM = thirdMenuM;
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
        ResModel other = (ResModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getModelCode() == null ? other.getModelCode() == null : this.getModelCode().equals(other.getModelCode()))
            && (this.getModelName() == null ? other.getModelName() == null : this.getModelName().equals(other.getModelName()))
            && (this.getModelFileName() == null ? other.getModelFileName() == null : this.getModelFileName().equals(other.getModelFileName()))
            && (this.getModelType() == null ? other.getModelType() == null : this.getModelType().equals(other.getModelType()))
            && (this.getModelSize() == null ? other.getModelSize() == null : this.getModelSize().equals(other.getModelSize()))
            && (this.getModelSuffix() == null ? other.getModelSuffix() == null : this.getModelSuffix().equals(other.getModelSuffix()))
            && (this.getModelLevel() == null ? other.getModelLevel() == null : this.getModelLevel().equals(other.getModelLevel()))
            && (this.getModelPath() == null ? other.getModelPath() == null : this.getModelPath().equals(other.getModelPath()))
            && (this.getModelDesc() == null ? other.getModelDesc() == null : this.getModelDesc().equals(other.getModelDesc()))
            && (this.getModelOrdering() == null ? other.getModelOrdering() == null : this.getModelOrdering().equals(other.getModelOrdering()))
      
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
        result = prime * result + ((getModelCode() == null) ? 0 : getModelCode().hashCode());
        result = prime * result + ((getModelName() == null) ? 0 : getModelName().hashCode());
        result = prime * result + ((getModelFileName() == null) ? 0 : getModelFileName().hashCode());
        result = prime * result + ((getModelType() == null) ? 0 : getModelType().hashCode());
        result = prime * result + ((getModelSize() == null) ? 0 : getModelSize().hashCode());
        result = prime * result + ((getModelSuffix() == null) ? 0 : getModelSuffix().hashCode());
        result = prime * result + ((getModelLevel() == null) ? 0 : getModelLevel().hashCode());
        result = prime * result + ((getModelPath() == null) ? 0 : getModelPath().hashCode());
        result = prime * result + ((getModelDesc() == null) ? 0 : getModelDesc().hashCode());
        result = prime * result + ((getModelOrdering() == null) ? 0 : getModelOrdering().hashCode());

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
    /*public ResModel copy(){
       ResModel obj =  new ResModel();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setModelCode(this.modelCode);
       obj.setModelName(this.modelName);
       obj.setModelFileName(this.modelFileName);
       obj.setModelType(this.modelType);
       obj.setModelSize(this.modelSize);
       obj.setModelSuffix(this.modelSuffix);
       obj.setModelLevel(this.modelLevel);
       obj.setModelPath(this.modelPath);
       obj.setModelDesc(this.modelDesc);
       obj.setModelOrdering(this.modelOrdering);
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
       map.put("modelCode",this.modelCode);
       map.put("modelName",this.modelName);
       map.put("modelFileName",this.modelFileName);
       map.put("modelType",this.modelType);
       map.put("modelSize",this.modelSize);
       map.put("modelSuffix",this.modelSuffix);
       map.put("modelLevel",this.modelLevel);
       map.put("modelPath",this.modelPath);
       map.put("modelDesc",this.modelDesc);
       map.put("modelOrdering",this.modelOrdering);
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
