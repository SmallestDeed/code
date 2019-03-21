package com.sandu.api.system.model;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Title: ResVersion.java 
 * @Package com.nork.system.model
 * @Description:系统版本-系统版本资源表
 * @createAuthor pandajun 
 * @CreateDate 2017-08-17 11:41:05
 * @version V1.0   
 */
public class ResVersion implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  文件编码  **/
	private String fileCode;
	/**  文件名称  **/
	private String fileName;
	/**  文件原名称  **/
	private String fileOriginalName;
	/**  文件类型  **/
	private String fileType;
	/**  文件大小  **/
	private String fileSize;
	/**  文件后缀  **/
	private String fileSuffix;
	/**  文件级别  **/
	private String fileLevel;
	/**  文件路径  **/
	private String filePath;
	/**  文件描述  **/
	private String fileDesc;
	/**  文件排序  **/
	private Integer fileOrdering;
	/** 版本Id **/
	private Integer businessId; 
	/**  版本Ids  **/
	private String businessIds;
	/*** 对应压缩包关联Ids **/
	private String compressFileIds;
	/**  fileKey  **/
	private String fileKey;
	/**  file_keys  **/
	private String fileKeys;
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

	private String resIds;
	
	private List<Integer> resIdList;

	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;

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

	public String getResIds() {
		return resIds;
	}

	public void setResIds(String resIds) {
		this.resIds = resIds;
	}

	public List<Integer> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}

	public  String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode){
		this.fileCode = fileCode;
	}
	public  String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public  String getFileOriginalName() {
		return fileOriginalName;
	}
	public void setFileOriginalName(String fileOriginalName){
		this.fileOriginalName = fileOriginalName;
	}
	public  String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	public  String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize){
		this.fileSize = fileSize;
	}
	public  String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix){
		this.fileSuffix = fileSuffix;
	}
	public  String getFileLevel() {
		return fileLevel;
	}
	public void setFileLevel(String fileLevel){
		this.fileLevel = fileLevel;
	}
	public  String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	public  String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc){
		this.fileDesc = fileDesc;
	}
	
	public Integer getFileOrdering() {
		return fileOrdering;
	}

	public void setFileOrdering(Integer fileOrdering) {
		this.fileOrdering = fileOrdering;
	}



	public String getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}

	public  String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey){
		this.fileKey = fileKey;
	}
	public  String getFileKeys() {
		return fileKeys;
	}
	public void setFileKeys(String fileKeys){
		this.fileKeys = fileKeys;
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
	

   public String getCompressFileIds() {
		return compressFileIds;
	}

	public void setCompressFileIds(String compressFileIds) {
		this.compressFileIds = compressFileIds;
	}

public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
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
        ResVersion other = (ResVersion) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
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
            && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
            && (this.getBusinessIds() == null ? other.getBusinessIds() == null : this.getBusinessIds().equals(other.getBusinessIds()))
            && (this.getFileKey() == null ? other.getFileKey() == null : this.getFileKey().equals(other.getFileKey()))
            && (this.getFileKeys() == null ? other.getFileKeys() == null : this.getFileKeys().equals(other.getFileKeys()))
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
            && (this.getCompressFileIds() == null ? other.getCompressFileIds() == null : this.getCompressFileIds().equals(other.getCompressFileIds()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getBusinessIds() == null) ? 0 : getBusinessIds().hashCode());
        result = prime * result + ((getFileKey() == null) ? 0 : getFileKey().hashCode());
        result = prime * result + ((getFileKeys() == null) ? 0 : getFileKeys().hashCode());
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
        result = prime * result + ((getCompressFileIds() == null) ? 0 : getCompressFileIds().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public ResVersion copy(){
       ResVersion obj =  new ResVersion();
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
       obj.setBusinessId(this.businessId);
       obj.setBusinessIds(this.businessIds);
       obj.setFileKey(this.fileKey);
       obj.setFileKeys(this.fileKeys);
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
       obj.setCompressFileIds(this.compressFileIds);
       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
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
       map.put("businessId",this.businessId);
       map.put("businessIds",this.businessIds);
       map.put("fileKey",this.fileKey);
       map.put("fileKeys",this.fileKeys);
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
       map.put("compressFileIds",this.compressFileIds);
       return map;
    }
}
