package com.sandu.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sandu.system.model.po.ResTexturePO;

/**   
 * @Title: ResTexture.java 
 * @Package com.nork.system.model
 * @Description:系统模块-材质库
 * @createAuthor pandajun 
 * @CreateDate 2016-06-30 14:10:42
 * @version V1.0   
 */
public class ResTexture extends ResTexturePO implements Serializable{
	private static final long serialVersionUID = 1L;

    /*id集合*/
    List<String> resTextureIds= new ArrayList<String>();
    
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

	public List<String> getResTextureIds() {
		return resTextureIds;
	}

	public void setResTextureIds(List<String> resTextureIds) {
		this.resTextureIds = resTextureIds;
	}
	
	
	
	
	
	
	

	private String normalPath;
	
	
	//材质球文件名称
	private String textureBallFileName;
	//材质球文件路径
	private String materialPath;

	public String getTextureBallFileName() {
		return textureBallFileName;
	}

	public void setTextureBallFileName(String textureBallFileName) {
		this.textureBallFileName = textureBallFileName;
	}

	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
	}

	public String getNormalPath() {
		return normalPath;
	}

	public void setNormalPath(String normalPath) {
		this.normalPath = normalPath;
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
        ResTexture other = (ResTexture) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getFileCode() == null ? other.getFileCode() == null : this.getFileCode().equals(other.getFileCode()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFileWidth() == null ? other.getFileWidth() == null : this.getFileWidth().equals(other.getFileWidth()))
            && (this.getFileHeight() == null ? other.getFileHeight() == null : this.getFileHeight().equals(other.getFileHeight()))
            && (this.getFileSuffix() == null ? other.getFileSuffix() == null : this.getFileSuffix().equals(other.getFileSuffix()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getFileDesc() == null ? other.getFileDesc() == null : this.getFileDesc().equals(other.getFileDesc()))
            && (this.getStyle() == null ? other.getStyle() == null : this.getStyle().equals(other.getStyle()))
            && (this.getTexture() == null ? other.getTexture() == null : this.getTexture().equals(other.getTexture()))
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
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getFileCode() == null) ? 0 : getFileCode().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFileWidth() == null) ? 0 : getFileWidth().hashCode());
        result = prime * result + ((getFileHeight() == null) ? 0 : getFileHeight().hashCode());
        result = prime * result + ((getFileSuffix() == null) ? 0 : getFileSuffix().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getFileDesc() == null) ? 0 : getFileDesc().hashCode());
        result = prime * result + ((getStyle() == null) ? 0 : getStyle().hashCode());
        result = prime * result + ((getTexture() == null) ? 0 : getTexture().hashCode());
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
    /*public ResTexture copy(){
       ResTexture obj =  new ResTexture();
       obj.setName(this.name);
       obj.setFileName(this.fileName);
       obj.setType(this.type);
       obj.setFileCode(this.fileCode);
       obj.setFileSize(this.fileSize);
       obj.setFileWidth(this.fileWidth);
       obj.setFileHeight(this.fileHeight);
       obj.setFileSuffix(this.fileSuffix);
       obj.setFilePath(this.filePath);
       obj.setFileDesc(this.fileDesc);
       obj.setStyle(this.style);
       obj.setTexture(this.texture);
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
       map.put("name",this.name);
       map.put("fileName",this.fileName);
       map.put("type",this.type);
       map.put("fileCode",this.fileCode);
       map.put("fileSize",this.fileSize);
       map.put("fileWidth",this.fileWidth);
       map.put("fileHeight",this.fileHeight);
       map.put("fileSuffix",this.fileSuffix);
       map.put("filePath",this.filePath);
       map.put("fileDesc",this.fileDesc);
       map.put("style",this.style);
       map.put("texture",this.texture);
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
